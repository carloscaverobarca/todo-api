/*
 * Copyright (C) 2019  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * TodoApplicationControllerTest.java is free software: you can redistribute it and/or modify it under the 
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
 * Todo Spring boot Controller tests
 */
package com.example.todo;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.todo.controller.TodoController;
import com.example.todo.domain.Todo;
import com.example.todo.exceptions.NotAuthorizedException;
import com.example.todo.oauth2.KeyCloakUser;
import com.example.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TodoApplicationControllerTests {
	
	private MockMvc mockMvc;

	@InjectMocks
	private TodoController todoController;
	
	@Mock
	private TodoService todoService;

    /**
     * Init the Mockito annotations and MVC mock.
     * 
     * @return void
     */
	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(todoController)
				.build();
	}
	
	private Todo todo;
	private KeyCloakUser user;
	private String authToken = "123456789";
	private ObjectWriter ow;
 
    /**
     * Mock the service calls for the different todos operations.
     * 
     * @return void
     * @throws NotAuthorizedException, when the user is not authorized to 
     *   use the API call 
     */
	@Before
	public void setUp() throws NotAuthorizedException {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    
	    ow = mapper.writer().withDefaultPrettyPrinter();

	    todo = new Todo();
	    todo.setComplete(false);
	    todo.setTitle("Item 1");
	    todo.setStart(System.currentTimeMillis());
	    
	    user = new KeyCloakUser();
	    user.setUsername("test");
	    user.setPassword("test");

	    Mockito
	    	.when(todoService.getTodoById(todo.getId()))
	    	.thenReturn(todo);
    
	    List<Todo> list = new ArrayList<Todo>();
	    list.add(todo);
	    Mockito
	    	.when(todoService.getAllTodos(""))
	    	.thenReturn(list);

	    Mockito
	    	.when(todoService.userInfo(""))
	    	.thenReturn("OK");
	    
	    Mockito
	    	.when(todoService.login(user))
	    	.thenReturn("123456789");
	}
	
    /**
     * Test get all todos when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void GetAllTodos_returnAuthorizationErrorTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/todos")
        		.accept(MediaType.APPLICATION_JSON))
          .andExpect(status().is4xxClientError());
	}
	
    /**
     * Test get all todos when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void getAllTodosOAuthDisable_returnTodos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/todos")
        		.header("Authorization", "Bearer " + authToken)
        		.accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()); 
	}
	
    /**
     * Test get todo by id when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void findTodoByIdOAuthDisable_returnTodo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/todos/0")
        		.header("Authorization", "Bearer " + authToken)
        		.accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
	}
	
    /**
     * Test delete todo when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void deleteTodoByIdOAuthDisable_returnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.delete("/todos/0")
        		.header("Authorization", "Bearer " + authToken)
        		.accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
	}
	
    /**
     * Test complete todo when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void completeTodoByIdOAuthDisable_returnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.put("/todos/{id}",0)
        		.header("Authorization", "Bearer " + authToken)
        		.accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
	}
	
    /**
     * Test create a new todo when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void createTodoOAuthDisable_returnOk() throws Exception {
	    String requestTodo = ow.writeValueAsString(todo);

	    mockMvc.perform(MockMvcRequestBuilders
        		.post("/todos")
        		.header("Authorization", "Bearer " + authToken)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestTodo.getBytes()))
          .andExpect(status().isOk());
	}
	
    /**
     * Test get user info when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void getUserInfoOAuthDisable_returnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
        		.get("/userinfo")
        		.header("Authorization", "Bearer " + authToken)
        		.contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
	}
	
    /**
     * Test login when no authorization bearer is provided.
     * 
     * @return void
     * @throws Exception, when some error happens 
     */
	@Test
	public void login_returnOk() throws Exception {
        String requestTodo = ow.writeValueAsString(user);
        mockMvc.perform(MockMvcRequestBuilders
        		.post("/login")
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(requestTodo.getBytes()))
          .andExpect(status().isOk());
	}
}


	
