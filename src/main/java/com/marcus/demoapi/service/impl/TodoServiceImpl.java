package com.marcus.demoapi.service.impl;

import com.marcus.demoapi.model.Todo;
import com.marcus.demoapi.service.TodoService;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.Single;

import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.*;

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
    public Todo _new(Todo todo) {
        synchronized (TodoServiceImpl.class) {
            todo.setId(++lastId);
            mDatabase.put(todo.getId(), todo);
        }

        return todo;
    }

    @Override
    public Optional<Todo> get(long id) {
        return Optional.ofNullable(mDatabase.get(id));
    }

    @Override
    public Iterable<Todo> get(String description) {
        List<Todo> todoList = new ArrayList<>(20);
        Single<Todo> todoSingle =
                Observable.from(mDatabase.values())
                        .filter(item -> item.getDescription().contains(description))
                        .toSingle();

        todoSingle.subscribe(
                todoList::add
        );
        return todoList;
    }

    @Override
    public Optional<Todo> delete(long id) {
        Optional<Todo> item = Optional.ofNullable(mDatabase.get(id));
        synchronized (TodoServiceImpl.class) {
            item.ifPresent(todo -> mDatabase.remove(todo));
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
