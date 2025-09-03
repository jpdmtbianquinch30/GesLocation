package com.gestion.location.service;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.dao.ImmeubleDAO;
import com.gestion.location.entities.Immeuble;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImmeubleService {

    private final EntityManager em = JPAUtil.getEntityManager();
    private final ImmeubleDAO dao = new ImmeubleDAO(em);

    public void ajouter(Immeuble i) {
        dao.create(i);
    }

    public void modifier(Immeuble i) {
        dao.update(i);
    }

    public void supprimer(int id) {
        Immeuble i = dao.findById((long) id);
        if (i != null) {
            dao.update(i); // ou dao.delete(i.getId()) si tu veux rester avec delete(Long)
        }
    }

    public List<Immeuble> lister() {
        return dao.findAll();
    }

    // Méthode pour le servlet
    public Immeuble getById(int id) {
        return dao.findById((long) id);
    }
}
