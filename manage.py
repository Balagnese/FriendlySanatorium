import os
import unittest

from flask_migrate import Migrate, MigrateCommand
from flask_script import Manager
from flask_jwt_extended import JWTManager
import psycopg2
from flask import render_template, request

from app.main import create_app, db

from app.main.model import user
from app.main.model import blacklist
from app import blueprint, jwt, admin

app = create_app(os.getenv('FRIENDLY_SANATORIUM_ENV') or 'dev')
app.register_blueprint(blueprint)
jwt.init_app(app)
admin.init_app(app, url='/admin')
app.app_context().push()

manager = Manager(app)

migrate = Migrate(app, db)

manager.add_command('db', MigrateCommand)

@app.route('/')
def docs_page():
    doc_url = request.base_url + 'doc'
    admin_url = request.base_url + 'admin'
    context = {
        'doc_url': doc_url,
        'admin_url': admin_url
    }
    print(context)
    return render_template('index.html', **context)


@manager.command
def run():
    app.run()


@manager.command
def test():
    """Runs unit tests"""

    tests = unittest.TestLoader().discover('./app/test', pattern='test*.py')
    result = unittest.TextTestRunner(verbosity=2).run(tests)

    if result.wasSuccessful():
        return 0
    return 1


if __name__ == '__main__':
    manager.run()
    # app.run()