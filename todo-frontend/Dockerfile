FROM maven:latest

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
ADD todo-frontend-spring/pom.xml /code/todo-frontend-spring/pom.xml
ADD todo-frontend-spring/src /code/todo-frontend-spring/src
ADD todo-frontend-angular/pom.xml /code/todo-frontend-angular/pom.xml
ADD todo-frontend-angular/*.json /code/todo-frontend-angular/
ADD todo-frontend-angular/*.conf.js /code/todo-frontend-angular/
ADD todo-frontend-angular/src /code/todo-frontend-angular/src
ADD todo-frontend-angular/e2e /code/todo-frontend-angular/e2e

RUN ["mvn", "clean", "install"]

EXPOSE 8082
CMD ["java", "-jar", "todo-frontend-spring/target/todo-frontend-0.0.1-SNAPSHOT.jar"]