from flask import request
from flask_restplus import Resource

from ..util.dto import UserDto
from ..service.user_service import save_new_user, get_all_users, get_a_user
from ..util import exceptions as exs


api = UserDto.api
_user = UserDto.user


@api.route('/')
class UserList(Resource):
    @api.doc('list of registered users')
    @api.marshal_list_with(_user)
    def get(self):
        """List of all registered users"""
        return get_all_users()

    @api.response(201, 'User successfully created')
    @api.doc('create a new user')
    @api.expect(_user, validate=True)
    def post(self):
        """Create new user"""
        data = request.json
        return save_new_user(data['username'], data['password'])


@api.route('/<public_id>')
@api.param('public_id', 'The User identifier')
@api.response(404, 'User not found')
class User(Resource):
    @api.doc('get a user')
    @api.marshal_with(_user)
    def get(self, public_id):
        """Get user by public_id"""
        user = get_a_user(public_id)
        if not user:
            exs.EntityNotFoundException('user')
        else:
            return user
