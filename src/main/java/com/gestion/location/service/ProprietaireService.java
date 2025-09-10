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

    public Proprietaire trouverProprietaireParId(Long id) {
        try {
            return proprietaireDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du propriétaire: " + e.getMessage(), e);
        }
    }

    public List<Proprietaire> listerTousLesProprietaires() {
        try {
            return proprietaireDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des propriétaires: " + e.getMessage(), e);
        }
    }

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

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}