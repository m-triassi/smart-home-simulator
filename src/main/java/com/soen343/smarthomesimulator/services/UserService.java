package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public User save(User user) {
        return repository.save(user);
    }

    // save list of users
    public List<User> saveUsers(List<User> users) {
        return (List<User>) repository.saveAll(users);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public User findUserByCredentials(String email, String password) {
        User potentialUser = repository.findByEmail(email);
        if (potentialUser != null && new BCryptPasswordEncoder().matches(password, potentialUser.getPassword())) {
            return potentialUser;
        } else {
            return null;
        }
    }
}
