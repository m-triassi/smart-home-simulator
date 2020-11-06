package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import com.soen343.smarthomesimulator.models.Appliance;
import com.soen343.smarthomesimulator.models.Opening;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.repositories.OpeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

@Service
public class OpeningService {

    @Autowired
    OpeningRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    public Opening findById(Long id) {
        return repository.findById(id).get();
    }

    public Opening save(Opening opening) {
        return repository.save(opening);
    }

    public List<Opening> findByZoneAndType(Long zoneId, String type) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Opening> query = cb.createQuery(Opening.class);
        Root<Opening> opening = query.from(Opening.class);

        Path<String> idPath = opening.get("zone");
        Path<String> typePath = opening.get("type");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(idPath, zoneId));
        predicates.add(cb.equal(typePath, type));
        query.select(opening).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }

    public List<Opening> findByZone(Long zoneId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Opening> query = cb.createQuery(Opening.class);
        Root<Opening> opening = query.from(Opening.class);

        Path<String> idPath = opening.get("zone");
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(idPath, zoneId));
        query.select(opening).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
