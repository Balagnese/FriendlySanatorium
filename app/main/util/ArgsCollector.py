from flask_restplus import Namespace
from flask_restplus.reqparse import RequestParser
from flask import copy_current_request_context
from werkzeug import exceptions
from functools import wraps


class ArgValueError(Exception):
    def __init__(self, code, *args, **kwargs):
        super().__init__(*args, *kwargs)
        self.code = code


class ArgsAggregator:
    def __init__(self, api: Namespace, model_name):
        self._api = api
        self._name = model_name
        self._parsing_args = {}
        self._fields = {}
        self._checkers = {}
        self._parser = None
        self.args = dict()

    class Argument:
        def __init__(self, args_aggregator, name):
            self.name = name
            self._arg_aggregator: ArgsAggregator = args_aggregator

        def with_field(self, field):
            self._arg_aggregator._fields[self.name] = field
            return self

        def with_parser(self, *args, **kwargs):
            self._arg_aggregator._parsing_args[self.name] = args, kwargs
            return self

        def with_value_check(self, checking_fun):
            self._arg_aggregator._checkers[self.name] = checking_fun
            return self

    def add_argument(self, name):
        return ArgsAggregator.Argument(self, name)

    @property
    def model(self):
        return self._api.model(self._name, self._fields)

    def parse(self):
        if self._parser is None:
            self._parser = self._create_parser(self)

        # @copy_current_request_context
        # def parse_args():
        #     return self._parser.parse_args()

        self.args = self._parser.parse_args()

        checking_errors = {}

        for checking, checker in self._checkers.items():
            try:
                checker[checking](checking, self.args[checking])
            except ArgValueError as e:
                checking_errors[checking] = {'message': str(e), 'code': e.code}
            # except Exception as e:
            #     checking_errors[checking] = {'message': str(e), 'code': 422}

        if len(checking_errors) != 0:
            # e = exceptions.BadRequest()
            e = exceptions.UnprocessableEntity()
            e.data = {
                'status': 'fail',
                'message': 'some properties failed value checks',
                'errors': checking_errors
            }
            raise e

        args = self.args
        self.args = dict()
        return args

    def pass_parsed(self, placeholder='props'):
        def out_wrapper(fun):
            @wraps(fun)
            def wrapper(*args, **kwargs):
                parser_args = self.parse()
                kwargs[placeholder] = parser_args
                return fun(*args, **kwargs)
            return wrapper
        return out_wrapper

    @staticmethod
    def _create_parser(self):
        p: RequestParser = RequestParser(bundle_errors=True)
        for arg in self._parsing_args:
            p.add_argument(arg, *self._parsing_args[arg][0], **self._parsing_args[arg][1])
        return p

