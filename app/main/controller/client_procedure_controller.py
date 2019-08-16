from flask_restplus import Resource
from ..util.dto import ClientProcedureDto
from ..service.client_procedure_service import find_all_client_procedures_for_client, get_a_client, save_client_procedure, remove_client_procedure

from ..util import exceptions as exs
from ..service.auth_helper import login_required, current_user

api = ClientProcedureDto.api
client_procedure_create_model = ClientProcedureDto.client_procedure_create_model()
client_procedure_model = ClientProcedureDto.client_procedure_model()


@api.route('/<client_public_id>/procedures')
class ClientProcedureList(Resource):
    @api.marshal_list_with(client_procedure_model.model)
    @api.response(403, 'User requested is not a client')
    def get(self, client_public_id):
        client = get_a_client(client_public_id)
        if client is None:
            raise exs.EntityNotFoundException('client')
        return find_all_client_procedures_for_client(client)

    @api.expect(client_procedure_create_model.model)
    @client_procedure_create_model.pass_parsed('proc_args')
    def post(self, client_public_id, proc_args):
        return save_client_procedure(client_public_id, proc_args['procedure_id'], proc_args['time'], proc_args['place'])


@api.route('/me/procedures')
class ClientProcedurePersonal(Resource):
    @api.marshal_list_with(client_procedure_model.model)
    @api.response(403, 'User requested is not a client')
    @login_required
    def get(self):
        user = current_user
        if user.client_profile is None:
            return {
                'status': 'fail',
                'message': 'User requested is not a client'
            }, 403

        return find_all_client_procedures_for_client(user.client_profile)


@api.route('/procedures/<client_procedure_id>')
class ClientProceduresRemover(Resource):
    @api.response(404, 'Client procedure not found')
    def delete(self, client_procedure_id):
        return remove_client_procedure(client_procedure_id)

