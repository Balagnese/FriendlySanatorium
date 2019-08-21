from flask_restplus import Resource
from ..util.dto import DailyDishesDto, IDsArray

from flask_restplus import Namespace, fields

from ..service.daily_dishes_service import (get_a_daily_menu, get_all_daily_menu, save_daily_menu, add_dishes_group_to_meal,
                                            get_a_dish, get_all_dishes, save_dish,
                                            get_a_dishes_group, get_all_dishes_groups, save_dishes_group, add_dishes_to_dishes_group)

api = DailyDishesDto.api

dish_create_model = DailyDishesDto.dish_create_model()
dish_model = DailyDishesDto.dish_model()
dishes_group_create_model = DailyDishesDto.dish_create_model()
dishes_group_model = DailyDishesDto.dishes_group_model()
daily_menu_create_model = DailyDishesDto.daily_menu_create_model()
daily_menu_model = DailyDishesDto.daily_menu_model()

ids_arr = IDsArray(api)

@api.route('/')
class DishesList(Resource):
    @api.marshal_list_with(dish_model.model)
    def get(self):
        return get_all_dishes()

    @api.expect(dish_create_model.model)
    @dish_create_model.pass_parsed()
    def post(self, props):
        return save_dish(props['name'], props['description'])


@api.route('/groups')
class DishesGroupsList(Resource):
    @api.marshal_list_with(dishes_group_model.model)
    def get(self):
        return get_all_dishes_groups()

    @api.expect(dishes_group_create_model.model)
    @dishes_group_create_model.pass_parsed()
    def post(self, props):
        return save_dishes_group(props['name'])

@api.route('/groups/<int:id>')
class DishesGroup(Resource):
    @api.expect(ids_arr.model)
    @ids_arr.pass_parsed()
    def put(self, id, props):
        return add_dishes_to_dishes_group(props['ids'], id)


@api.route('/daily_menus')
class DailyMenusList(Resource):
    @api.marshal_list_with(daily_menu_model.model)
    def get(self):
        return get_all_daily_menu()

    @api.expect(daily_menu_create_model.model)
    @daily_menu_create_model.pass_parsed()
    def post(self, props):
        return save_daily_menu(props['diet_id'], props['date'])


@api.route('/daily_menus/<int:id>/<meal_time>')
class DailyMenus(Resource):
    @api.expect(ids_arr.model)
    @api.doc(params={
        'meal_time': {
            'type': 'string',
            'example': 'breakfast',
            'enum': [
                'breakfast',
                'lunch',
                'supper'
            ]
        }
    })
    @ids_arr.pass_parsed()
    def put(self, id, props, meal_time):
        if meal_time not in ['breakfast', 'lunch', 'supper']:
            return {
                'status': 'fail',
                'message': 'invalid meal_time value'
            }, 400

        return add_dishes_group_to_meal(props['ids'], id, meal_time)

