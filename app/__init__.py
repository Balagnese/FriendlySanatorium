from flask_jwt_extended import JWTManager
from flask_restplus import Api
from flask import Blueprint
from flask_admin import Admin
from flask_admin.contrib.sqla import ModelView
from .main import db

blueprint = Blueprint('api', __name__)

authorizations = {
    'Bearer Auth': {
        'type': 'apiKey',
        'in': 'header',
        'name': 'Authorization'
    }
}

api = Api(
    blueprint,
    title='Friendly Sanatorium api with jwt',
    version='1.0',
    doc='/doc',
    prefix='/api',
    description='test api for Friendly Sanatorium',
    authorizations=authorizations
)

jwt = JWTManager()
admin = Admin()

from .main.model.diet import Diet, AdminModelDiet
from .main.model.user import User, AdminModelUser
from .main.model.client_profile import ClientProfile, AdminModelClientProfile
from .main.model.procedure import Procedure, AdminModelProcedure
from .main.model.client_procedure import ClientProcedure,AdminModelClientProcedure

admin.add_view(AdminModelUser(User, db.session))
admin.add_view(AdminModelDiet(Diet, db.session))
admin.add_view(AdminModelClientProfile(ClientProfile, db.session))
admin.add_view(AdminModelProcedure(Procedure, db.session))
admin.add_view(AdminModelClientProcedure(ClientProcedure, db.session))

from .main.controller.user_controller import api as user_ns
from .main.controller.auth_controller import api as auth_ns
from .main.controller.client_controller import api as client_ns
from .main.controller.diet_controller import api as diet_ns
from .main.controller.procedure_controller import api as procedure_ns
from .main.controller.client_procedure_controller import api as client_procedure_ns

from .main.controller.test_controller import api as test_ns

print('loading namespaces')
# print('aaa '+str(user_ns))

api.add_namespace(user_ns, path='/users')
api.add_namespace(auth_ns, path='/auth')
api.add_namespace(client_ns, path='/clients')
api.add_namespace(diet_ns, path='/diets')
api.add_namespace(procedure_ns, path='/procedures')
api.add_namespace(client_procedure_ns, path='/clients')

api.add_namespace(test_ns, path='/test')
