package com.klef.fsad.exam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class ClientDemo implements CommandLineRunner {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // I. Insert records using a persistent object
        Restaurant r1 = new Restaurant("Tasty Bites", LocalDate.now(), "Open", "Downtown", 4.5);
        Restaurant r2 = new Restaurant("Sunny Cafe", LocalDate.now().minusDays(1), "Open", "Uptown", 4.0);

        em.persist(r1);
        em.persist(r2);
        em.flush();

        System.out.println("Inserted: " + r1);
        System.out.println("Inserted: " + r2);

        // II. Update fields such as Name and Status based on the ID using HQL with named parameters
        String hql = "UPDATE Restaurant r SET r.name = :name, r.status = :status WHERE r.id = :id";
        Query q = em.createQuery(hql);
        q.setParameter("name", "Tasty Bites - Renovated");
        q.setParameter("status", "Temporarily Closed");
        q.setParameter("id", r1.getId());
        int updated = q.executeUpdate();
        System.out.println("Rows updated: " + updated);

        Restaurant updatedR = em.find(Restaurant.class, r1.getId());
        System.out.println("After update: " + updatedR);
    }
}
