# ktor-sample

CREATE USER

curl --location 'http://localhost:8080/user/add' \
--header 'Content-Type: application/json' \
--data '{
    "phone": "9123456789",
    "password": "12345"
}'



Login User

curl --location 'http://localhost:8080/user/login' \
--header 'Content-Type: application/json' \
--data '{
    "phone": "09123456789",
    "password": "12345"
}'



Get User

curl --location 'http://localhost:8080/me' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yVXNlcnMiLCJpc3MiOiJrdG9yLmlvIiwicGhvbmUiOiIrOTg5Mzc4NTU0ODQxIn0.iBLunQLfwLk3RQR2lCgPOWI_9FcpsGNwXqPMtGeZMc0'



Delete User

curl --location --request DELETE 'http://localhost:8080/me/delete' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yVXNlcnMiLCJpc3MiOiJrdG9yLmlvIiwicGhvbmUiOiIrOTg5Mzc4NTU0ODQxIn0.iBLunQLfwLk3RQR2lCgPOWI_9FcpsGNwXqPMtGeZMc0'



Update User

curl --location --request PUT 'http://localhost:8080/me/update' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJrdG9yVXNlcnMiLCJpc3MiOiJrdG9yLmlvIiwicGhvbmUiOiIrOTg5Mzc4NTU0ODQxIn0.iBLunQLfwLk3RQR2lCgPOWI_9FcpsGNwXqPMtGeZMc0' \
--header 'Content-Type: application/json' \
--data '{
    "name": "user"
}'
