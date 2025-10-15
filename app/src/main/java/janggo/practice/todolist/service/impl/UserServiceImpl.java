package janggo.practice.todolist.service.impl;

import janggo.practice.todolist.domain.User;
import janggo.practice.todolist.dto.UserRegistrationDto;
import janggo.practice.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerNewUser(UserRegistrationDto registrationDto) {
        if (userRepository.findByUsername(registrationDto.username()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 사용자 이름입니다.");
        }

        User user = User.builder()
                .username(registrationDto.username())
                .password(passwordEncoder.encode(registrationDto.password()))
                .build();

        userRepository.save(user);
    }
}
