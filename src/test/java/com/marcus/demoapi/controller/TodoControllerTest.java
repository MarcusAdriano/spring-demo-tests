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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void setUp() {
        Todo todo = new Todo();
        todo.setComplete(false);
        todo.setDescription("First todo!");
        todo.setCreateAt(new java.util.Date());
        todo.setCompleteAt(null);
        todo.setUpdateAt(null);

        currentTodo = mService._new(todo);
    }

    @After
    public void tearDown() {
        currentTodo.ifPresent(todo -> mService.delete(todo.getId()));
    }

    @Test
    public void getAll() throws Exception {

        String json = new ObjectMapper().writeValueAsString(mService.findAll());

        mvc.perform(get("/todo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(json));
    }

    @Test
    public void _newTodo() throws Exception {
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
    public void getById() throws Exception {
        if (currentTodo.isPresent()) {
            Todo todo = currentTodo.get();

            String expectResponse = new ObjectMapper()
                    .writeValueAsString(todo);

            mvc.perform(get("/todo/" + todo.getId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(content().json(expectResponse));

        } else {
            fail();
        }
    }

    @Test
    public void getByIdNotFound() throws Exception {
        mvc.perform(get("/todo/" + 9999))
                .andExpect(status().is(404));
    }

    @Test
    public void deleteByIdNotFound() throws Exception {
        mvc.perform(delete("/todo/" + 9999))
                .andExpect(status().is(404));
    }

    @Test
    public void updateNotFound() throws Exception {
        Todo todo = new Todo();
        String json = new ObjectMapper().writeValueAsString(todo);
        mvc.perform(put("/todo")
                .contentType("application/json; charset=utf-8")
                .content(json))
                .andExpect(status().is(404));
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
    public void _newTodoBadRequest() throws Exception {
        Todo t1 = new Todo();

        currentTodo.ifPresent(todo -> t1.setId(todo.getId()));

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
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    public void updateTodo() throws Exception {
        if (currentTodo.isPresent()) {

            Todo todo = currentTodo.get();
            todo.setComplete(true);
            todo.setCompleteAt(new Date());

            String request = new ObjectMapper().writeValueAsString(todo);

            mvc.perform(put("/todo")
                    .contentType("application/json; charset=utf-8")
                    .content(request))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.complete").value(true))
                    .andExpect(jsonPath("$.completeAt").value(todo.getCompleteAt().getTime()));
        } else {
            fail();
        }
    }
}