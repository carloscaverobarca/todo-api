FROM java:8 

# Install maven
RUN apt-get update
RUN apt-get install -y maven

WORKDIR /code

# Prepare by downloading dependencies
ADD phs-frontend-web/pom.xml /code/pom.xml
RUN ["mvn", "dependency:resolve"]
# RUN ["mvn", "verify"]

# Adding source, compile and package into a fat jar
ADD facet-api/src /code/src
RUN ["mvn", "package"]

EXPOSE 9091
# Be careful with the version of the library
CMD ["/usr/lib/jvm/java-8-openjdk-amd64/bin/java", "-jar", "target/phs-frontend.jar"]