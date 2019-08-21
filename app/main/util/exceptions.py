class EntityNotFoundException(Exception):
    def __init__(self, name, message=''):
        self.name = name
        self.message = message
        if not message:
            self.message = '{} not found'.format(name)


class InvalidArgumentFormat(Exception):
    def __init__(self, name, message=''):
        self.name = name
        self.message = message
        if not message:
            self.message = '{} has invalid format'.format(name)