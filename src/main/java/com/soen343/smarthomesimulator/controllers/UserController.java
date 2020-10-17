package com.soen343.smarthomesimulator.controllers;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/login")
    public User getUser(@RequestParam(value = "email") String email,
                        @RequestParam(value = "password") String password) {

        //TODO: instead of returning the user create, implement persistence for user login
        return userService.findUserByCredentials(email, password);
    }

    @PostMapping(value = "/user/store")
    public String store(@RequestParam String name,
                        @RequestParam String email,
                        @RequestParam String password) {

        // TODO: Input Validation

        password = passwordEncoder().encode(password);

        if (userService.save(new User(name, email, password)) != null) {
            return "{\"success\": true}";
        }

        return "{\"success\": false}";
    }


    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}