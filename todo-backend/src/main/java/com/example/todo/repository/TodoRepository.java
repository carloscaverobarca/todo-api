/*
 * Copyright (C) 2018  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * TodoRepository.java is free software: you can redistribute it and/or modify it under the 
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
 * Todo Spring boot Repository interface
 */
package com.example.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.domain.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {} 
