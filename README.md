# habitat-production


# User API calls


# Create

https://habitatapi.appspot.com/_ah/api/users/1.00/create

{
	"email": "example@example.com",
	"first_name": "Kris",
	"last_name": "Vukasinovic",
	"password": "password"
}

Response:

{
 "token": "a658ddaeece0815713df5b11d3bb8b2be0f5314e",
 "errors": "",
 "message": "User created successfully",
 "success": true,
 "kind": "users#resourcesItem",
 "etag": "\"hy2vmhrmW-q5hSOLd9JtUOsfNhA/E6pRusKd8NgBQmIkc9ZY7Gahlu0\""
}


# Login:

https://habitatapi.appspot.com/_ah/api/users/1.00/login

Sent:

{
 "email": "example@example.com",
 "password": "password"
}

Response:

{
 "token": "61efd6ad21d5552dfcec31fdab9d496b9451f978",
 "errors": "",
 "message": "Kris has successfully logged in!",
 "success": true,
 "kind": "users#resourcesItem",
 "etag": "\"hy2vmhrmW-q5hSOLd9JtUOsfNhA/MlKgzs3mipC6HCm9K8cl7KB6TIY\""
}

# Get User

Sent:

https://habitatapi.appspot.com/_ah/api/users/1.00/get_user?token=61efd6ad21d5552dfcec31fdab9d496b9451f978

Response:

{
 "first_name": "Kris",
 "last_name": "Vukasinovic",
 "email": "example@example.com",
 "kind": "users#resourcesItem",
 "etag": "\"hy2vmhrmW-q5hSOLd9JtUOsfNhA/hi43JQLrKXcMvLW61YsVh3hQfuM\""
}

# Check if User Exists

Sent:

https://habitatapi.appspot.com/_ah/api/users/1.00/check_user_exists?email=example%40example.com

Response:

{
 "success": true,
 "kind": "users#resourcesItem",
 "etag": "\"hy2vmhrmW-q5hSOLd9JtUOsfNhA/6e5BwS9UeU-AeBF8jH73QVhlRgc\""
}



