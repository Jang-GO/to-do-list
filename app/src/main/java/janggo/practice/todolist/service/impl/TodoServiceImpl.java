package janggo.practice.todolist.service.impl;

import janggo.practice.todolist.domain.Todo;
import janggo.practice.todolist.domain.User;
import janggo.practice.todolist.repository.TodoRepository;
import janggo.practice.todolist.repository.UserRepository;
import janggo.practice.todolist.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<Todo> findTodosForUser(User user) {
        return todoRepository.findByUserId(user.getId());
    }

    public void addTodo(String content, User user) {
        Todo todo = Todo.builder()
                .content(content)
                .user(user)
                .build();
        todoRepository.save(todo);
    }

    public void toggleTodoStatus(Long todoId, User user) {
        Todo todo = findTodoByIdAndValidateOwner(todoId, user);
        todo.toggleCompletion();
    }

    // 할 일 삭제
    public void deleteTodo(Long todoId, User user) {
        findTodoByIdAndValidateOwner(todoId, user);
        todoRepository.deleteById(todoId);
    }

    private Todo findTodoByIdAndValidateOwner(Long todoId, User user) {
        return todoRepository.findById(todoId)
                .filter(todo -> todo.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new IllegalStateException("존재하지 않거나 권한이 없는 할 일입니다."));
    }
}
