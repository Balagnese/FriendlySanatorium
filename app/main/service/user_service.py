import uuid
import datetime

from flask_jwt_extended import create_access_token, create_refresh_token

from app.main import db
from app.main.model.user import User


def save_new_user(username, password):
    user = User.query.filter_by(username=username).first()
    if not user:
        new_user = User(
            public_id=str(uuid.uuid4()),
            username=username,
            password=password,
            registered_on=datetime.datetime.utcnow()
        )
        save_changes(new_user)

        access_token = create_access_token(new_user.id)
        refresh_token = create_refresh_token(new_user.id)

        return {
            'status': 'success',
            'message': 'Successfully registered',
            'access_token': access_token,
            'refresh_token': refresh_token
        }, 201
    else:
        response_object = {
            'status': 'fail',
            'message': 'User already exists. Please log in.'
        }
        return response_object, 409


def get_all_users():
    return User.query.all()


def get_a_user(public_id):
    return User.query.filter_by(public_id=public_id).first()


def save_changes(data):
    db.session.add(data)
    db.session.commit()


# def generate_token(user):
#     try:
#         auth_token = user.encode_auth_token(user.id)
#         response_object = {
#             'status': 'success',
#             'message': 'Successfully registered.',
#             'Authorization': auth_token.encode()
#         }
#         return response_object, 201
#     except Exception as e:
#         response_object = {
#             'status': 'fail',
#             'message': 'Some errors occured. Please try again.'
#         }
#         return response_object, 401
