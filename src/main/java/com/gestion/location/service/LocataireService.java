package com.gestion.location.service;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.dao.LocataireDAO;
import com.gestion.location.entities.Locataire;

import jakarta.persistence.EntityManager;
import java.util.List;

public class LocataireService {

    private final EntityManager em;
    private final LocataireDAO locataireDAO;

    public LocataireService() {
        this.em = JPAUtil.getEntityManager();  // Récupère l'EntityManager
        this.locataireDAO = new LocataireDAO(em);
    }

    // Méthode pour ajouter ou mettre à jour un locataire
    public void ajouterLocataire(Locataire locataire) {
        if (locataire.getId() == null) {
            locataireDAO.create(locataire);  // nouveau locataire
        } else {
            locataireDAO.update(locataire);  // locataire existant
        }
    }

    // Lister tous les locataires
    public List<Locataire> listerLocataires() {
        return locataireDAO.findAll();
    }

    // Chercher un locataire par ID
    public Locataire trouverParId(Long id) {
        return locataireDAO.findById(id);
    }
}
