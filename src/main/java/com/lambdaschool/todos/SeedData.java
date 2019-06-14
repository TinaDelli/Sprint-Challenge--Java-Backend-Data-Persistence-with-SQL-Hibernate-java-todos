package com.lambdaschool.todos;

import com.lambdaschool.todos.models.Role;
import com.lambdaschool.todos.models.Todo;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.models.UserRoles;
import com.lambdaschool.todos.repository.RoleRepository;
import com.lambdaschool.todos.repository.ToDoRepository;
import com.lambdaschool.todos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
    RoleRepository rolerepos;
    UserRepository userrepos;
    ToDoRepository todorepos;

    public SeedData(RoleRepository rolerepos, UserRepository userrepos, ToDoRepository todorepos)
    {
        this.rolerepos = rolerepos;
        this.userrepos = userrepos;
        this.todorepos = todorepos;
    }

    @Override
    public void run(String[] args) throws Exception
    {
        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role ("usertwo");
        Role r4 = new Role ("userthree");

        ArrayList<UserRoles> admins = new ArrayList<>();
        admins.add(new UserRoles(new User(), r1));
//        admins.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> users = new ArrayList<>();
        users.add(new UserRoles(new User(), r2));

        ArrayList<UserRoles> userstwo = new ArrayList<>();
        users.add(new UserRoles(new User(), r3));

        ArrayList<UserRoles> usersthree = new ArrayList<>();
        users.add(new UserRoles(new User(), r4));

        rolerepos.save(r1);
        rolerepos.save(r2);
        rolerepos.save(r3);
        rolerepos.save(r4);

        User u1 = new User("barnbarn", "ILuvM4th!", users);
        User u2 = new User("admin", "password", admins);
        User u3 = new User("Bob", "password", userstwo);
        User u4 = new User("Jane", "password", usersthree);

        // the date and time string should get coverted to a datetime Java data type. This is done in the constructor!
        u4.getToDos().add(new Todo("Finish java-orders-swagger", "2019-01-13 04:04:04", u4));
        u4.getToDos().add(new Todo("Feed the turtles", "2019-03-01 04:04:04", u4));
        u4.getToDos().add(new Todo("Complete the sprint challenge", "2019-02-22 04:04:04", u4));

        u3.getToDos().add(new Todo("Walk the dogs", "2019-01-17 04:04:04", u3));
        u3.getToDos().add(new Todo("provide feedback to my instructor", "2019-02-13 04:04:04", u3));

        userrepos.save(u1);
        userrepos.save(u2);
        userrepos.save(u3);
        userrepos.save(u4);
    }
}
