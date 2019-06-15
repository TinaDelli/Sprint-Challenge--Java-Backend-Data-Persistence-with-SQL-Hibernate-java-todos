package com.lambdaschool.todos.controllers;

import com.lambdaschool.todos.models.Todo;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.services.ToDoService;
import com.lambdaschool.todos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController
{
    @Autowired
    private UserService userService;

    @Autowired
    private ToDoService toDoService;

    @GetMapping(value = "/users/mine", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
     ArrayList<Todo> todoList = (ArrayList<Todo>) toDoService.findByUserName(authentication.getName());
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/users", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser =  userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/users/userid/{userid}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/users/todos", produces = {"application/json"})
    public ResponseEntity<?> listAllTodos()
    {
        ArrayList<Todo> todoList = toDoService.findAll();
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PostMapping(value = "/users/todo/{userid}")
    public ResponseEntity<?> addNewQuote(@Valid @RequestBody Todo newTodo) throws URISyntaxException
    {
        newTodo = toDoService.save(newTodo);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newQuoteURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newTodo.getTodoid())
                .toUri();
        responseHeaders.setLocation(newQuoteURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "todos/todoid/{todoid}")
    public ResponseEntity<?> updateTodos(@RequestBody Todo newTodo, @PathVariable long todoid)
    {
        toDoService.update(newTodo, todoid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users/list", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }
}
