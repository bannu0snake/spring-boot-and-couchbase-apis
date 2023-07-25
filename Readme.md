Installation Guide

1. Download and Extract the project.
2. Download CouchDB server and create a bucket as “employee” and create admin user account
3. Update couch db credentials in CouchBaseConfig file.
4. update email and password in properties file.
5. Run AssignmentApplication Class to start the server.
6. The Postman Exported Json file is attached in the zip folder.  
   File name: ‘api_routes.json’

APIs Routes:

Get Employee By Id:
GET Request
"http://localhost:8080/api/v1/employee/{id}"

Get all Employees with pagination:
GET Request  
"http://localhost:8080/api/v1/employee?sort=id&order=desc&page=0&pageSize=10"

Get nth manager of employee:
GET Request
"http://localhost:8080/api/v1/employee/{id}/{n}"

Add employee :
POST Request
"http://localhost:8080/api/v1/employee"

Update employee :
PUT Request
"http://localhost:8080/api/v1/employee/{id}"

Delete employee :
DELETE Request
"http://localhost:8080/api/v1/employee/{id}"

Send Email to manager :
POST Request
"http://localhost:8080/api/v1/employee/sendMail/{id}"

\*\* All Tasks are completed.
