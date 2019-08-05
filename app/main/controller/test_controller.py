from flask_restplus import Resource, Namespace

api = Namespace('test', description='only for test')

@api.route('/')
class Test(Resource):
    def get(self):
        return {'message': 'hello you'}