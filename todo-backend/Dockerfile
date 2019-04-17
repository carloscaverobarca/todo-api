FROM maven:3.3.9-jdk-8-alpine 

WORKDIR /code

# Prepare by downloading dependencies
ADD pom.xml /code/pom.xml
ADD src /code/src

RUN ["mvn", "clean", "install"]

EXPOSE 8081
CMD ["java", "-jar", "target/todo-0.0.1-SNAPSHOT.jar"]
# Production
# CMD ["java", "-jar", "target/todo-0.0.1-SNAPSHOT.jar", "-Dspring.profiles.active=local"]
