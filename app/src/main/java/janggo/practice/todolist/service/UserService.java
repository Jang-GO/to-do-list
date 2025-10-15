package janggo.practice.todolist.service;

import janggo.practice.todolist.dto.UserRegistrationDto;

public interface UserService {
    void registerNewUser(UserRegistrationDto registrationDto);
}
