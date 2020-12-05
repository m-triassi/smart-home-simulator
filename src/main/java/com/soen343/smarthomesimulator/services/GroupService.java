package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Group;
import com.soen343.smarthomesimulator.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    
    @Autowired
    GroupRepository repository;

    public Group findById(Long id) {
        return repository.findById(id).get();
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public Group save(Group group) {
        return repository.save(group);
    }
}
