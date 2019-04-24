/*
 * Copyright (C) 2019  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * TodoApplicationServiceTest.java is free software: you can redistribute it and/or modify it under the 
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
 * @author	Carlos Cavero
 * 
 * Todo Spring boot service tests
 */
package com.example.todo;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.example.todo.domain.Todo;
import com.example.todo.exceptions.NotAuthorizedException;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "keycloak.url=http://localhost:8080","keycloak.realm=test",
 "keycloak.client_id=test"})
// @TestPropertySource(locations = "/application.yml")
@Profile("test")
public class TodoApplicationServiceTests {
	
    /**
     * Create the Bean Todo Service.
     * 
     * @return the Todo Service
     */
	@TestConfiguration
	static class TodoServiceImplTestContextConfiguration {
	  
		@Bean
		public TodoService todoService() {
			return new TodoService();
		}
	}
	 
	@Autowired
	private TodoService todoService;
	 
	@MockBean
	private TodoRepository todoRepository;
	
	@Mock
	private RestTemplate restTemplate;
	
	private String authToken = "123456789";
	
    /**
     * SetUp the system to mock the Repository and test the services.
     * 
     * @return void
     */
	@Before
	public void setUp() {
	    Todo todo = new Todo();
	    todo.setComplete(false);
	    todo.setTitle("Item 1");
	    todo.setStart(System.currentTimeMillis());
	    todoService.saveTodo(todo, "");
	    
	    Mockito.when(todoRepository.findById(todo.getId()))
	      .thenReturn(Optional.of(todo));

	    List<Todo> list = new ArrayList<Todo>();
	    list.add(todo);
	    Mockito.when(todoRepository.findAll())
	      .thenReturn(list);
		
	    // Mock any exchange REST call to provide status ok
	    when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<String>>any()))
             .thenReturn(new ResponseEntity<String>(HttpStatus.OK));
		
		todoService.setRestTemplate(restTemplate);
	}
	
    /**
     * Test all the Service functionalities provided.
     * 
     * @return void
     * @throws NotAuthorizedException, when some not authorization raises 
     */
	@Test
	public void callService_returnExpectedBehaviourTest() throws NotAuthorizedException {
	    Integer id = 0;
	    Todo found = todoService.getTodoById(id);
	  
	    assertThat(found, Matchers.<Todo>is(found));
	    
	    todoService.completeTodo(id);
	    
	    List<Todo> list = todoService.getAllTodos("");
	    Todo compareFound = list.get(0);
	    
	    assertThat(found.getTitle(), Matchers.<String>is(compareFound.getTitle()));

	    todoService.delete(id);
	    
	    todoService.userInfo(authToken);
	 }
}
