from flask import request
# from flask_jwt_extended import get_raw_jwt
from flask_restplus import Resource

from app.main.service.auth_helper import Auth #, login_required
from ..util.dto import AuthDto


api = AuthDto.api
user_auth = AuthDto.user_auth


@api.route('/login')
class UserLogin(Resource):
    """
    User login resourse
    """
    @api.doc('user login')
    @api.expect(user_auth, validate=True)
    def post(self):
        post_data = request.json
        return Auth.login_user(post_data['username'], post_data['password'])


# @api.route('/logout')
# class LogoutAPI(Resource):
#     """
#     User logout resource
#     """
#     @api.doc('logout the user')
#     @login_required
#     def post(self):
#         auth_header = request.headers.get('Authorization')
#         return Auth.logout_user(get_raw_jwt()['jti'])