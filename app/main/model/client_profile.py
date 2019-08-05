from .. import db
import enum
from flask_admin.contrib.sqla import ModelView


parent_to_child = db.Table('parent_to_child', db.Model.metadata,
                           db.Column('parent_id', db.Integer, db.ForeignKey('client_profile.id'), primary_key=True),
                           db.Column('child_id', db.Integer, db.ForeignKey('client_profile.id'), primary_key=True)
                           )


class Gender(enum.Enum):
    MALE = 'male'
    FEMALE = 'female'


class ClientProfile(db.Model):
    """Client model for storing client related details"""
    __tablename__ = 'client_profile'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)

    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)
    user = db.relationship("User", backref=db.backref('client_profile', uselist=False))

    name = db.Column(db.String(50), nullable=False)
    surname = db.Column(db.String(50), nullable=False)
    patronymic = db.Column(db.String(50))

    gender = db.Column(db.Enum(Gender), nullable=False)

    is_child = db.Column(db.Boolean, nullable=False, default=False)

    birthday = db.Column(db.Date)

    time_arrival = db.Column(db.DateTime, nullable=False)
    time_departure = db.Column(db.DateTime, nullable=False)

    diet_id = db.Column(db.Integer, db.ForeignKey('diet.id'), nullable=False)
    diet = db.relationship('Diet')

    parents = db.relationship("ClientProfile",
                               secondary=parent_to_child,
                               # lazy='subquery',
                               primaryjoin=id==parent_to_child.c.parent_id,
                               secondaryjoin=id==parent_to_child.c.child_id,
                               backref=db.backref('children')
                               )


class AdminModelClientProfile(ModelView):
    column_hide_backrefs = False
