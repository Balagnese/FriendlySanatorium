from flask import Flask
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt

from .config import config_by_name

db = SQLAlchemy()
flask_bcrypt = Bcrypt()



def create_app(config_name):
    import os
    path = os.path.join(os.getcwd(), 'app', 'main', 'templates')
    print('templates path: '+ str(path))
    app = Flask(__name__, template_folder=path)
    app.config.from_object(config_by_name[config_name])
    db.init_app(app)
    flask_bcrypt.init_app(app)

    return app