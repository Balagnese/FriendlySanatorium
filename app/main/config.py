import os

basedir = os.path.abspath(os.path.dirname(__file__))


class Config:
    SECRET_KEY = os.getenv('SECRET_KEY', 'my-secret-key')
    DEBUG = False

    JWT_SECRET_KEY = SECRET_KEY
    PROPAGATE_EXCEPTIONS=True
    JWT_BLACKLIST_ENABLED = True
    JWT_BLACKLIST_TOKEN_CHECKS = ['access', 'refresh']
    JWT_TOKEN_LOCATION = ['headers']
    JWT_COOKIE_SECURE = False
    JWT_COOKIE_CSRF_PROTECT = False
    ACCESS_TOKEN_NAME = 'access_token'
    REFRESH_TOKEN_NAME = 'refresh_token'
    ERROR_INCLUDE_MESSAGE = 'False'



class DevelopmentConfig(Config):
    DEBUG = True
    # SQLALCHEMY_DATABASE_URI = 'sqlite:///'+os.path.join(basedir, 'my_database_main.db')
    SQLALCHEMY_DATABASE_URI = 'postgresql+psycopg2://localhost/friendlysanatoriumdbmain'
    SQLALCHEMY_TRACK_MODIFICATIONS = False


class TestingConfig(Config):
    DEBUG = True
    TESTING = True
    SQLALCHEMY_DATABASE_URI = 'postgresql+psycopg2://localhost/friendlyfanatoriumdbtest'
    # SQLALCHEMY_DATABASE_URI = 'sqlite:///'+os.path.join(basedir, 'my_database_test.db')
    PRESERVE_CONTEXT_ON_EXCEPTION = False
    SQLALCHEMY_TRACK_MODIFICATIONS = False


class ProductionConfig(Config):
    DEBUG = False


config_by_name = dict(
    dev=DevelopmentConfig,
    test=TestingConfig,
    prod=ProductionConfig
)

key = Config.SECRET_KEY
