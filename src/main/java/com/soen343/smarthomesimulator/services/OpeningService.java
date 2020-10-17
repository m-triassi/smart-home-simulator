package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Opening;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.repositories.OpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpeningService {

    @Autowired
    OpeningRepository repository;

    public Opening save(Opening opening) {
        return repository.save(opening);
    }
}
