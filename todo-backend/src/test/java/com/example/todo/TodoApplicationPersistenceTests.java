package com.example.todo;

import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.todo.domain.Todo;
import com.example.todo.oauth2.KeyCloakInterceptor;
import com.example.todo.repository.TodoRepository;

@RunWith(SpringRunner.class)
@AutoConfigureTestEntityManager
@SpringBootTest
@Transactional
public class TodoApplicationTests {
	 
    @Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private TodoRepository todoRepository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void whenFindById_thenTodo() {
	    // given
	    Todo todo = new Todo();
	    todo.setComplete(false);
	    todo.setTitle("Item 1");
	    todo.setStart(System.currentTimeMillis());
	    entityManager.persist(todo);
	    entityManager.flush();
	 
	    // when
	    Todo found = todoRepository.findById(todo.getId()).get();
	 
	    // then
	    assertThat(found, Matchers.<Todo>is(found));
	}
}
