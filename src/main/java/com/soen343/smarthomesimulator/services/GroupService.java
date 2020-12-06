package com.soen343.smarthomesimulator.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import com.soen343.smarthomesimulator.models.Group;
import com.soen343.smarthomesimulator.models.User;
import com.soen343.smarthomesimulator.models.Zone;
import com.soen343.smarthomesimulator.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    
    @Autowired
    GroupRepository repository;

    @PersistenceContext
    EntityManager entityManager;

    public Group findById(Long id) {
        return repository.findById(id).get();
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public Group save(Group group) {
        return repository.save(group);
    }

    public List<Zone> findByGroup(Long groupId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Zone> query = cb.createQuery(Zone.class);
        Root<Zone> zone = query.from(Zone.class);

        Path<String> idPath = zone.get("group");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(idPath, groupId));
        query.select(zone).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .getResultList();
    }
}
