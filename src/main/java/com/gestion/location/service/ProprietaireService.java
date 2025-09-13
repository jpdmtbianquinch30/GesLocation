package com.gestion.location.service;

import com.gestion.location.dao.ProprietaireDAO;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProprietaireService {
    private final EntityManager entityManager;
    private final ProprietaireDAO proprietaireDAO;

    public ProprietaireService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.proprietaireDAO = new ProprietaireDAO(entityManager);
    }

    // CRUD Operations
    public Proprietaire trouverProprietaireParId(Long id) {
        try {
            return proprietaireDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du propriétaire: " + e.getMessage(), e);
        }
    }

    public void supprimerProprietaire(Long id) {
        try {
            // Réassigner les immeubles avant suppression
            String jpqlUpdate = "UPDATE Immeuble i SET i.proprietaire = null WHERE i.proprietaire.id = :id";
            entityManager.createQuery(jpqlUpdate)
                    .setParameter("id", id)
                    .executeUpdate();

            // Supprimer le propriétaire
            Proprietaire proprio = entityManager.find(Proprietaire.class, id);
            if (proprio != null) {
                entityManager.remove(proprio);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du propriétaire: " + e.getMessage(), e);
        }
    }

    // Listing methods
    public List<Proprietaire> listerTousLesProprietaires() {
        try {
            return proprietaireDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des propriétaires: " + e.getMessage(), e);
        }
    }

    // Search methods
    public Proprietaire trouverProprietaireParEmail(String email) {
        try {
            return proprietaireDAO.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du propriétaire par email: " + e.getMessage(), e);
        }
    }

    public List<Proprietaire> trouverProprietairesParNom(String nom) {
        try {
            return proprietaireDAO.findByNom(nom);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des propriétaires par nom: " + e.getMessage(), e);
        }
    }

    // Special operations
    public void supprimerTousProprietairesSauf(Long idExclusion) {
        try {
            entityManager.getTransaction().begin();

            // 1. Mettre à null les immeubles des propriétaires à supprimer
            String jpqlUpdate = "UPDATE Immeuble i SET i.proprietaire = null " +
                    "WHERE i.proprietaire.id IS NOT NULL AND i.proprietaire.id != :idExclusion";
            entityManager.createQuery(jpqlUpdate)
                    .setParameter("idExclusion", idExclusion)
                    .executeUpdate();

            // 2. Supprimer les propriétaires
            String jpqlDelete = "DELETE FROM Proprietaire p WHERE p.id != :idExclusion";
            int deletedCount = entityManager.createQuery(jpqlDelete)
                    .setParameter("idExclusion", idExclusion)
                    .executeUpdate();

            entityManager.getTransaction().commit();

            System.out.println(deletedCount + " propriétaires supprimés (sauf ID " + idExclusion + ")");

        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            throw new RuntimeException("Erreur lors de la suppression des propriétaires: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}