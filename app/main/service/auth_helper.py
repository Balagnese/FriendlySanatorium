from functools import wraps

from flask import jsonify, Response
from flask_jwt_extended import get_jwt_identity, verify_jwt_in_request, current_user, jwt_required

from app.main.model.user import User
from ..service.blacklist_service import save_token, BlacklistToken

import flask_jwt_extended.exceptions as jwt_extended_exception
import jwt.exceptions as jwt_exception
from flask_jwt_extended import create_access_token, create_refresh_token

from app.main.util.dto import AuthDto
from app import jwt
import json

from ..util import exceptions as exs

api = AuthDto.api

class Auth:
    @staticmethod
    def login_user(username, password):
        try:
            user = User.query.filter_by(username=username).first()
            if user and user.check_password(password):

                token = create_access_token(user.id)
                # refresh_token = create_refresh_token(user.id)

                response_object = {
                    'status': 'success',
                    'message': 'Successfully logged in.',
                    'token': token
                }
                return response_object, 200
            else:
                response_object = {
                    'status': 'fail',
                    'message': 'username or password does not match.'
                }
                return response_object, 401

        except Exception as e:
            print(e)
            response_object = {
                'status': 'fail',
                'message': 'Try again.'
            }
            return response_object, 500

    @staticmethod
    def logout_user(jti):
        return save_token(jti)


def admin_required(fn):
    @wraps(fn)
    @api.doc(security='Bearer Auth')
    def wrapper(*args, **kwargs):
        verify_jwt_in_request()
        u: User = current_user
        if not u:
            resp = Response(json.dumps({'message': 'Invalid access token'}), 401)
            return resp
        if not u.admin:
            resp = Response(json.dumps({'message': 'Only admins allowed'}), 403)
            return resp
        return fn(*args, **kwargs)
    return wrapper


def login_required(fn):
    @wraps(fn)
    @api.doc(security='Bearer Auth')
    def wrapper(*args, **kwargs):
        return jwt_required(fn)(*args, **kwargs)
    return wrapper



@jwt.token_in_blacklist_loader
def _check_if_token_in_blacklist(decrypted_token):
    # print('decripted token')
    # print(decrypted_token)
    jti = decrypted_token['jti']
    res = BlacklistToken.check_blacklist(jti)
    if res:
        return True
    else:
        return False
    # TODO Check for password changes
    # u: User = User.query.filter_by(login=decrypted_token['identity']).first()
    # return u.is_token_valid(decrypted_token)


@jwt.user_loader_callback_loader
def _load_user(identity):
    u = User.query.filter_by(id=identity).first()
    return u


@jwt.expired_token_loader
def _token_expired(expired_token):
    token_type = expired_token['type']
    return jsonify({
        'status': 'fail',
        'msg': 'The {} token has expired'.format(token_type)
    }), 401


@api.errorhandler(jwt_extended_exception.NoAuthorizationError)
def _handle_auth_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 401


@api.errorhandler(jwt_extended_exception.CSRFError)
def _handle_auth_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 401


@api.errorhandler(jwt_exception.ExpiredSignatureError)
def _handle_expired_error(e):
    return {
               'status': 'fail',
               'message': 'Token has expired'
           }, 401


@api.errorhandler(jwt_extended_exception.InvalidHeaderError)
def _handle_invalid_header_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 422


@api.errorhandler(jwt_exception.InvalidTokenError)
def _handle_invalid_token_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 422


@api.errorhandler(jwt_extended_exception.JWTDecodeError)
def _handle_jwt_decode_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 422


@api.errorhandler(jwt_extended_exception.WrongTokenError)
def _handle_wrong_token_error(e):
    return {
               'status': 'fail',
               'message': str(e)
           }, 422


@api.errorhandler(jwt_extended_exception.RevokedTokenError)
def _handle_revoked_token_error(e):
    return {
               'status': 'fail',
               'message': 'Token has been revoked'
           }, 401


@api.errorhandler(jwt_extended_exception.FreshTokenRequired)
def _handle_fresh_token_required(e):
    return {
               'status': 'fail',
               'message': 'Fresh token required'
           }, 401


@api.errorhandler(jwt_extended_exception.UserLoadError)
def _handler_user_load_error(e):
    # The identity is already saved before this exception was raised,
    # otherwise a different exception would be raised, which is why we
    # can safely call get_jwt_identity() here
    identity = get_jwt_identity()
    return {
               'status': 'fail',
               'message': "Error loading the user {}".format(identity)
           }, 401


@api.errorhandler(jwt_extended_exception.UserClaimsVerificationError)
def _handle_failed_user_claims_verification(e):
    return {
               'status': 'fail',
               'message': 'User claims verification failed'
           }, 400

@api.errorhandler(exs.EntityNotFoundException)
def _handle_failed_to_find_entity(e: exs.EntityNotFoundException):
    return {
        'status': 'fail',
        'message': e.message
    }, 404