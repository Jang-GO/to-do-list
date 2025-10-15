package janggo.practice.todolist.service;

import janggo.practice.todolist.domain.Todo;
import janggo.practice.todolist.domain.User;

import java.util.List;

public interface TodoService {
    List<Todo> findTodosForUser(User user);
    void toggleTodoStatus(Long todoId, User user);
    void addTodo(String content, User user);
    void deleteTodo(Long todoId, User user);
}
