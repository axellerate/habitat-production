# Cloud Endpoints Libraries
import endpoints
from protorpc import messages
from protorpc import message_types
from protorpc import remote

# allow for HTTP Headers
from google.appengine.ext.endpoints import api_config
AUTH_CONFIG = api_config.ApiAuth(allow_cookie_auth=True)

api_version = "1.00"


#import Datastore models
from models import *


'''
	Users API
'''


class Error(messages.Message):
    error = messages.StringField(1)

class Response(messages.Message):
    message = messages.StringField(1)
    success = messages.BooleanField(2)
    errors = messages.StringField(3)
    token = messages.StringField(4)

class UserObject(messages.Message):
    email = messages.StringField(1)
    password = messages.StringField(2)
    first_name = messages.StringField(3)
    last_name = messages.StringField(4)

class GetUserObject(messages.Message):
    token = messages.StringField(1)

@endpoints.api(name = 'users', version = api_version, auth=AUTH_CONFIG,
               description = 'User Resources')
class UsersApi(remote.Service):



    @endpoints.method(UserObject, Response,
                        name = 'create',
                        path = 'create',
                        http_method = 'POST')
    def create(self, request):
        response = Users.create_user(request)
        if response:
            u = response[0]
            t = response[1]
            return Response(message = "User created successfully", success = True, errors = "", token = t)
        return Response(message = "That email address already exists in our system", success = False)


    @endpoints.method(UserObject, Response,
                        name = 'login',
                        path = 'login',
                        http_method = 'POST')
    def login(self, request):
        #basic_auth = self.request_state.headers.get('authorization')
        auth_values = Users.login(request.email, request.password)
        u = auth_values[0]
        t = auth_values[1]
        if u:
            return Response(message = u.first_name + " has successfully logged in!", success = True, errors = "", token = t)
        return Response(message = "Email or password is incorrect.", success = False, errors = "", token = "")

    @endpoints.method(GetUserObject, UserObject,
                        name = 'get_user',
                        path = 'get_user',
                        http_method = 'GET')
    def get_user(self, request):
        u = Users.by_token(request.token)
        if u:
            return UserObject(email=u.email, first_name=u.first_name, last_name=u.last_name)
        return UserObject(email="", first_name="", last_name="")


    @endpoints.method(UserObject, Response,
                        name = 'check_user_exists',
                        path = 'check_user_exists',
                        http_method = 'GET')
    def check_user_exists(self, request):
        u = Users.by_email(request.email)
        if u:
            return Response(success = True)
        return Response(success = False)


application = endpoints.api_server([UsersApi])