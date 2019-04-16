# todo-api

# Context

Manage TODO list of tasks

# Description

TODO List prepared for production with the following architecture (letsencrypt for https):

![Diagram](img/nginx-proxy-angular.jpg)


# Technologies

- Eclipse.
- Angular2.
- Spring Boot REST API
- Swagger
- KeyCloak
- Nginx as reverse proxy and letsencrypt

Spring profiles have been used to differentiate between test and production. Run:
```
	mvn spring-boot:run -Dspring.profiles.active=local
```

# How to compile

It uses Manven and it includes a parent pom

```
	mvn clean install
```

# How to deploy

Deploy directly the Spring application:

```
	mvn spring-boot:run
```

Deploy using docker-compose:

```
Stop and remove the containers
docker-compose down

Launch the containers (dettached)
docker-compose up -d
```

Swagger can be accessed on:

```
	http://localhost:8081/swagger-ui.html
```

To accesse the REST API first call login function with user and password. Later on include it in the Authorization Http Header with "BEARER <token>".

**IMPORTANT!** Create a realm "test", with a client id "test" in KeyCloak. Create a user in the new realm for testing.
```
	http://localhost:8080/
```

The nginx reverse proxy allows also accessing using domains: todo and keycloak.

# How to contribute

Features and bug fixes are more than welcome. They must be linked to an issue, so the first step before contributing is the creation of a [GitHub issue](https://github.com/carloscaverobarca/todo-api/issues).

# External resources

- [Angular TODO application by SitePoint tutorial](https://www.sitepoint.com/angular-2-tutorial/) and the corresponding [GitHub repo](https://github.com/sitepoint-editors/angular-todo-app/tree/master/src/app)

# License

Apache 2.0.