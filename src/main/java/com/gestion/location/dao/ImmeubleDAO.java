package com.gestion.location.dao;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ImmeubleDAO {
    private final EntityManager entityManager;

    public ImmeubleDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Immeuble immeuble) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(immeuble);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Immeuble findById(Long id) {
        return entityManager.find(Immeuble.class, id);
    }

    public List<Immeuble> findAll() {
        TypedQuery<Immeuble> query = entityManager.createQuery(
                "SELECT i FROM Immeuble i", Immeuble.class);
        return query.getResultList();
    }

    public List<Immeuble> findByProprietaire(Proprietaire proprietaire) {
        TypedQuery<Immeuble> query = entityManager.createQuery(
                "SELECT i FROM Immeuble i WHERE i.proprietaire = :proprietaire", Immeuble.class);
        query.setParameter("proprietaire", proprietaire);
        return query.getResultList();
    }

    public List<Immeuble> findByNom(String nom) {
        TypedQuery<Immeuble> query = entityManager.createQuery(
                "SELECT i FROM Immeuble i WHERE i.nom LIKE :nom", Immeuble.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }

    public List<Immeuble> findByAdresse(String adresse) {
        TypedQuery<Immeuble> query = entityManager.createQuery(
                "SELECT i FROM Immeuble i WHERE i.adresse LIKE :adresse", Immeuble.class);
        query.setParameter("adresse", "%" + adresse + "%");
        return query.getResultList();
    }

    public void update(Immeuble immeuble) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(immeuble);
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
            Immeuble immeuble = entityManager.find(Immeuble.class, id);
            if (immeuble != null) {
                entityManager.remove(immeuble);
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