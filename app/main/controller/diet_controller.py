from flask_restplus import Resource
from ..util.dto import DietDto
from ..service.diet_service import save_diet, get_a_diet, get_all_diets, delete_a_diet

api = DietDto.api
diet_create_model = DietDto.diet_create_model()
diet_model = DietDto.diet_model()


@api.route('/')
class DietList(Resource):
    @api.marshal_list_with(diet_model.model)
    @api.response(200, 'All diets successfully returned')
    def get(self):
        """ Get all available diets """
        return get_all_diets()

    @api.expect(diet_create_model.model)
    @diet_create_model.pass_parsed()
    @api.response(201, 'Diet successfully created')
    def post(self, props):
        """ Create new diet """
        return save_diet(props['name'], props['description'])


@api.route('/<diet_id>')
class Diet(Resource):
    @api.marshal_with(diet_model.model)
    @api.response(404, 'diet not found')
    @api.response(200, 'Diet successfully found and returned')
    def get(self, diet_id):
        """ Get diet by id """
        diet = get_a_diet(diet_id)
        if diet is None:
            api.abort(404)
        return diet

    @api.response(404, 'diet not found')
    @api.response(200, 'diet deleted')
    def delete(self, diet_id):
        """ Delete diet by id """
        return delete_a_diet(diet_id)
