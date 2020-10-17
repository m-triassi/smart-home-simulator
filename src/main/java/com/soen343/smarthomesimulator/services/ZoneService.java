package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.repositories.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

@Service
public class ZoneService {

    @Autowired
    ZoneRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    public List<Zone> findByHome(Long homeID) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Zone> query = cb.createQuery(Zone.class);
        Root<Zone> zone = query.from(Zone.class);

        Path<String> idPath = zone.get("home");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(idPath, homeID));
        query.select(zone).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }

    public Zone save(Zone zone) {
        return repository.save(zone);
    }

}
