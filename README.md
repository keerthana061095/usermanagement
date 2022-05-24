# usermanagement

POST method : http://localhost:8080/api/v1/register

RequestBody:

{
    "fullName": "Keerthana",
    "email": "Keerhana@gmail.com",
    "userName":"keerthana",
    "password": "pass123",
    "roleUid":1,
    "passcodeVerify":1
}

=================================
Login:-

POST method : http://localhost:8080/api/v1/authenticate

Request body:

{
    "username":"keerthana",
    "password":"pass123"
}


Response:

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmplZSIsImV4cCI6MTY1MzI1MTQ0NCwiaWF0IjoxNjUzMjM3MDQ0fQ.KIyCAQsb30CAcaLLWX27cYs3S5w7cK_lTpvrcplmv92boonUF5pCexDFKyVNpG6tc8OoN-HCo9lhWSUKwL-39Q",
    "fullName": "Keerthana"
}

====================
POST method:
http://localhost:8080/api/v1/user/generateOtp/keerthana

Response:
{
    "username": "keerthana",
    "otp": "jAII9EW"
}
=======================

Change Password:-

PUT method:

http://localhost:8080/api/v1/user/changePassword

Request body:

{
    "userName":"keerthi",
    "otp":"ytvFlIM",
    "currentPassword":"pass213",
    "newPassword":"pass213"
}

response:

Password changed - Text format
