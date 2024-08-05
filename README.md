### User Service

- To run the service, you need to either edit the file located at [src/main/resources/datasource-properties.yml](https://github.com/justitman123/user-service/blob/master/src/main/resources/datasource-properties.yml "dsadsadsadsa") or execute the command:
  `docker-compose up --build`
  This will execute the scripts in the initdb folder to create and populate the databases.
- Then, build the project with: `mvn package`
- Run the command in the package directory:
  `java -jar target/user-service.jar`
- There are tests in the package. To check them, run `mvn test`


# API
- GET http://localhost:8080/swagger-ui/index.html - URL to view the API documentation.
- GET  http://localhost:8080/users?username=?&name=?&surname=? - the single API endpoint to get all users.

# Requirements
Java 17+
Maven 3
Docker