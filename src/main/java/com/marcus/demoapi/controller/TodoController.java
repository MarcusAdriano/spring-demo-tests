package com.marcus.demoapi.controller;

import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("todo")
public final class TodoController {

    @Autowired
    private TodoService mService;

    @GetMapping()
    public Iterable<Todo> getAll() {
        return mService.findAll();
    }

    @PostMapping
    public ResponseEntity _new(@RequestBody Todo request) {
        mService._new(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
