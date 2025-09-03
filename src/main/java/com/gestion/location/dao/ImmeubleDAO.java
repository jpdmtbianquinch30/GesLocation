package com.gestion.location.dao;

import com.gestion.location.entities.Immeuble;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ImmeubleDAO {

    private final EntityManager em;

    public ImmeubleDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Immeuble immeuble) {
        try {
            em.getTransaction().begin();
            em.persist(immeuble);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void update(Immeuble immeuble) {
        try {
            em.getTransaction().begin();
            em.merge(immeuble);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Immeuble immeuble = em.find(Immeuble.class, id);
            if (immeuble != null) {
                em.remove(immeuble);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public Immeuble findById(Long id) {
        return em.find(Immeuble.class, id);
    }

    public List<Immeuble> findAll() {
        TypedQuery<Immeuble> query = em.createQuery("SELECT i FROM Immeuble i", Immeuble.class);
        return query.getResultList();
    }
}


