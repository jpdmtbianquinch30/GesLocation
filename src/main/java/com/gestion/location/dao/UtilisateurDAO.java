package com.gestion.location.dao;

import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UtilisateurDAO {
    private final EntityManager entityManager;

    public UtilisateurDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Utilisateur utilisateur) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(utilisateur);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Utilisateur findById(Long id) {
        return entityManager.find(Utilisateur.class, id);
    }

    public Utilisateur findByEmail(String email) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Utilisateur> findAll() {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u", Utilisateur.class);
        return query.getResultList();
    }

    public List<Utilisateur> findByRole(String role) {
        TypedQuery<Utilisateur> query = entityManager.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.role = :role", Utilisateur.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    public void update(Utilisateur utilisateur) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(utilisateur);
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
            Utilisateur utilisateur = entityManager.find(Utilisateur.class, id);
            if (utilisateur != null) {
                entityManager.remove(utilisateur);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public boolean emailExists(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE LOWER(u.email) = :email", Long.class);
        query.setParameter("email", email.toLowerCase());
        return query.getSingleResult() > 0;

    }
}