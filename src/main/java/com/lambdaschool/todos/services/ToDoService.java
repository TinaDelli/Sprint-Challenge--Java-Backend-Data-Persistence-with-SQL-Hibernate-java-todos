package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todo;

import java.util.ArrayList;
import java.util.List;

public interface ToDoService
{
    ArrayList<Todo> findAll();

    Todo findToDoById(long id);

    List<Todo> findByUserName(String username);

    void delete(long id);

    Todo save(Todo todo);

    Todo update(Todo todo, long id);

}
