from flask_restplus import Namespace, fields
import datetime
import string

from ..util.helpers import datetime_parser, min_length_validate
from .ArgsCollector import ArgsAggregator, ArgValueError

from .reg_exps import (USERNAME_REGEX, PASSWORD_REGEX,
                       NAME_REGEX, SURNAME_REGEX, PATRONYMIC_REGEX)

from app.main.model.client_profile import Gender

# class IdsContainer(ArgsAggregator):
#     def __init__(self, api, ):


class IDsArray(ArgsAggregator):
    def __init__(self, api):
        super().__init__(api, "ids")
        self.add_argument('ids').with_field(fields.List(fields.Integer)).with_parser(type=int, action='append')


class UserDto:
    api = Namespace('users ns', description='user related operations')
    user = api.model('user', {
        'username':  fields.String(required=True, description='user username'),
        'password':  fields.String(required=True, description='user password'),
        'public_id': fields.String(description='user Identifier')
    })

    @staticmethod
    def username_check(username):
        if not USERNAME_REGEX.fullmatch(username):
            raise ValueError('username should match pattern {}', USERNAME_REGEX.pattern)
        return username

    @staticmethod
    def password_check(password):
        if not PASSWORD_REGEX.fullmatch(password):
            raise ValueError('password should match pattern {}', PASSWORD_REGEX.pattern)
        return password

    class UserCreateArgs(ArgsAggregator):
        def __init__(self):
            super().__init__(UserDto.api, "UserCreate")
            self.add_argument('username').with_field(fields.String(pattern=USERNAME_REGEX.pattern))\
                .with_parser(type=UserDto.username_check)
            self.add_argument('password').with_field(fields.String(pattern=PASSWORD_REGEX.pattern))\
                .with_parser(type=UserDto.password_check)

    class UserModel(ArgsAggregator):
        def __init__(self):
            super().__init__(UserDto.api, "User")
            self.add_argument('username').with_field(fields.String)
            self.add_argument('is_admin').with_field(fields.Boolean(attribute=lambda x: x.admin))
            self.add_argument('public_id').with_field(fields.String)

    _user_create = None

    @staticmethod
    def user_create_model():
        if not UserDto._user_create:
            UserDto._user_create = UserDto.UserCreateArgs()
        return UserDto._user_create


class AuthDto:
    api = Namespace('auth', description='authentication related operations')
    user_auth = api.model('auth_details', {
        'username': fields.String(required=True, description="users username"),
        'password': fields.String(required=True, description="user password")
    })


class ClientProfileDto:
    api = Namespace('clients', description='clients related operations')

    @staticmethod
    def check_name(name):
        if not NAME_REGEX.fullmatch(name):
            raise ValueError("name should match pattern {}".format(NAME_REGEX.pattern))
        return name

    @staticmethod
    def check_surname(surname):
        if not SURNAME_REGEX.fullmatch(surname):
            raise ValueError("surname should match pattern {}".format(SURNAME_REGEX.pattern))
        return surname

    @staticmethod
    def check_patronymic(patronymic):
        if not PATRONYMIC_REGEX.fullmatch(patronymic):
            raise ValueError("patronymic should match pattern {}".format(PATRONYMIC_REGEX.pattern))
        return patronymic

    @staticmethod
    def gender(gender_str):
        if gender_str == Gender.MALE.value:
            return Gender.MALE
        return Gender.FEMALE

    class ClientCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientProfileDto.api, "ClientCreate")

            self.add_argument('public_id').with_field(fields.String(required=True)).with_parser()

            self.add_argument('is_child').with_field(fields.Boolean(default=False, required=True))\
                .with_parser(type=bool, default=False, required=True)

            self.add_argument('name').with_field(fields.String(min_length=1)).with_parser(
                type=min_length_validate('name', 1), required=True)

            self.add_argument('surname').with_field(fields.String(min_length=1)).with_parser(
                type=min_length_validate('surname', 1), required=True)

            self.add_argument('patronymic').with_field(fields.String(min_length=1)).with_parser(
                type=min_length_validate('patronymic', 1))

            gender_strs = [val.value for val in [Gender.MALE, Gender.FEMALE]]
            self.add_argument('gender')\
                .with_field(fields.String(enum=gender_strs, required=True))\
                .with_parser(type=ClientProfileDto.gender, choices=[Gender.MALE, Gender.FEMALE], required=True)

            self.add_argument('birthday')\
                .with_field(fields.DateTime).with_parser(type=datetime_parser, required=False)

            self.add_argument('time_arrival').with_field(fields.DateTime(required=True))\
                .with_parser(type=datetime_parser, required=True)

            self.add_argument('time_departure').with_field(fields.DateTime(required=True)) \
                .with_parser(type=datetime_parser, required=True)

            self.add_argument('diet_id').with_field(fields.Integer(required=True)).with_parser(type=int, required=True)

    class ClientModel(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientProfileDto.api, 'Client')

            def pr(el):
                str(el)
                print(el)
            self.add_argument('public_id').with_field(fields.String(attribute=lambda x: x.user.public_id))

            self.add_argument('name').with_field(fields.String)

            self.add_argument('surname').with_field(fields.String)

            self.add_argument('patronymic').with_field(fields.String)

            self.add_argument('birthday').with_field(fields.DateTime)

            self.add_argument('is_child').with_field(fields.Boolean)

            gender_strs = [val.value for val in [Gender.MALE, Gender.FEMALE]]
            self.add_argument('gender').with_field(fields.String(attribute=lambda x: x.gender.value, enum=gender_strs))

            self.add_argument('time_arrival').with_field(fields.DateTime)

            self.add_argument('time_departure').with_field(fields.DateTime)

            self.add_argument('diet_id').with_field(fields.Integer)

    _client_create = None

    @staticmethod
    def client_create_model():
        if not ClientProfileDto._client_create:
            ClientProfileDto._client_create = ClientProfileDto.ClientCreate()
        return ClientProfileDto._client_create

    _client_model = None

    @staticmethod
    def client_model():
        if not ClientProfileDto._client_model:
            ClientProfileDto._client_model = ClientProfileDto.ClientModel()
        return ClientProfileDto._client_model



class DietDto:
    api = Namespace('diets', description='diets related operations')

    @staticmethod
    def check_diet_name(name):
        import string
        name = name.strip(string.whitespace)
        if len(name) == 0:
            raise ValueError('diet name should not contain only whitespace or be empty')
        return name

    class DietCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(DietDto.api, 'DietCreate')
            self.add_argument('name').with_field(fields.String(required=True))\
                .with_parser(type=DietDto.check_diet_name, location='json', required=True)
            self.add_argument('description').with_field(fields.String(required=True))\
                .with_parser(location='json', required=True)

    class DietModel(ArgsAggregator):
        def __init__(self):
            super().__init__(DietDto.api, 'Diet')
            self.add_argument('id').with_field(fields.Integer)
            self.add_argument('name').with_field(fields.String)
            self.add_argument('description').with_field(fields.String)

    _diet_create = None

    @staticmethod
    def diet_create_model():
        if DietDto._diet_create is None:
            DietDto._diet_create = DietDto.DietCreate()
        return DietDto._diet_create

    _diet_model = None

    @staticmethod
    def diet_model():
        if DietDto._diet_model is None:
            DietDto._diet_model = DietDto.DietModel()
        return DietDto._diet_model


class ProcedureDto:
    api = Namespace('procedure', description='procedures related operations')

    @staticmethod
    def check_name(name):
        name = str(name)
        name = name.strip(string.whitespace)
        if len(name) == 0:
            raise ArgValueError("name should not be empty or only whitespaced")
        return name

    @staticmethod
    def check_description(description):
        description = str(description)
        description = description.strip(string.whitespace)
        if len(description) == 0:
            raise ArgValueError("name should not be empty or only whitespaced")
        return description

    @staticmethod
    def filter_out_empty_places(places):
        res = []
        for place in places:
            new_place = place.strip(string.whitespace)
            if len(new_place) > 0:
                res.append(new_place)

        return res

    class ProcedureCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(ProcedureDto.api, 'ProcedureCreate')
            self.add_argument('name').with_field(fields.String(min_length=1, required=True))\
                .with_parser(location='json', type=str, required=True)\
                .with_value_check(ProcedureDto.check_name)
            self.add_argument('description').with_field(fields.String(min_length=1, required=True))\
                .with_parser(location='json', type=str, required=True)\
                .with_value_check(ProcedureDto.check_description)

            self.add_argument('places').with_field(fields.List(fields.String, required=True))\
                .with_parser(location='json', type=str, action='append', required=True)\
                .with_value_check(ProcedureDto.filter_out_empty_places)

    class ProcedureModel(ArgsAggregator):
        def __init__(self):
            super().__init__(ProcedureDto.api, 'Procedure')
            self.add_argument('id').with_field(fields.Integer)
            self.add_argument('name').with_field(fields.String)
            self.add_argument('description').with_field(fields.String)
            self.add_argument('places').with_field(fields.List(fields.String))

    _procedure_create_model = None

    @staticmethod
    def procedure_create_model():
        if ProcedureDto._procedure_create_model is None:
            ProcedureDto._procedure_create_model = ProcedureDto.ProcedureCreate()
        return ProcedureDto._procedure_create_model

    _procedure_model = None

    @staticmethod
    def procedure_model():
        if ProcedureDto._procedure_model is None:
            ProcedureDto._procedure_model = ProcedureDto.ProcedureModel()
        return ProcedureDto._procedure_model


class ClientProcedureDto:
    api = Namespace('client_procedure', description='operations related to managing client procedures')

    class ClientProcedureCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientProcedureDto.api, 'ClientProcedureCreate')
            # self.add_argument('client_id').with_field(fields.Integer(required=True))\
            #     .with_parser(location='json', type=int)

            self.add_argument('procedure_id').with_field(fields.Integer(required=True))\
                .with_parser(location='json', type=int)

            self.add_argument('time').with_field(fields.DateTime(required=True))\
                .with_parser(location='json', type=datetime_parser)

            self.add_argument('place').with_field(fields.String(required=True))\
                .with_parser(location='json', type=str)

    class ClientProcedureModel(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientProcedureDto.api, 'ClientProcedureModel')
            self.add_argument('id').with_field(fields.Integer)
            self.add_argument('client_public_id').with_field(fields.String(attribute=lambda x:x.client.user.public_id))
            self.add_argument('procedure_id').with_field(fields.Integer)
            self.add_argument('time').with_field(fields.DateTime)
            self.add_argument('place').with_field(fields.String)

    _client_procedure_create_model = None

    @staticmethod
    def client_procedure_create_model():
        if ClientProcedureDto._client_procedure_create_model is None:
            ClientProcedureDto._client_procedure_create_model = ClientProcedureDto.ClientProcedureCreate()
        return ClientProcedureDto._client_procedure_create_model

    _client_procedure_model = None

    @staticmethod
    def client_procedure_model():
        if ClientProcedureDto._client_procedure_model is None:
            ClientProcedureDto._client_procedure_model = ClientProcedureDto.ClientProcedureModel()
        return ClientProcedureDto._client_procedure_model


class DailyDishesDto:
    api = Namespace('dishes', description='operation related to daily dishes only')

    class DishCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DishCreate')
            self.add_argument('name').with_field(fields.String(min_length=1)).with_parser(type=min_length_validate('name', 1))
            self.add_argument('description').with_field(fields.String).with_parser(type=str)

    class DishModel(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DishModel')
            self.add_argument('dish_id').with_field(fields.Integer(attribute=lambda x: x.id))
            self.add_argument('name').with_field(fields.String)
            self.add_argument('description').with_field(fields.String)

    _dish_create_model = None

    @staticmethod
    def dish_create_model():
        if DailyDishesDto._dish_create_model is None:
            DailyDishesDto._dish_create_model = DailyDishesDto.DishCreate()

        return DailyDishesDto._dish_create_model

    _dish_model = None

    @staticmethod
    def dish_model():
        if DailyDishesDto._dish_model is None:
            DailyDishesDto._dish_model = DailyDishesDto.DishModel()

        return DailyDishesDto._dish_model

    class DishesGroupCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DishesGroupCreate')
            self.add_argument('name').with_field(fields.String).with_parser(type=min_length_validate('name', 1))

    class DishesGroupModel(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DishesGroupModel')
            self.add_argument('dishes_group_id').with_field(fields.Integer(attribute=lambda x: x.id))
            self.add_argument('name').with_field(fields.String)
            self.add_argument('dishes').with_field(fields.Nested(DailyDishesDto.dish_model().model))

    _dishes_group_create_model = None

    @staticmethod
    def dishes_group_create_model():
        if DailyDishesDto._dishes_group_create_model is None:
            DailyDishesDto._dishes_group_create_model = DailyDishesDto.DishesGroupCreate()

        return DailyDishesDto._dishes_group_create_model

    _dishes_group_model = None

    @staticmethod
    def dishes_group_model():
        if DailyDishesDto._dishes_group_model is None:
            DailyDishesDto._dishes_group_model = DailyDishesDto.DishesGroupModel()

        return DailyDishesDto._dishes_group_model

    class DailyMenuCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DailyMenuCreate')
            self.add_argument('date').with_field(fields.Date).with_parser(type=datetime_parser)
            self.add_argument('diet_id').with_field(fields.Integer).with_parser(type=int)

    class DailyMenuModel(ArgsAggregator):
        def __init__(self):
            super().__init__(DailyDishesDto.api, 'DailyMenuModel')
            self.add_argument('id').with_field(fields.Integer)
            self.add_argument('date').with_field(fields.Date)
            self.add_argument('diet_id').with_field(fields.Integer)
            self.add_argument('breakfast').with_field(fields.List(fields.Nested(DailyDishesDto.dishes_group_model().model),
                                                                  attribute=lambda x: x.breakfast.dishes_lists))
            self.add_argument('lunch').with_field(fields.List(fields.Nested(DailyDishesDto.dishes_group_model().model),
                                                              attribute=lambda x: x.lunch.dishes_lists))
            self.add_argument('supper').with_field(fields.List(fields.Nested(DailyDishesDto.dishes_group_model().model),
                                                               attribute=lambda x: x.supper.dishes_lists))

    _daily_menu_create_model = None

    @staticmethod
    def daily_menu_create_model():
        if DailyDishesDto._daily_menu_create_model is None:
            DailyDishesDto._daily_menu_create_model = DailyDishesDto.DailyMenuCreate()

        return DailyDishesDto._daily_menu_create_model

    _daily_menu_model = None

    @staticmethod
    def daily_menu_model():
        if DailyDishesDto._daily_menu_model is None:
            DailyDishesDto._daily_menu_model = DailyDishesDto.DailyMenuModel()

        return DailyDishesDto._daily_menu_model


class ClientDailyDishesDto:
    api = Namespace('client_daily_dishes')

    class ClientSelectedDishCreate(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientDailyDishesDto.api, 'ClientSelectedDishCreate')
            self.add_argument('daily_menu_id').with_field(fields.Integer).with_parser(type=int)
            meals = ('breakfast', 'lunch', 'supper')
            self.add_argument('meal_tag')\
                .with_field(fields.String(enum=meals))\
                .with_parser(type=str, choices=meals)

            self.add_argument('dishes_group_id').with_field(fields.Integer).with_parser(type=int)
            self.add_argument('dish_id').with_field(fields.Integer).with_parser(type=int)

    _client_selected_dish_create = None

    @staticmethod
    def client_selected_dish_create_model():
        if ClientDailyDishesDto._client_selected_dish_create is None:
            ClientDailyDishesDto._client_selected_dish_create = ClientDailyDishesDto.ClientSelectedDishCreate()

        return ClientDailyDishesDto._client_selected_dish_create

    class ClientSelectedDish(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientDailyDishesDto.api, 'ClientSelectedDish')
            self.add_argument('dish').with_field(fields.Nested(DailyDishesDto.dish_model().model))
            self.add_argument('dishes_group').with_field(fields.Nested(DailyDishesDto.dishes_group_model().model))

    _client_selected_dish = None

    @staticmethod
    def client_selected_dish():
        if ClientDailyDishesDto._client_selected_dish is None:
            ClientDailyDishesDto._client_selected_dish = ClientDailyDishesDto.ClientSelectedDish()

        return ClientDailyDishesDto._client_selected_dish

    class ClientMealSelection(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientDailyDishesDto.api, 'ClientMealSelection')
            self.add_argument('not_selected').with_field(fields.List(fields.Nested(DailyDishesDto.dishes_group_model().model)))
            self.add_argument('selected')\
                .with_field(fields.List(fields.Nested(ClientDailyDishesDto.client_selected_dish().model)))

    _client_meal_selection = None

    @staticmethod
    def client_meal_selection():
        if ClientDailyDishesDto._client_meal_selection is None:
            ClientDailyDishesDto._client_meal_selection = ClientDailyDishesDto.ClientMealSelection()

        return ClientDailyDishesDto._client_meal_selection

    class ClientSelectedDishesModel(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientDailyDishesDto.api, 'ClientSelectedDishModel')
            self.add_argument('daily_menu_id').with_field(fields.Integer)
            self.add_argument('breakfast').with_field(fields.Nested(ClientDailyDishesDto.client_meal_selection().model))
            self.add_argument('lunch').with_field(fields.Nested(ClientDailyDishesDto.client_meal_selection().model))
            self.add_argument('supper').with_field(fields.Nested(ClientDailyDishesDto.client_meal_selection().model))

    _client_selected_dishes_model = None

    @staticmethod
    def client_selected_dishes_model():
        if ClientDailyDishesDto._client_selected_dishes_model is None:
            ClientDailyDishesDto._client_selected_dishes_model = ClientDailyDishesDto.ClientSelectedDishesModel()

        return ClientDailyDishesDto._client_selected_dishes_model

