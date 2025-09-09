package com.gestion.location.service;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.dao.LocataireDAO;
import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LocataireService {

    private final LocataireDAO dao;

    public LocataireService() {
        EntityManager em = JPAUtil.getEntityManager();
        this.dao = new LocataireDAO(em);
    }

    public List<Utilisateur> listerLocatairesParProprietaire(Long proprietaireId) {
        return dao.findLocatairesByProprietaireId(proprietaireId);
    }
}
