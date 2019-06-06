package com.marcus.demoapi.service;

import com.marcus.demoapi.model.Todo;

import java.util.Optional;

public interface TodoService {

    Iterable<Todo> findAll();

    Optional<Todo> _new(Todo todo);

    Optional<Todo> get(long id);

    Optional<Todo> delete(long id);

    Optional<Todo> update(Todo todo);

}
