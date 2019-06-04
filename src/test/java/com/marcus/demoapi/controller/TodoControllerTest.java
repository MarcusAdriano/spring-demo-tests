package com.marcus.demoapi.controller;

import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoService mService;

    @Before
    public void setUp() throws Exception {
        Todo todo = new Todo();
        todo.setComplete(false);
        todo.setDescription("First todo!");
        todo.setCreateAt(new Date(new java.util.Date().getTime()));
        todo.setCompleteAt(null);
        todo.setUpdateAt(null);

        mService._new(todo);
    }

    @Test
    public void getAll() throws Exception {
        
    }

    @Test
    public void newTodo() {

    }

    @Test
    public void getByFilter() {
    }

    @Test
    public void getById() {
    }

    @Test
    public void deleteById() {
    }

    @Test
    public void updateTodo() {
    }
}