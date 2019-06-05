package com.marcus.demoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TodoController.class)
@ComponentScan("com.marcus.demoapi.service")
public class TodoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoService mService;

    private Optional<Todo> currentTodo;

    @Before
    public void setUp() throws Exception {
        Todo todo = new Todo();
        todo.setComplete(false);
        todo.setDescription("First todo!");
        todo.setCreateAt(new java.util.Date());
        todo.setCompleteAt(null);
        todo.setUpdateAt(null);

        currentTodo = mService._new(todo);
    }

    @After
    public void tearDown() throws Exception {
        currentTodo.ifPresent(todo -> mService.delete(todo.getId()));
    }

    @Test
    public void findAll() throws Exception {

        String json = new ObjectMapper().writeValueAsString(mService.findAll());

        mvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json));
    }

    @Test
    public void newTodo() throws Exception {
        Todo t1 = new Todo();
        t1.setId(new Random().nextInt(1000) + 500);
        t1.setUpdateAt(null);
        t1.setCreateAt(new Date());
        t1.setCompleteAt(new Date());
        t1.setDescription("Apenas um teste");
        t1.setComplete(true);

        String requestContent = new ObjectMapper()
                .writeValueAsString(t1);

        mvc.perform(post("/todo")
                .contentType("application/json; charset=utf-8")
                .content(requestContent))
                .andExpect(status().is(201))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(requestContent));
    }

    @Test
    public void getByFilter() {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void deleteById() throws Exception {
        if (currentTodo.isPresent()) {
            mvc.perform(delete(String.format("/todo/%d", currentTodo.get().getId())))
                    .andExpect(status().isOk());
        } else {
            fail();
        }
    }

    @Test
    public void updateTodo() {
    }
}