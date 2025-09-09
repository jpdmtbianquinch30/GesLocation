package com.gestion.location.service;

import com.gestion.location.dao.ContratDAO;
import com.gestion.location.entities.Contrat;
import com.gestion.location.config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ContratService {
    private final EntityManager em;
    private final ContratDAO dao;

    public ContratService() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new ContratDAO(em);
    }
    public List<Contrat> listerContrats() {
        // Alias pour les servlets
        return lister();
    }

    public void ajouter(Contrat c) { dao.ajouter(c); }
    public void modifier(Contrat c) { dao.modifier(c); }
    public void supprimer(Contrat c) { dao.supprimer(c); }
    public void supprimer(Long id) {
        Contrat c = trouverParId(id);
        if (c != null) dao.supprimer(c);
    }

    public Contrat trouverParId(Long id) { return dao.trouverParId(id); }
    public List<Contrat> lister() { return dao.lister(); }
    public List<Contrat> listerParProprietaire(Long proprietaireId) { return dao.listerParProprietaire(proprietaireId); }
}
