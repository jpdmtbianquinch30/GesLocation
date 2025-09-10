package com.gestion.location.dao;

import com.gestion.location.entities.Locataire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class LocataireDAO {
    private final EntityManager entityManager;

    public LocataireDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Locataire findById(Long id) {
        return entityManager.find(Locataire.class, id);
    }

    public List<Locataire> findAll() {
        TypedQuery<Locataire> query = entityManager.createQuery(
                "SELECT l FROM Locataire l", Locataire.class);
        return query.getResultList();
    }

    public Locataire findByEmail(String email) {
        TypedQuery<Locataire> query = entityManager.createQuery(
                "SELECT l FROM Locataire l WHERE l.email = :email", Locataire.class);
        query.setParameter("email", email);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<Locataire> findByNom(String nom) {
        TypedQuery<Locataire> query = entityManager.createQuery(
                "SELECT l FROM Locataire l WHERE l.nom LIKE :nom", Locataire.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }

    public List<Locataire> findByProfession(String profession) {
        TypedQuery<Locataire> query = entityManager.createQuery(
                "SELECT l FROM Locataire l WHERE l.profession LIKE :profession", Locataire.class);
        query.setParameter("profession", "%" + profession + "%");
        return query.getResultList();
    }
}