from ..model.procedure import Procedure
from .. import db
from ..util.exceptions import EntityNotFoundException


def save_procedure(name, description, places):
    try:
        procedure = Procedure(name=name, description=description, places=places)

        db.session.add(procedure)
        db.session.commit()
        return {
            'status': 'success',
            'message': 'Procedure successfully created'
        }, 201
    except Exception as e:
        return {
            'status': 'fail',
            'message': str(e)
        }, 500


def get_procedures():
    return Procedure.query.all()


def get_a_procedure(procedure_id):
    return Procedure.query.filter_by(id=procedure_id).first()


def remove_procedure(procedure_id):
    procedure = Procedure.query.filter_by(id=procedure_id).first()

    if not procedure:
        raise EntityNotFoundException('procedure')

    db.session.delete(procedure)
    db.session.commit()
    return {
        'status': 'success',
        'message': 'procedure successfully deleted'
    }, 200
