from dateutil import parser
from flask_restplus import ValidationError


def datetime_parser(string):
    datetime = parser.parse(string)
    return datetime

def min_length_validate(param_name, min_length):
    def validate(s):
        if len(s) >= min_length:
            return s
        raise ValidationError("{} must be at least {} characters long".format(param_name, min_length))
    return validate