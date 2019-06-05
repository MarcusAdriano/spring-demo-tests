package com.marcus.demoapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public final class TodoController {

    @Autowired
    private TodoService mService;
    private static ObjectMapper mObjectMapper = new ObjectMapper();

    private static String serialized(Object obj) {
        try {
            return mObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "{}";
    }

    @GetMapping(value = "/todo", produces = "application/json; charset=utf-8")
    public ResponseEntity getAll() {
        return ResponseEntity
                .ok(serialized(mService.findAll()));
    }

    /*@PostMapping(produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    public ResponseEntity getByFilter(@RequestBody StringFilter stringFilter) {
        Iterable<Todo> todos = mService.get(stringFilter.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(serialized(todos));
    }*/

    @PostMapping(value = "/todo", produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    public ResponseEntity newTodo(@RequestBody Todo request) {
        Optional<Todo> todo = mService._new(request);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(serialized(todo.get()));
        } else {
            return ResponseEntity
                    .badRequest()
                    .build();
        }
    }

    @GetMapping(value = "/todo/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity getById(@PathVariable(name = "id") long id) {
        Optional<Todo> todo = mService.get(id);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(serialized(todo.get()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping(value = "/todo/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity deleteById(@PathVariable(name = "id") long id) {
        Optional<Todo> todo = mService.delete(id);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(serialized(todo.get()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping(value = "/todo", produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    public ResponseEntity updateTodo(@RequestBody Todo request) {
        Optional<Todo> todo = mService.update(request);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(serialized(todo.get()));
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
