package com.gestion.location.service;

import com.gestion.location.dao.UniteDAO;
import com.gestion.location.entities.Unite;
import com.gestion.location.config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UniteService {
    private final EntityManager em;
    private final UniteDAO dao;

    public UniteService() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UniteDAO(em);
    }
    public List<Unite> listerUnites() {
        // Alias pour les servlets
        return lister();
    }

    public void ajouter(Unite u) { dao.ajouter(u); }
    public void modifier(Unite u) { dao.modifier(u); }
    public void supprimer(Unite u) { dao.supprimer(u); }
    public void supprimer(Long id) {
        Unite u = trouverParId(id);
        if (u != null) dao.supprimer(u);
    }

    public Unite trouverParId(Long id) { return dao.trouverParId(id); }
    public List<Unite> lister() { return dao.lister(); }
    public List<Unite> listerParImmeuble(Long immeubleId) { return dao.listerParImmeuble(immeubleId); }
    public List<Unite> listerParProprietaire(Long proprietaireId) { return dao.listerParProprietaire(proprietaireId); }
}
