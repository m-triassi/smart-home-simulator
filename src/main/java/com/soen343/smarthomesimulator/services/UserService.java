package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired UserRepository repository;

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

    public void deleteUser(Long id){
        repository.deleteById(id);
    }

    public User updateUser(User updatedUser){
        User existingUser = findById(updatedUser.getId());
        if(existingUser == null)
            return existingUser;
        existingUser.setHome(updatedUser.getHome());
        existingUser.setZone(updatedUser.getZone());
        // existingUser.setEmail(updatedUser.getEmail());
        return save(existingUser);
    }
}
