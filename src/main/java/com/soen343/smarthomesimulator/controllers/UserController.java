package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<User> index() {
        return userService.findAll();
    }

    @GetMapping("/user")
    public User show(@RequestParam(value = "id") Long id) {
        // TODO: get some user with "id"
        return userService.findById(id);
    }

    @PostMapping("/user/store")
    public String store(@RequestParam(value = "name") String name,
                        @RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password) {

        // TODO: Input Validation

        if (userService.save(new User(name, email, password)) != null) {
            return "{\"success\": true}";
        }

        return "{\"success\": false}";
    }
}