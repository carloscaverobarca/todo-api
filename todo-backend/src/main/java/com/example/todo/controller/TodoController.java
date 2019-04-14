/*
 * Copyright (C) 2018  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * TodoController.java is free software: you can redistribute it and/or modify it under the 
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
 * Todo Spring boot Controller
 */
package com.example.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.todo.domain.Todo;
import com.example.todo.service.Service;

@RestController
public class TodoController {

    @Autowired
    Service todoService;

    @GetMapping("/todos")
    private List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/todos/{id}")
    private Todo getTodo(@PathVariable("id") int id) {
        return todoService.getTodoById(id);
    }

    @DeleteMapping("/todos/{id}")
    private void delete(@PathVariable("id") int id) {
        todoService.delete(id);
    }

    @PostMapping("/todos")
    private int saveTodo(@RequestBody Todo todo) {
        todoService.saveTodo(todo);
        return todo.getId();
    }

    @PutMapping("/todos/{id}")
    private void complete(@PathVariable("id") int id) {
        todoService.completeTodo(id);
    }
}