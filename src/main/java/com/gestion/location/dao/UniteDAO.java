package com.gestion.location.dao;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Unite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UniteDAO {
    private final EntityManager entityManager;

    public UniteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Unite unite) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(unite);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Unite findById(Long id) {
        return entityManager.find(Unite.class, id);
    }

    public List<Unite> findAll() {
        TypedQuery<Unite> query = entityManager.createQuery(
                "SELECT u FROM Unite u", Unite.class);
        return query.getResultList();
    }

    public List<Unite> findByImmeuble(Immeuble immeuble) {
        TypedQuery<Unite> query = entityManager.createQuery(
                "SELECT u FROM Unite u WHERE u.immeuble = :immeuble", Unite.class);
        query.setParameter("immeuble", immeuble);
        return query.getResultList();
    }

    public List<Unite> findDisponibles() {
        TypedQuery<Unite> query = entityManager.createQuery(
                "SELECT u FROM Unite u WHERE u.estDisponible = true", Unite.class);
        return query.getResultList();
    }

    public List<Unite> findByNombrePieces(int nombrePieces) {
        TypedQuery<Unite> query = entityManager.createQuery(
                "SELECT u FROM Unite u WHERE u.nombrePieces = :nombrePieces", Unite.class);
        query.setParameter("nombrePieces", nombrePieces);
        return query.getResultList();
    }

    public List<Unite> findByLoyerBetween(double min, double max) {
        TypedQuery<Unite> query = entityManager.createQuery(
                "SELECT u FROM Unite u WHERE u.loyerMensuel BETWEEN :min AND :max", Unite.class);
        query.setParameter("min", min);
        query.setParameter("max", max);
        return query.getResultList();
    }

    public void update(Unite unite) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(unite);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Unite unite = entityManager.find(Unite.class, id);
            if (unite != null) {
                entityManager.remove(unite);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}