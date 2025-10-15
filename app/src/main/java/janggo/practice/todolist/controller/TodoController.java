package janggo.practice.todolist.controller;

import janggo.practice.todolist.config.CustomUserDetails;
import janggo.practice.todolist.domain.Todo;
import janggo.practice.todolist.domain.User;
import janggo.practice.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todos")
    public String listTodos(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        User currentUser = userDetails.getUser();
        model.addAttribute("todos", todoService.findTodosForUser(currentUser));
        return "todo/todos";
    }

    @PostMapping("/todos/add")
    public String addTodo(@AuthenticationPrincipal CustomUserDetails userDetails,@RequestParam String content) {
        User currentUser = userDetails.getUser();
        todoService.addTodo(content, currentUser);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        User currentUser = userDetails.getUser();
        todoService.toggleTodoStatus(id, currentUser);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long id) {
        User currentUser = userDetails.getUser();
        todoService.deleteTodo(id, currentUser);
        return "redirect:/todos";
    }
}
