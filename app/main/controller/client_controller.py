from flask_restplus import Resource

from ..util.dto import ClientProfileDto
from ..service.client_service import get_a_client, save_client, get_all_clients
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

    @api.expect(client_model.model)
    @client_create_model.pass_parsed()
    def post(self, props):
        return save_client(props['public_id'], props['is_child'], props['gender'],
                           props['birthday'], props['time_arrival'], props['time_departure'])


@api.route('/<public_id>')
class Client(Resource):
    @api.doc('get a client')
    @api.marshal_with(client_model.model)
    def get(self, public_id):
        """ Get a client by public id """
        client = get_a_client(public_id)
        if not client:
            exs.EntityNotFoundException('client')
        return client
