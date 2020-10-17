package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> findAll() {
        var iterator = repository.findAll();
        var users = new ArrayList<User>();
        iterator.forEach(e -> users.add(e));

        return users;
    }

    public User findById(Long id) {
        return repository.findById(id).get();
    }

    public User save(User user) {
        return repository.save(user);
    }
}
