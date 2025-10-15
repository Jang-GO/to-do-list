package janggo.practice.todolist.controller;

import janggo.practice.todolist.dto.UserRegistrationDto;
import janggo.practice.todolist.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @PostMapping("/register")
    public String registerUser(UserRegistrationDto registrationDto) {
        userService.registerNewUser(registrationDto);
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "user/auth";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/auth";
    }
}
