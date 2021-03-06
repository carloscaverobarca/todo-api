/*
 * Copyright (C) 2018  Carlos Cavero. All rights reserved.
 * 
 * This file is part of the Todo project.
 * 
 * This is free software: you can redistribute it and/or modify it under the 
 * terms of the Apache License, Version 2.0 (the License);
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * The software is provided "AS IS", without any warranty of any kind, express or implied,
 * including but not limited to the warranties of merchantability, fitness for a particular
 * purpose and noninfringement, in no event shall the authors or copyright holders be 
 * liable for any claim, damages or other liability, whether in action of contract, tort or
 * otherwise, arising from, out of or in connection with the software or the use or other
 * dealings in the software.
 * 
 * See README file for the full disclaimer information and LICENSE file for full license 
 * information in the project root.
 * 
 * @author  Carlos Cavero
 * 
 * Todo Spring boot application TodoService implementation
 */
package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.todo.controller.TodoController;
import com.example.todo.domain.Todo;
import com.example.todo.exceptions.NotAuthorizedException;
import com.example.todo.oauth2.KeyCloakUser;
import com.example.todo.repository.TodoRepository;

@Component
public class TodoService implements Service {

	public static final Logger log = LoggerFactory.getLogger(TodoService.class);

	//Constants
	@Value("${keycloak.url}")
    private String keycloak_url;
	
	@Value("${keycloak.realm}")
    private String keycloak_realm;
	
	@Value("${keycloak.client_id}")
    private String keycloak_client_id;
	
	@Autowired
    TodoRepository todoRepository;
	
	RestTemplate restTemplate = new RestTemplate();

    public List<Todo> getAllTodos(String token) {
        List<Todo> todos = new ArrayList<Todo>();
        todoRepository.findAll().forEach(todo -> todos.add(todo));
        return todos;
    }

    public Todo getTodoById(int id) {
        return todoRepository.findById(id).get();
    }

    public void saveTodo(Todo todo, String token) {
    	todo.setStart(System.currentTimeMillis());
        todoRepository.save(todo);
    }

    public void completeTodo(int id) {
    	Todo todo = getTodoById(id);
    	todo.setEnd(System.currentTimeMillis());
        todoRepository.save(todo);
    }

    public void delete(int id) {
        todoRepository.deleteById(id);
    }

	@Override
	public String login(KeyCloakUser user) throws NotAuthorizedException {
		AccessTokenResponse accessToken = null;

		try {
			// Gets authorization token (if it is correct)
		    Keycloak keycloak = KeycloakBuilder
		            .builder()
		            .serverUrl(keycloak_url)
		            .realm(keycloak_realm)
		            .username(user.getUsername())
		            .password(user.getPassword())
		            .clientId(keycloak_client_id)
		            .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
		            .build();
		    
			accessToken = keycloak.tokenManager().getAccessToken();
		} catch (Exception ex) {
    		log.error(ex.toString());
    		throw new NotAuthorizedException("Unauthorised access to protected resource");
		}
		return accessToken.getToken();
	}

	@Override
	public String userInfo(String authToken) throws NotAuthorizedException {
        
        HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + authToken);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		log.info("URL :" + keycloak_url + "/realms/" + 
			keycloak_realm + "/protocol/openid-connect/userinfo");
		
		ResponseEntity<String> response = restTemplate.exchange(keycloak_url + "/realms/" + 
			keycloak_realm + "/protocol/openid-connect/userinfo", 
			HttpMethod.POST, entity, String.class);

   		if (response.getStatusCode().value() != 200) {
            log.error("OAuth2 Authentication failure. Invalid OAuth Token supplied in Authorization Header on Request.");
    		throw new NotAuthorizedException("OAuth2 Authentication failure. "
    				+ "Invalid OAuth Token supplied in Authorization Header on Request.");
        }
   		
   		return "OK";
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
}