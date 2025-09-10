package com.gestion.location.service;

import com.gestion.location.dao.ImmeubleDAO;
import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImmeubleService {
    private final EntityManager entityManager;
    private final ImmeubleDAO immeubleDAO;

    public ImmeubleService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.immeubleDAO = new ImmeubleDAO(entityManager);
    }

    public Immeuble creerImmeuble(String nom, String adresse, String description, String equipements, Proprietaire proprietaire) {
        try {
            Immeuble immeuble = new Immeuble(nom, adresse, description, equipements);
            immeuble.setProprietaire(proprietaire);

            immeubleDAO.create(immeuble);
            return immeuble;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'immeuble: " + e.getMessage(), e);
        }
    }

    public Immeuble trouverImmeubleParId(Long id) {
        try {
            return immeubleDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'immeuble: " + e.getMessage(), e);
        }
    }

    public List<Immeuble> listerTousLesImmeubles() {
        try {
            return immeubleDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des immeubles: " + e.getMessage(), e);
        }
    }

    public List<Immeuble> listerImmeublesParProprietaire(Proprietaire proprietaire) {
        try {
            return immeubleDAO.findByProprietaire(proprietaire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des immeubles par propriétaire: " + e.getMessage(), e);
        }
    }

    public List<Immeuble> trouverImmeublesParNom(String nom) {
        try {
            return immeubleDAO.findByNom(nom);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des immeubles par nom: " + e.getMessage(), e);
        }
    }

    public List<Immeuble> trouverImmeublesParAdresse(String adresse) {
        try {
            return immeubleDAO.findByAdresse(adresse);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des immeubles par adresse: " + e.getMessage(), e);
        }
    }

    public void modifierImmeuble(Immeuble immeuble) {
        try {
            immeubleDAO.update(immeuble);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de l'immeuble: " + e.getMessage(), e);
        }
    }

    public void supprimerImmeuble(Long id) {
        try {
            immeubleDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'immeuble: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}