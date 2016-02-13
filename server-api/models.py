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

	@classmethod
	def by_email(cls, email):
		user = cls.query(cls.email == email).get()
		return user

	@classmethod
	def by_token(cls, token):
		t = Tokens.query(Tokens.token == token).get()
		if t:
			u = Users
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