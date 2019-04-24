/*
 * Copyright (C) 2019  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * TodoApplicationPersistenceTest.java is free software: you can redistribute it and/or modify it under the 
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
 * Todo Spring boot persistence tests
 */
package com.example.todo;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoApplicationPersistenceTests {
	 
    @Autowired
    private TodoRepository todoRepository;

    /**
     * Test persistence functionalities to manage the TODOs.
     * 
     * @return void
     */
    
    @Test
	public void persistenceTest() {
	    Todo todo = new Todo();
	    todo.setComplete(false);
	    todo.setTitle("Item 1");
	    todo.setStart(System.currentTimeMillis());
	    
	    List<Todo> initialTodos = new ArrayList<Todo>();
	    initialTodos.add(todo);
	    todoRepository.save(todo);
	 
	    Todo found = todoRepository.findById(todo.getId()).get();
	 
	    assertThat(found, Matchers.<Todo>is(found));
	    
	    List<Todo> finalTodos = todoRepository.findAll();

	    assertThat(initialTodos.get(0).getTitle(), 
	    		Matchers.<String>is(finalTodos.get(0).getTitle()));
	    
	    todoRepository.deleteById(todo.getId());
	    
	    finalTodos = todoRepository.findAll();
	    
	    assertThat(finalTodos.size(), Matchers.<Integer>is(0));
	}
}
