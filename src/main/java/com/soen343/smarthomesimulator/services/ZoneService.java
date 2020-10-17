package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.repositories.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {

    @Autowired
    ZoneRepository repository;

    public Zone save(Zone zone) {
        return repository.save(zone);
    }

}
