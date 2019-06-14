package com.lambdaschool.todos.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String description;

    private String datestarted;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userid",
                nullable = false)
    @JsonIgnoreProperties({"toDos", "todo"})
    private User user;

    public Todo()
    {
    }

    public Todo(String description, String datestarted)
    {
        this.description = description;
        this.datestarted = datestarted;
    }

    public Todo(String description, String datestarted, User user)
    {
        this.description = description;
        this.datestarted = datestarted;
        this.user = user;
    }

    public Todo(String description, User user)
    {
        this.description = description;
        this.user = user;
    }

    public Todo(String description, String datestarted, boolean completed, User user)
    {
        this.description = description;
        this.datestarted = datestarted;
        this.completed = completed;
        this.user = user;
    }

    public long getTodoid()
    {
        return todoid;
    }

    public void setTodoid(long todoid)
    {
        this.todoid = todoid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDatestarted()
    {
        return datestarted;
    }

    public void setDatestarted(Long datestarted)
    {
        this.datestarted = new SimpleDateFormat("dd MMM yyyy HH:mm:ss:SSS Z").format(new Date(datestarted));
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
