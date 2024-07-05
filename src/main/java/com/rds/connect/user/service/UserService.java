package com.rds.connect.user.service;

import com.rds.connect.user.domain.entity.User;
import com.rds.connect.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void insertUser() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                    .username(String.valueOf(i) + "님")
                    .build();

            users.add(user);
        }

        userRepository.saveAll(users);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
