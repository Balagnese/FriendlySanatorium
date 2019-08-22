from flask_restplus import Resource
from ..util.dto import ProcedureDto
from ..service.procedure_service import get_a_procedure, get_procedures, remove_procedure, save_procedure
from ..util.exceptions import EntityNotFoundException

api = ProcedureDto.api
procedure_create_model = ProcedureDto.procedure_create_model()
procedure_model = ProcedureDto.procedure_model()


@api.route('/')
class ProcedureList(Resource):
    @api.marshal_list_with(procedure_model.model)
    def get(self):
        """ Get all available procedures """
        return get_procedures()

    @api.expect(procedure_create_model.model)
    @procedure_create_model.pass_parsed('procedure_params')
    @api.response(201, 'procedure successfully created')
    def post(self, procedure_params):
        """ Create new Procedure """
        return save_procedure(procedure_params['name'], procedure_params['description'], procedure_params['places'], procedure_params['duration'])


@api.route('/<procedure_id>')
class Procedure(Resource):
    @api.marshal_with(procedure_model.model)
    @api.response(404, 'Procedure with passed id not found')
    def get(self, procedure_id):
        """ Get procedure by id """
        procedure = get_a_procedure(procedure_id)
        if not procedure:
            raise EntityNotFoundException('procedure')
        return procedure

    @api.response(200, 'procedure successfully deleted')
    @api.response(404, 'procedure with passed id not found')
    def delete(self, procedure_id):
        """ Delete procedure by id """
        return remove_procedure(procedure_id)