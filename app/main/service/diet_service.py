from ..model.diet import Diet
from .. import db
from ..util import exceptions as exs


def save_diet(name, description):
    diet = Diet(name=name, description=description)
    db.session.add(diet)
    db.session.commit()
    respose_object = {
        'status': 'success',
        'message': 'Diet created successfully'
    }
    return respose_object, 201


def get_all_diets():
    return Diet.query.all()


def get_a_diet(diet_id):
    return Diet.query.filter_by(id=diet_id).first()


def delete_a_diet(diet_id):
    diet = get_a_diet(diet_id)
    if not diet:
        raise exs.EntityNotFoundException('diet')

    db.session.delete(diet)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'Diet successfully deleted'
    }, 200
