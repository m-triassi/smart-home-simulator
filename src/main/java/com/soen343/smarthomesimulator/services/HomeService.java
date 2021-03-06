package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Home;
import com.soen343.smarthomesimulator.repositories.HomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    @Autowired
    HomeRepository repository;

    public Home findById(Long id) {
        return repository.findById(id).get();
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public Home save(Home home) {
        return repository.save(home);
    }

}
