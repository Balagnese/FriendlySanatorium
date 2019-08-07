from flask_restplus import Namespace, fields
import datetime
import string
from .ArgsCollector import ArgsAggregator, ArgValueError

from .reg_exps import (USERNAME_REGEX, PASSWORD_REGEX,
                       NAME_REGEX, SURNAME_REGEX, PATRONYMIC_REGEX)

from app.main.model.client_profile import Gender


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

            gender_strs = [val.value for val in [Gender.MALE, Gender.FEMALE]]
            self.add_argument('gender')\
                .with_field(fields.String(enum=gender_strs, required=True))\
                .with_parser(type=ClientProfileDto.gender, choices=gender_strs, required=True)

            self.add_argument('time_arrival').with_field(fields.DateTime(required=True))\
                .with_parser(type=datetime.datetime, required=True)

            self.add_argument('time_departure').with_field(fields.DateTime(required=True)) \
                .with_parser(type=datetime.datetime, required=True)

            self.add_argument('diet_id').with_field(fields.Integer(required=True)).with_parser(type=int, required=True)

    class ClientModel(ArgsAggregator):
        def __init__(self):
            super().__init__(ClientProfileDto.api, 'Client')

            self.add_argument('username').with_field(fields.String)

            self.add_argument('public_id').with_field(fields.String)

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












