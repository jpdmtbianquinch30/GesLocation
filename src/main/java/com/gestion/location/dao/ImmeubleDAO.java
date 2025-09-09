package com.gestion.location.dao;

import com.gestion.location.entities.Immeuble;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImmeubleDAO {

    private final EntityManager em;

    public ImmeubleDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Immeuble immeuble) {
        em.persist(immeuble);
    }

    public void update(Immeuble immeuble) {
        em.merge(immeuble);
    }

    public boolean delete(Immeuble immeuble) {
        em.remove(em.contains(immeuble) ? immeuble : em.merge(immeuble));
        return true;
    }

    public Immeuble findById(Long id) {
        return em.find(Immeuble.class, id);
    }

    public List<Immeuble> findAll() {
        return em.createQuery("SELECT i FROM Immeuble i", Immeuble.class).getResultList();
    }

    public List<Immeuble> findByProprietaireId(Long proprietaireId) {
        return em.createQuery(
                        "SELECT i FROM Immeuble i WHERE i.proprietaire.id = :pid", Immeuble.class)
                .setParameter("pid", proprietaireId)
                .getResultList();
    }
}
