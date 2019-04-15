# todo-api

# Context

Manage TODO list of tasks

# Description

TODO List

# Technologies

- Eclipse.
- Angular2.
- Spring Boot REST API
- Swagger

# How to compile

It uses Manven and it includes a parent pom

```
	mvn clean install
```

# How to deploy

```
	mvn spring-boot:run
```

Swagger can be accessed on:

```
	http://localhost:8080/swagger-ui.html
```

To accessed the REST API first call login function with user and password. Later on include in the Authorization Http Header with "BEARER <token>".

# How to contribute

Features and bug fixes are more than welcome. They must be linked to an issue, so the first step before contributing is the creation of a [GitHub issue](https://github.com/carloscaverobarca/todo-api/issues).

# External resources

- [Angular TODO application by SitePoint](https://www.sitepoint.com/angular-2-tutorial/) and [GitHub repo](https://github.com/sitepoint-editors/angular-todo-app/tree/master/src/app)

# License

Apache 2.0.