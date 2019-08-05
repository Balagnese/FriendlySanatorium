from app.main import db
from app.main.model.client_profile import ClientProfile, Gender
from app.main.model.user import User


def save_client(user_public_id, is_child, gender, birthday, time_arrival, time_departure):
    user = User.query.filter_by(public_id=user_public_id).first()
    if not user:
        return {
            'status': 'fail',
            'message': 'User {} not found'.format(user_public_id)
        }, 404

    client = ClientProfile.query.filter_by(user=user).first()
    if client:
        return {
            'status': 'fail',
            'message': 'User {} is already a client'.format(user_public_id)
        }, 400

    client = ClientProfile(user=user,
                           gender=gender,
                           is_child=is_child,
                           birthday=birthday,
                           time_arrival=time_arrival,
                           time_departure=time_departure)
    db.session.add(client)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'Client successfully created'
    }, 200

def get_all_clients():
    return ClientProfile.query.all()

def get_a_client(public_id):
    return ClientProfile.query.filter_by(ClientProfile.user.public_id.in_((public_id,))).first()