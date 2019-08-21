from flask_jwt_extended import current_user
from flask_restplus import Resource
from ..util.dto import DailyDishesDto, IDsArray

from ..service.client_daily_dishes_service import get_client_selected_dishes_grouped, remove_a_client_selected_dish, save_client_selected_dish
from ..service.daily_dishes_service import get_a_daily_menu_by_day
from ..service.client_service import get_a_client
from ..service.auth_helper import login_required

from ..util.dto import ClientDailyDishesDto
from ..util.helpers import datetime_parser
from ..util.exceptions import EntityNotFoundException, InvalidArgumentFormat

api = ClientDailyDishesDto.api

selection_create_model = ClientDailyDishesDto.client_selected_dish_create_model()
selected_dishes = ClientDailyDishesDto.client_selected_dishes_model()


@api.route('/<public_id>/dishes/<date>')
class ClientSelectedDishesGetter(Resource):
    @api.marshal_with(selected_dishes.model)
    def get(self, public_id, date):
        client = get_a_client(public_id)
        if not client:
            raise EntityNotFoundException('client')
        try:
            _date = datetime_parser(date)
        except:
            raise InvalidArgumentFormat('date')
        return get_client_selected_dishes_grouped(client, _date)


@api.route('/<public_id>/dishes')
class ClientSelectedDishesCreater(Resource):
    @api.expect(selection_create_model.model)
    @selection_create_model.pass_parsed()
    def post(self, public_id, props):
        client = get_a_client(public_id)
        if not client:
            raise EntityNotFoundException('client')
        return save_client_selected_dish(client, props['daily_menu_id'], props['meal_tag'], props['dishes_group_id'], props['dish_id'])


@api.route('/me/dishes/<date>')
class ClientSelectedDishesSelfGetter(Resource):
    @api.marshal_with(selected_dishes.model)
    @login_required
    def get(self, date):
        user = current_user
        client = user.client_profile
        if client is None:
            return {
                       'status':  'fail',
                       'message': 'User requested is not a client'
                   }, 403
        try:
            _date = datetime_parser(date)
        except:
            raise InvalidArgumentFormat('date')

        return get_client_selected_dishes_grouped(client, _date)


@api.route('/me/dishes')
class ClientSelectedDishesCreate(Resource):
    @api.expect(selection_create_model.model)
    @login_required
    @selection_create_model.pass_parsed()
    def post(self, props):
        user = current_user
        client = user.client_profile
        if client is None:
            return {
                       'status':  'fail',
                       'message': 'User requested is not a client'
                   }, 403
        return save_client_selected_dish(client, props['daily_menu_id'], props['meal_tag'], props['dishes_group_id'], props['dish_id'])