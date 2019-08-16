from flask_admin.contrib.sqla import ModelView

from .. import db


class ClientProcedure(db.Model):
    __tablename__ = 'client_procedure'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    client_id = db.Column(db.Integer, db.ForeignKey('client_profile.id'), nullable=False)
    client = db.relationship('ClientProfile')

    procedure_id = db.Column(db.Integer, db.ForeignKey('procedure.id'), nullable=False)
    procedure = db.relationship('Procedure')

    place = db.Column(db.String, nullable=False)
    time = db.Column(db.DateTime, nullable=False)


class AdminModelClientProcedure(ModelView):
    column_hide_backrefs = False