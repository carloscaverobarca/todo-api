package com.example.todo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
/*
 * Copyright (C) 2018  All rights reserved.
 * 
 * This file is part of the todo-api.
 * 
 * Todo.java is free software: you can redistribute it and/or modify it under the 
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
 * Todo domain
 */
import javax.persistence.Id;

@Entity
public class Todo {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    private boolean complete = false;
    private Long start;
    private Long end;
    private String userid;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	public Long getStart() {
		return start;
	}
	
	public void setStart(Long start) {
		this.start = start;
	}
	
	public Long getEnd() {
		return end;
	}
	
	public void setEnd(Long end) {
		this.end = end;
	}

	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
}
