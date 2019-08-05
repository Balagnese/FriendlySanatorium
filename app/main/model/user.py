from .. import db, flask_bcrypt
from flask_admin.contrib.sqla import ModelView


class User(db.Model):
    """User model for storing user related details"""
    __tablename__ = 'user'

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    registered_on = db.Column(db.DateTime, nullable=False)
    admin = db.Column(db.Boolean, nullable=False, default=False)
    public_id = db.Column(db.String(100), unique=True)
    username = db.Column(db.String(50), unique=True)
    password_hash = db.Column(db.String(100))

    @property
    def password(self):
        raise AttributeError('password: write-only field')

    @password.setter
    def password(self, password):
        self.password_hash = flask_bcrypt.generate_password_hash(password).decode('utf-8')

    def check_password(self, password):
        return flask_bcrypt.check_password_hash(self.password_hash, password)

    def __repr__(self):
        return "<User '{}'>".format(self.username)


class AdminModelUser(ModelView):
    form_excluded_columns = ['password_hash', 'client_profile']
    form_widget_args = {
        'public_id': {
            'disabled': True
        },
        'registered_on': {
            'disabled': True
        }
    }