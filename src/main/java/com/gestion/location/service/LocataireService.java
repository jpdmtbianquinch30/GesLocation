package com.gestion.location.service;

import com.gestion.location.dao.LocataireDAO;
import com.gestion.location.entities.Locataire;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LocataireService {
    private final EntityManager entityManager;
    private final LocataireDAO locataireDAO;

    public LocataireService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.locataireDAO = new LocataireDAO(entityManager);
    }

    public Locataire trouverLocataireParId(Long id) {
        try {
            return locataireDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du locataire: " + e.getMessage(), e);
        }
    }

    public List<Locataire> listerTousLesLocataires() {
        try {
            return locataireDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des locataires: " + e.getMessage(), e);
        }
    }

    public Locataire trouverLocataireParEmail(String email) {
        try {
            return locataireDAO.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du locataire par email: " + e.getMessage(), e);
        }
    }

    public List<Locataire> trouverLocatairesParNom(String nom) {
        try {
            return locataireDAO.findByNom(nom);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des locataires par nom: " + e.getMessage(), e);
        }
    }

    public List<Locataire> trouverLocatairesParProfession(String profession) {
        try {
            return locataireDAO.findByProfession(profession);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des locataires par profession: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}