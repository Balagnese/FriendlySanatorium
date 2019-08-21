from app.main import db
from app.main.model.client_profile import ClientProfile

from ..util import exceptions as exs

from ..service.user_service import get_a_user
from ..service.diet_service import get_a_diet


def save_client(user_public_id, name, surname, patronymic, is_child, gender, birthday, time_arrival, time_departure, diet_id):
    user = get_a_user(user_public_id)
    if not user:
        raise exs.EntityNotFoundException('user')

    diet = get_a_diet(diet_id)
    if not diet:
        raise exs.EntityNotFoundException('diet')

    client = ClientProfile.query.filter_by(user=user).first()
    if client:
        return {
            'status': 'fail',
            'message': 'User {} is already a client'.format(user_public_id)
        }, 400

    client = ClientProfile(user=user,
                           name=name,
                           surname=surname,
                           patronymic=patronymic,
                           diet=diet,
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
    user = get_a_user(public_id)
    if not user:
        return None
    return user.client_profile


def get_children_of(client):
    return client.children