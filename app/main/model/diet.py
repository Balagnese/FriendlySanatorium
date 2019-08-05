from .. import db
from flask_admin.contrib.sqla import ModelView


class Diet(db.Model):
    """ Diet model to store info about all available diets"""
    __tablename__ = 'diet'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    name = db.Column(db.String(100), nullable=False)
    description = db.Column(db.String(400))


class AdminModelDiet(ModelView):
    pass