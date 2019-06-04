package com.marcus.demoapi.controller;

import com.marcus.demoapi.model.StringFilter;
import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController()
@RequestMapping("todo")
public final class TodoController {

    @Autowired
    private TodoService mService;

    @GetMapping(produces = "application/json; charset=utf-8")
    public Iterable<Todo> getAll() {
        return mService.findAll();
    }

    @PostMapping(produces = "application/json; charset=utf-8")
    public ResponseEntity newTodo(@RequestBody Todo request) {
        Todo todo = mService._new(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(todo);
    }

    @PostMapping(produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    public ResponseEntity getByFilter(@RequestBody StringFilter stringFilter) {
        Iterable<Todo> todos = mService.get(stringFilter.getFilter());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(todos);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity getById(@PathVariable(name = "id") long id) {
        Optional<Todo> todo = mService.get(id);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(todo.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json; charset=utf-8")
    public ResponseEntity deleteById(@PathVariable(name = "id") long id) {
        Optional<Todo> todo = mService.delete(id);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(todo.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PutMapping(produces = "application/json; charset=utf-8", consumes = "application/json; charset=utf-8")
    public ResponseEntity updateTodo(@RequestBody Todo request) {
        Optional<Todo> todo = mService.update(request);
        if (todo.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(todo.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
