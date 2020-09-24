Demo Spring Boot REST Api application
==================


This is a basic spring-boot app to demonstrate exposing a set of REST endpoints to retrieve and add messages and replies. That can be used as a template to develop a forum system.

## Getting Started

### Prerequisites
* Git
* JDK 8 or later
* Maven 3.0 or later

### Clone
To get started you can simply clone this repository using git:
```
git clone https://github.com/dbaranau/interview-question3.git
cd interview-question3
```

### Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.example.demo.DemoApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

### Build an executable JAR
You can run the application from the command line using:
```
mvn spring-boot:run
```
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
```
mvn clean package
```
Then you can run the JAR file with:
```
java -jar target/*.jar
```


# Testing API endpoints locally
 
Once the application is running, there are 4 API endpoints are available on port 5000:

### Post new question: `http://localhost:5000/questions`
with body:
```json
{
  "author": "Daniel",
  "message": "Message text"
}
```
The response is 201:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": 0
}
```

### Post a reply to a message: `http://localhost:5000/questions/{questionId}/reply`
with body:
```json
{
  "author": "Reply author",
  "message": "Message reply text"
}
```
The response is 201:
```json
{
  "questionId": 1,
  "id": 5,
  "author": "Reply author",
  "message": "Message reply text"
}
```

### Get a thread: `http://localhost:5000/questions/{questionId}`, 
The response looks like:
```json
{
  "id": 1,
  "author": "Daniel",
  "message": "Message text",
  "replies": [
    {
       "id": 5,
       "author": "Reply author",
       "message": "Message reply text"
    },
    ...
  ]
}
```

### Get a list of questions: `http://localhost:5000/questions`
The response looks like:
```json
[
    {
      "id": 1,
      "author": "Daniel",
      "message": "Message text",     
      "replies": 0
    },
    ...
]
```


## License
This project is licensed under the terms of the [MIT license](LICENSE).

