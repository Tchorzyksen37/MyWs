# my-web-service
"I don't know where it goes. So far user authentication is SpringBoot" - old description.

On 1-Apr-2020, I decided to learn springframework. That's one of my first repository where I tested core functionalities of every web application written in springboot.
It was inspiered by various online tutorials.

Starting from 28-Jan-2022, I forked my old playground repository with plan to
- add new functionalities
- collect aquired knowledge over course of last year and a half in one place 
- learn yet unfamiliar to me aspects of springboot 

## How to use application or test manually 
### Create User
```
curl --location --request POST 'http://localhost:8080/user' \
--header 'Content-Type: application/json' \
--data-raw '{
"person": {
"firstName": "your-firstname",
"lastName": "your-surname"
},
"email": "email@domain.com",
"password": "password"
}'
```
### Log-in
```
curl --location --request POST 'http://localhost:8080/user/login' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "email@domain.com",
    "password": "password"
}'
```
<b>Remark:</b> Use credential provided in create user section.

Example response's headers  
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0Y2hvcnp5a3NlbkBnbWFpbC5jb20iLCJleHAiOjE2NDY0OTkxNTJ9.KRwm7bKAg494ngelJL8OJyGdx1SZFAn72_bMY6lwdYzZjM7IEnY5MbHS-pkWuU07B5fSrZ02HAvFkJOqiOhviw
UserID: 1b4Ui1LthiLLFpaYtGWvtbj2SZ0DeG
```
One should save "Authorization" header. As it is necessary to provide the token in order to access endpoints that require authorization.
### How to authorize 
Example:
```
curl --location --request GET 'http://localhost:8080/user/' \
--header 'Authorization: Bearer Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0Y2hvcnp5a3NlbkBnbWFpbC5jb20iLCJleHAiOjE2NDY0OTkxNTJ9.KRwm7bKAg494ngelJL8OJyGdx1SZFAn72_bMY6lwdYzZjM7IEnY5MbHS-pkWuU07B5fSrZ02HAvFkJOqiOhviw'
```

New functionalities 
- send email based on html template with dynamic variables 
- serves gif media type
- add flyway support (Evolutionary Database Design)
