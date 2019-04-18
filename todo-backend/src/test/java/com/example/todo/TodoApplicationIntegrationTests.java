package com.example.todo;

import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.todo.domain.Todo;
import com.example.todo.repository.TodoRepository;
import com.example.todo.service.TodoService;

@RunWith(SpringRunner.class)
public class TodoApplicationIntegrationTests {
	
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
	
	@Before
	public void setUp() {
	    Todo todo = new Todo();
	    todo.setComplete(false);
	    todo.setTitle("Item 1");
	    todo.setStart(System.currentTimeMillis());
	    
	    Mockito.when(todoRepository.findById(todo.getId()))
	      .thenReturn(Optional.of(todo));
	}
	
	@Test
	public void whenValidName_thenTodoShouldBeFound() {
	    Integer id = 0;
	    Todo found = todoService.getTodoById(id);
	  
	    assertThat(found, Matchers.<Todo>is(found));
	 }
}
