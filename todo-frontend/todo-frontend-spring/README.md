# Todo Frontend Spring

Frontend for web application to embed the TODO UI angular project.

## Description

Java frontend project, using Spring. It provides:

- UI deployment

## Technologies

- Java 8
- Maven
- Spring Boot

## Dependencies

It needs the angular project copied to `src/main/resources/static` from the `todo-frontend` subproject.

## How to deploy

Compile with
```
	mvn clean install
```

It can be run as:
```
    mvn spring-boot:run
```

### White Label error

Angular is configured to access the index when pressing F5. Spring has problems because it does not find the refreshment pages raising the White Label
error. In the Application source code we must force this behaviour in Spring as it is explained [here](https://stackoverflow.com/questions/44692781/configure-spring-boot-to-redirect-404-to-a-single-page-app):
```
@Configuration
public class WebApplicationConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/notFound").setViewName("forward:/index.html");
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,
                    "/notFound"));
        };
    }

```

## How to deploy in production

Spring can be configured to run the executable [jars/wars as linux services](https://stackoverflow.com/questions/22886083/how-do-i-run-a-spring-boot-executable-jar-in-a-production-environment)
by including `executable=true`:
```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
```

It is possible to symlink the spring boot to init.default
```
$ln -s /var/yourapp/yourapp.jar /etc/init.d/yourapp
$/etc/init.d/yourapp start|stop|restart
```

Or use a system script
```
[Unit]
Description=yourapp
After=syslog.target

[Service]
ExecStart=/var/yourapp/yourapp.jar

[Install]
WantedBy=multi-user.target
```

## Docker

Included Dockerfile:

    $ docker build -t todofrontendtest .
    $ docker run -d todofrontendtest
    
Test

    $ docker-machine ip default    
    $ curl [ip default]:4567 

Stop

    $ docker stop $(docker ps -qa)

Delete all docker containers

    docker rm $(docker ps -a -q)

Delete all docker images

    docker rmi $(docker images -q)

## Usage and configuration

Context and port for the api is set in application.properties: 

    server.port=8082

**IMPORTANT** Rest interface root access: http://localhost:8081/

The port can be changed in resources/application.properties.

### Static content

The static content must be placed in src/main/resources/static

## License

Apache 2.0.