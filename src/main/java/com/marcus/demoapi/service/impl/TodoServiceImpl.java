package com.marcus.demoapi.service.impl;

import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private Map<Long, Todo> mDatabase;
    private long lastId = 0;

    {
        mDatabase = new HashMap<>();
    }

    @Override
    public Iterable<Todo> findAll() {
        return mDatabase.values();
    }

    @Override
    public Optional<Todo> _new(Todo todo) {
        synchronized (TodoServiceImpl.class) {
            if (todo.getId() == 0) {
                todo.setId(++lastId);
                mDatabase.put(todo.getId(), todo);
            } else {
                if (mDatabase.get(todo.getId()) == null) {
                    mDatabase.put(todo.getId(), todo);
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.of(todo);
    }

    @Override
    public Optional<Todo> get(long id) {
        return Optional.ofNullable(mDatabase.get(id));
    }

    @Override
    public Optional<Todo> delete(long id) {
        Optional<Todo> item = Optional.ofNullable(mDatabase.get(id));
        synchronized (TodoServiceImpl.class) {
            item.ifPresent(todo -> mDatabase.remove(todo.getId()));
        }
        return item;
    }

    @Override
    public Optional<Todo> update(@NotNull Todo todoToUpdate) {
        Optional<Todo> item = Optional.ofNullable(mDatabase.get(todoToUpdate.getId()));
        synchronized (TodoServiceImpl.class) {
            item.ifPresent(todo -> {
                mDatabase.put(todo.getId(), todo);
                todo.setUpdateAt(new Date(new java.util.Date().getTime()));
            });
        }
        return item;
    }
}
