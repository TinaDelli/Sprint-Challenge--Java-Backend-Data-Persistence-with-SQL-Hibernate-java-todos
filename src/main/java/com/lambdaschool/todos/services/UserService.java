package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.User;

import java.util.List;

public interface UserService
{

    List<User> findAll();

    User findUserById(long id);

    void delete(long id);

    User save(User user);

    User update(User user, long id);
}