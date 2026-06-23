package com.interviewace.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.interviewace.dto.LoginRequest;
import com.interviewace.dto.RegisterRequest;
import com.interviewace.entity.User;
import com.interviewace.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User register(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));

        return repository.save(user);
    }

    public String login(LoginRequest request) {

        Optional<User> optionalUser =
                repository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found";
        }

        User user = optionalUser.get();

        if (encoder.matches(
                request.getPassword(),
                user.getPassword())) {

            return "Login Successful";
        }

        return "Invalid Password";
    }
}