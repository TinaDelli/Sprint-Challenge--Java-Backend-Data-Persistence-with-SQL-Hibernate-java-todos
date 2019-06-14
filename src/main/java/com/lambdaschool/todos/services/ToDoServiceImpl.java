package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todo;
import com.lambdaschool.todos.repository.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "toDoService")
public class ToDoServiceImpl implements ToDoService
{
    @Autowired
    private ToDoRepository todorepos;

    @Override
    public ArrayList<Todo> findAll()
    {
        ArrayList<Todo> list= new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findToDoById(long id)
    {
       return todorepos.findById(id)
               .orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public List<Todo> findByUserName(String username)
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);

        list.removeIf(t -> !t.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }

    @Override
    public void delete(long id)
    {
        if (todorepos.findById(id).isPresent())
        {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (todorepos.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
            {
                todorepos.deleteById(id);
            }
            else
            {
                throw new EntityNotFoundException(Long.toString(id) + " " + authentication.getName());
            }
        }
        else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Override
    public Todo save(Todo todo)
    {
        Todo newTodo = new Todo();
        newTodo.setDescription(todo.getDescription());
       newTodo.setUser(todo.getUser());
        return todorepos.save(todo);
    }

    @Override
    public Todo update(Todo todo, long id)
    {
        Todo currentTodo = todorepos.findById(id)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(id)));

        if(todo.getDescription() != null)
        {
            currentTodo.setDescription(todo.getDescription());
        }

        return todorepos.save(currentTodo);
    }
}
