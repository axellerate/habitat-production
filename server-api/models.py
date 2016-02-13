'''
	Models
'''

# NDB Model Libraries
from google.appengine.ext import ndb

# user auth
from user_authentication import *


# token libraries
import uuid
import hmac

try:
    from hashlib import sha1
except ImportError:
    import sha
    sha1 = sha.sha

#urllib
import urllib2

import datetime

class BaseModel(ndb.Model):
	'''
		BaseModel - inherited by all other models.
	'''

	created = ndb.DateTimeProperty(auto_now_add = True)
	updated = ndb.DateTimeProperty(auto_now = True)

class Roles(ndb.Model):
	pass


class Users(BaseModel):
	'''
		Contains user information.
	'''

	email = ndb.StringProperty(required = True)
	password_hash = ndb.StringProperty(required = True)
	first_name = ndb.StringProperty(required = True)
	last_name = ndb.StringProperty(required = True)
	active = ndb.BooleanProperty(default = True)
	profile_image = ndb.KeyProperty(kind = 'Images')
	friends = ndb.KeyProperty(kind = 'Users', repeated = True)
	car = ndb.KeyProperty(kind = 'Cars')
	motorcycles = ndb.KeyProperty(kind = 'Motorcycles', repeated = True)
	public_transport = ndb.BooleanProperty()
	walk_or_bike = ndb.BooleanProperty()

	@classmethod
	def by_email(cls, email):
		user = cls.query(cls.email == email).get()
		return user

	@classmethod
	def by_token(cls, token):
		t = Tokens.query(Tokens.token == token).get()
		print t
		if t:
			return t.user.get()
		return False

	@classmethod
	def login(cls, email, password):
		# returns a user and a token value
		user = cls.by_email(email)
		if user and valid_pw(email, password, user.password_hash):
			t = Tokens()
			t.user = user.key
			t.token = t.generate_key()
			t.put()
			return [user, t.token]

	@classmethod
	def create_user(cls, user):
		u = cls.by_email(user.email)
		if not u:
			u = Users()
			u.email = user.email
			u.password_hash = make_pw_hash(user.email, user.password)
			u.first_name = user.first_name
			u.last_name = user.last_name
			u.put()
			t = Tokens()
			t.user = u.key
			t.token = t.generate_key()
			t.put()
			return [u, t.token]

class Tokens(BaseModel):
	'''
		Contains tokens for authentication.
	'''

	token = ndb.StringProperty(required = True)
	user = ndb.KeyProperty(kind = 'Users', required = True)
	lifespan = ndb.DateTimeProperty()

	def generate_key(self):
		new_uuid = uuid.uuid4()
		# Hmac that beast.
		return hmac.new(str(new_uuid), digestmod=sha1).hexdigest()

class Cars(BaseModel):
	'''
		Stores the car data from http://www.FuelEconomy.gov
	'''

	car_id = ndb.IntegerProperty(required = True)
	make = ndb.StringProperty(required = True)
	model = ndb.StringProperty(required = True)
	year = ndb.IntegerProperty(required = True)
	emissions_per_km = ndb.FloatProperty(required = True)


	@classmethod
	def save(cls, car_id, user_token):
		'''
			Accepts a car ID. If the car doesn't exist,
			fetch the data and save the new car.
		'''

		if not cls.query(cls.car_id == int(car_id)).count() > 0:
			url = "http://fueleconomy.gov/ws/rest/vehicle/%s" %str(car_id)
			xml = urllib2.urlopen(url).read()

			start = xml.find("<make>")+6
			end = xml.find("</make>")
			make = xml[start:end]

			start = xml.find("<model>")+7
			end = xml.find("</model>")
			model = xml[start:end]

			start = xml.find("<year>")+6
			end = xml.find("</year>")
			year = xml[start:end]

			start = xml.find("<co2TailpipeGpm>")+16
			end = xml.find("</co2TailpipeGpm>")
			emissions_per_mile = float(xml[start:end])

			emissions_per_km = emissions_per_mile / 1.6

			car = cls(car_id = int(car_id), make = make, model = model, 
				year = int(year), emissions_per_km = emissions_per_km).put()

			user = Users.by_token(str(user_token))
			user.car = car.key
			user.put()

			return car
		else:
			car = cls.query(cls.car_id == int(car_id)).get()

			user = Users.by_token(str(user_token))
			user.car = car.key
			user.put()

			return car


class Motorcycles(BaseModel):
	'''
		Contains Motorcyles Information
	'''
	make = ndb.StringProperty(required = True)
	model = ndb.StringProperty(required = True)
	year = ndb.IntegerProperty(required = True)
	size = ndb.IntegerProperty(required = True)
	emissions_per_km = ndb.FloatProperty(required = True)


class Emissions(BaseModel):
	'''
		Stores distances from when a user starts to 
		move right until the user stops.
	'''

	user = ndb.KeyProperty(kind = Users)
	distance = ndb.FloatProperty(required = True)
	date = ndb.DateProperty(auto_now_add = True)
	emissions = ndb.FloatProperty(required = True)

	@classmethod
	def add_emissions(cls, user_token, _distance):
		user = Users.by_token(str(user_token))
		current_date = datetime.datetime.now()
		distance = Emissions.query(Emissions.date >= current_date).get()

		if user and distance:
			car_emissions = Cars.get_by_id(user.car.id()).emissions_per_km
			distance.distance += float(_distance)
			distance.emissions += (car_emissions * float(_distance))
			distance.put()
			return True
		if user:
			car_emissions = Cars.get_by_id(user.car.id()).emissions_per_km
			d = cls(user = user.key, distance = float(_distance), emissions = (car_emissions * float(_distance)))
			d.put()
			return True
		return False

	@classmethod
	def get_emissions(cls, user_token):
		user = Users.by_token(str(user_token))
		if user:
			distances = cls.query().order(-cls.date)
			return distances
		return False


