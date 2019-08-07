from .. import db
# from sqlalchemy.dialects.postgresql import JSON
from flask_admin.contrib.sqla import ModelView


class Procedure(db.Model):
    __tablename__ = 'procedure'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    name = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String, nullable=False)

    places = db.Column(db.JSON, default=[])

    def __repr__(self):
        return '<Procedure {}>'.format(self.name)


class AdminModelProcedure(ModelView):
    pass