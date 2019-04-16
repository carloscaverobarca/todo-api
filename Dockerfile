FROM maven:latest

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
ADD todo-backend/pom.xml /code/todo-backend/pom.xml
ADD todo-backend/src /code/todo-backend/src
ADD todo-frontend/pom.xml /code/todo-frontend/pom.xml
ADD todo-frontend/*.json /code/todo-frontend/
ADD todo-frontend/*.conf.js /code/todo-frontend/
ADD todo-frontend/src /code/todo-frontend/src
ADD todo-frontend/e2e /code/todo-frontend/e2e

RUN ["mvn", "clean", "install"]

EXPOSE 8081
CMD ["java", "-jar", "todo-backend/target/todo-0.0.1-SNAPSHOT.jar"]
# Production
# CMD ["java", "-jar", "todo-backend/target/todo-0.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=local"]
