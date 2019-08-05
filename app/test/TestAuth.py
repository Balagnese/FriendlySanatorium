import unittest
import json
from app.test.base import BaseTestCase
from app.main.model.blacklist import BlacklistToken
from app.main import db


def register_user(self):
    return self.client.post(
        '/user/',
        data=json.dumps(
            {
            'username': 'test test name',
            'password': '123456'
            }
        ),
        content_type='application/json'
    )


def login_user(self):
    return self.client.post(
        '/auth/login',
        data=json.dumps(
            {
                'username': 'test test name',
                'password': '123456'
            }
        ),
        content_type='application/json'
    )


class TestAuthBlueprint(BaseTestCase):

    def test_registration(self):
        """ Test for user registration """
        with self.client:
            response = register_user(self)
            data = json.loads(response.data.decode())
            self.assertTrue(data['status'] == 'success')
            self.assertTrue(data['message']=='Successfully registered.')
            self.assertTrue(data['Authorization'])
            self.assertTrue(response.content_type == 'application/json')
            self.assertEqual(response.status_code, 201)


    def test_registration_with_already_registered_user(self):
        """ Test registration with already regustered username """
        register_user(self)
        with self.client:
            response = register_user(self)
            data = json.loads(response.data.decode())
            self.assertTrue(data['status'] == 'fail')
            self.assertTrue(data['message'] == 'User already exists. Please log in')
            self.assertTrue(response.content_type == 'application/json')
            self.assertEqual(response.status_code, 409)


    def test_registered_user_login(self):
        """ test login of registered user login """

        with self.client:
            # user registration
            resp_registered = register_user(self)
            data_registered = json.loads(resp_registered.data.decode())
            self.assertTrue(data_registered['status'] == 'success')
            self.assertTrue(data_registered['message'] == 'Successfully registered.')
            self.assertTrue(data_registered['Authorization'])
            self.assertTrue(resp_registered.content_type == 'application/json')
            self.assertEqual(resp_registered.status_code, 201)

            # registered user login
            login_response = login_user(self)
            data = json.loads(login_response.data.decode())
            self.assertTrue(data['status'] == 'success')
            self.assertTrue(data['message'] == 'Successfully logged in.')
            self.assertTrue(data['Authorization'])
            self.assertTrue(login_response.content_type == 'application/json')
            self.assertEqual(login_response.status_code, 200)


    def test_non_registered_user_login(self):
        """ Testing for login of non-registered user"""
        with self.client:
            response = login_user(self)
            data = json.loads(response.data.decode())
            self.assertTrue(data['status'] == 'fail')
            # не проверяю сообщение
            self.assertTrue(response.content_type == 'application/json')
            self.assertEqual(response.status_code, 401)


    def test_valid_logout(self):
        """ test for logout before token expiration """
        with self.client:
            # user registration
            resp_registered = register_user(self)
            data_registered = json.loads(resp_registered.data.decode())
            self.assertTrue(data_registered['status'] == 'success')
            self.assertTrue(data_registered['message'] == 'Successfully registered.')
            self.assertTrue(data_registered['Authorization'])
            self.assertTrue(resp_registered.content_type == 'application/json')
            self.assertEqual(resp_registered.status_code, 201)

            # registered user login
            resp_login = login_user(self)
            data_login = json.loads(resp_login.data.decode())
            self.assertTrue(data_login['status'] == 'success')
            self.assertTrue(data_login['message'] == 'Successfully logged in.')
            self.assertTrue(data_login['Authorization'])
            self.assertTrue(resp_login.content_type == 'application/json')
            self.assertEqual(resp_login.status_code, 200)

            # valid token logout
            response = self.client.post(
                '/auth/logout',
                headers={
                    'Authorization': 'Bearer ' + json.loads(
                        resp_login.data.decode()
                    )['Authorization']
                }
            )

            data = json.loads(response.data.decode())
            self.assertTrue(data['status'] == 'success')
            self.assertTrue(data['message'] == 'Successfully logged out.')
            self.assertEqual(response.status_code, 200)

    def test_valid_blacklisted_token_logout(self):
        """ test for logout after a valid token gets blacklisted """
        with self.client:
            # user registration
            resp_registered = register_user(self)
            data_registered = json.loads(resp_registered.data.decode())
            self.assertTrue(data_registered['status'] == 'success')
            self.assertTrue(data_registered['message'] == 'Successfully registered.')
            self.assertTrue(data_registered['Authorization'])
            self.assertTrue(resp_registered.content_type == 'application/json')
            self.assertEqual(resp_registered.status_code, 201)

            # registered user login
            resp_login = login_user(self)
            data_login = json.loads(resp_login.data.decode())
            self.assertTrue(data_login['status'] == 'success')
            self.assertTrue(data_login['message'] == 'Successfully logged in.')
            self.assertTrue(data_login['Authorization'])
            self.assertTrue(resp_login.content_type == 'application/json')
            self.assertEqual(resp_login.status_code, 200)

            # blacklist a valid token
            blacklist_token = BlacklistToken(
                token=json.loads(resp_login.data.decode())['Authorization']
            )

            db.session.add(blacklist_token)
            db.session.commit()

            # blacklisted valid token logout
            response = self.client.post(
                '/auth/logout',
                headers={
                    'Authorization': 'Bearer ' + json.loads(
                        resp_login.data.decode()
                    )['Authorization']
                }
            )

            data = json.loads(response.data.decode())
            self.assertTrue(data['status'] == 'fail')
            self.assertTrue(data['message'] == 'Token blacklisted. Please log in again.')
            self.assertEqual(response.status_code, 401)


if __name__ == '__main__':
    unittest.main()