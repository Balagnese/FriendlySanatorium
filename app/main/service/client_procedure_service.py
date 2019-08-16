from app.main import db
from ..model.client_procedure import ClientProcedure
from .client_service import get_a_client
from .procedure_service import get_a_procedure
from ..util import exceptions as exs


def find_all_client_procedures_for_client(client):
    return  ClientProcedure.query.filter_by(client_id=client.id).all()


def save_client_procedure(client_id, procedure_id, time, place):
    client = get_a_client(client_id)
    if not client:
        raise exs.EntityNotFoundException('client')
    procedure = get_a_procedure(procedure_id)
    if not procedure:
        raise exs.EntityNotFoundException('procedure')

    cl_proc = ClientProcedure(client=client, procedure=procedure, time=time, place=place)

    db.session.add(cl_proc)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'client procedure successfully created'
    }, 201


def get_a_client_procedure(client_procedure_id):
    return ClientProcedure.query.filter_by(id=client_procedure_id).first()


def remove_client_procedure(client_procedure_id):
    cl_proc = get_a_client_procedure(client_procedure_id)
    if not cl_proc:
        raise exs.EntityNotFoundException('client_procedure')

    db.session.delete(cl_proc)
    db.session.commit()

    return {
        'status': 'success',
        'message': 'client_procedure successfully deleted'
    }, 200
