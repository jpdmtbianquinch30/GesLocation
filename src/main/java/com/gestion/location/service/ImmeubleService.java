package com.gestion.location.service;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.dao.ImmeubleDAO;
import com.gestion.location.entities.Immeuble;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImmeubleService {

    private final ImmeubleDAO dao;
    private final EntityManager em;

    public ImmeubleService() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new ImmeubleDAO(em);
    }

    public void ajouter(Immeuble i) {
        em.getTransaction().begin();
        dao.save(i);
        em.getTransaction().commit();
    }

    public void modifier(Immeuble i) {
        em.getTransaction().begin();
        dao.update(i);
        em.getTransaction().commit();
    }

    public void supprimer(Long id) {
        em.getTransaction().begin();
        Immeuble i = dao.findById(id);
        if (i != null) dao.delete(i);
        em.getTransaction().commit();
    }

    public Immeuble trouverParId(Long id) {
        return dao.findById(id);
    }

    public List<Immeuble> lister() {
        return dao.findAll();
    }

    public List<Immeuble> listerParProprietaire(Long proprietaireId) {
        return dao.findByProprietaireId(proprietaireId);
    }
}
