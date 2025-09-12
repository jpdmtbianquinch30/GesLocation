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

    // ✅ Créer un nouveau locataire
    public void create(Locataire locataire) {
        entityManager.persist(locataire);
    }

    // ✅ Trouver par ID (hérité de Utilisateur → id = utilisateur_id)
    public Locataire findById(Long id) {
        return entityManager.find(Locataire.class, id);
    }

    // ✅ Trouver par utilisateur_id (lié à la table utilisateur)
    public Locataire findByUtilisateurId(Long utilisateurId) {
        String jpql = "SELECT l FROM Locataire l WHERE l.id = :utilisateurId";
        TypedQuery<Locataire> query = entityManager.createQuery(jpql, Locataire.class);
        query.setParameter("utilisateurId", utilisateurId);
        List<Locataire> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    // ✅ Trouver par email
    public Locataire findByEmail(String email) {
        String jpql = "SELECT l FROM Locataire l WHERE l.email = :email";
        TypedQuery<Locataire> query = entityManager.createQuery(jpql, Locataire.class);
        query.setParameter("email", email);
        List<Locataire> result = query.getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    // ✅ Lister tous les locataires
    public List<Locataire> findAll() {
        String jpql = "SELECT l FROM Locataire l";
        return entityManager.createQuery(jpql, Locataire.class).getResultList();
    }

    // ✅ Lister par nom
    public List<Locataire> findByNom(String nom) {
        String jpql = "SELECT l FROM Locataire l WHERE l.nom = :nom";
        TypedQuery<Locataire> query = entityManager.createQuery(jpql, Locataire.class);
        query.setParameter("nom", nom);
        return query.getResultList();
    }

    // ✅ Lister par profession
    public List<Locataire> findByProfession(String profession) {
        String jpql = "SELECT l FROM Locataire l WHERE l.profession = :profession";
        TypedQuery<Locataire> query = entityManager.createQuery(jpql, Locataire.class);
        query.setParameter("profession", profession);
        return query.getResultList();
    }

    // ✅ Mise à jour
    public void update(Locataire locataire) {
        entityManager.merge(locataire);
    }

    // ✅ Suppression
    public void delete(Long id) {
        Locataire locataire = findById(id);
        if (locataire != null) {
            entityManager.remove(locataire);
        }
    }
}
