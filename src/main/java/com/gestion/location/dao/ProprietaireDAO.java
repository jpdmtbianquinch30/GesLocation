package com.gestion.location.dao;

import com.gestion.location.entities.Proprietaire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ProprietaireDAO {
    private final EntityManager entityManager;

    public ProprietaireDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Proprietaire findById(Long id) {
        return entityManager.find(Proprietaire.class, id);
    }

    public List<Proprietaire> findAll() {
        TypedQuery<Proprietaire> query = entityManager.createQuery(
                "SELECT p FROM Proprietaire p", Proprietaire.class);
        return query.getResultList();
    }

    public Proprietaire findByEmail(String email) {
        TypedQuery<Proprietaire> query = entityManager.createQuery(
                "SELECT p FROM Proprietaire p WHERE p.email = :email", Proprietaire.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Proprietaire> findByNom(String nom) {
        TypedQuery<Proprietaire> query = entityManager.createQuery(
                "SELECT p FROM Proprietaire p WHERE p.nom LIKE :nom", Proprietaire.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }
}