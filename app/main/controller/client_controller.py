from flask_restplus import Resource

from ..service.auth_helper import login_required, current_user
from ..util.dto import ClientProfileDto
from ..service.client_service import get_a_client, save_client, get_all_clients, get_children_of
from ..util import exceptions as exs

api = ClientProfileDto.api

client_model = ClientProfileDto.client_model()
client_create_model = ClientProfileDto.client_create_model()


@api.route('/')
class ClientsList(Resource):
    @api.marshal_list_with(client_model.model)
    def get(self):
        """ List all created clients """
        return get_all_clients()

    @api.expect(client_create_model.model)
    @client_create_model.pass_parsed()
    def post(self, props):
        return save_client(props['public_id'], props['name'], props['surname'], props.get('patronymic', ''),
                           props['is_child'], props['gender'],
                           props['birthday'], props['time_arrival'], props['time_departure'],
                           props['diet_id'])


@api.route('/me')
class ClientMe(Resource):
    @api.response(403, 'User requested is not a client')
    @api.marshal_with(client_model.model)
    @login_required
    def get(self):
        user = current_user
        if user.client_profile is None:
            return {
                'status': 'fail',
                'message': 'User requested is not a client'
            }, 403
        return user.client_profile


@api.route('/me/children')
class ClientChildren(Resource):
    @api.response(403, 'User requested is not a client')
    @api.marshal_list_with(client_model.model)
    @login_required
    def get(self):
        user = current_user
        client = user.client_profile
        if client is None:
            return {
                       'status':  'fail',
                       'message': 'User requested is not a client'
                   }, 403
        return get_children_of(client)


@api.route('/<client_public_id>')
class Client(Resource):
    @api.doc('get a client')
    @api.marshal_with(client_model.model)
    def get(self, client_public_id):
        """ Get a client by public id """
        client = get_a_client(client_public_id)
        if not client:
            exs.EntityNotFoundException('client')
        return client
