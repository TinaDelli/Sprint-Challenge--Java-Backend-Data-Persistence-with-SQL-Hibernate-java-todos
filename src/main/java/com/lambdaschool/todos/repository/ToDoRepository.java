package com.lambdaschool.todos.repository;

import com.lambdaschool.todos.models.Todo;
import org.springframework.data.repository.CrudRepository;

public interface ToDoRepository extends CrudRepository<Todo, Long>
{
}
