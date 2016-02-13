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

class CarObject(messages.Message):
    make = messages.StringField(1)
    model = messages.StringField(2)
    year = messages.StringField(3)
    car_id = messages.StringField(4)
    user_token = messages.StringField(5)


@endpoints.api(name = 'cars', version = api_version, auth=AUTH_CONFIG,
               description = 'Car Resources')
class CarsApi(remote.Service):



    @endpoints.method(CarObject, Response,
                        name = 'create',
                        path = 'create',
                        http_method = 'POST')
    def create(self, request):
        response = Cars.save(request.car_id, request.user_token)
        if response:
            return Response(message = "Car saved successfully", success = True, errors = "")
        return Response(message = "The car was not saved successfully.", success = False)


class EmissionsObject(messages.Message):
    make = messages.StringField(1)
    model = messages.StringField(2)
    year = messages.StringField(3)
    car_id = messages.StringField(4)
    user_token = messages.StringField(5)

class DistanceObject(messages.Message):
    user_token = messages.StringField(1)
    distance = messages.StringField(2)
    date = messages.StringField(3)

class EmissionsObject(messages.Message):
    distance = messages.StringField(1)
    emissions = messages.StringField(2)
    date = messages.StringField(3)

class EmissionsObjects(messages.Message):
    emissions = messages.MessageField(EmissionsObject, 1, repeated = True)

@endpoints.api(name = 'emissions', version = api_version, auth=AUTH_CONFIG,
               description = 'Emission Resources')
class EmissionsApi(remote.Service):


    @endpoints.method(DistanceObject, Response,
                        name = 'add_emissions',
                        path = 'add_emissions',
                        http_method = 'POST')
    def add_emissions(self, request):
        response = Emissions.add_emissions(request.user_token, request.distance)
        if response:
            return Response(message = "", success = True, errors = "")
        return Response(message = "", success = False)

    @endpoints.method(DistanceObject, EmissionsObjects,
                        name = 'get_emissions',
                        path = 'get_emissions',
                        http_method = 'POST')
    def get_emissions(self, request):
        response = Emissions.get_emissions(request.user_token)
        all_emissions = [EmissionsObject(distance = str(d.distance), date = d.date.strftime("%B %d, %Y"), emissions = str(d.emissions)) for d in response]
        return EmissionsObjects(emissions = all_emissions)

application = endpoints.api_server([UsersApi, CarsApi, EmissionsApi])