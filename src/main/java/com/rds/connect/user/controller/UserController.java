package com.rds.connect.user.controller;

import com.rds.connect.user.domain.entity.User;
import com.rds.connect.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/api/users")
    public ResponseEntity<Void> insertUser() {
        HttpStatus status = HttpStatus.CREATED;
        userService.insertUser();

        return new ResponseEntity<>(status);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
        HttpStatus status = HttpStatus.OK;
        List<User> users = userService.getUsers();

        return new ResponseEntity<>(users, status);
    }
}
