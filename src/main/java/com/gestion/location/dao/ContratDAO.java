package com.gestion.location.dao;

import com.gestion.location.entities.Contrat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ContratDAO {
    private final EntityManager em;

    public ContratDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Contrat c) {
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }

    public void modifier(Contrat c) {
        em.getTransaction().begin();
        em.merge(c);
        em.getTransaction().commit();
    }

    public void supprimer(Contrat c) {
        em.getTransaction().begin();
        em.remove(em.contains(c) ? c : em.merge(c));
        em.getTransaction().commit();
    }

    public Contrat trouverParId(Long id) {
        return em.find(Contrat.class, id);
    }

    public List<Contrat> lister() {
        TypedQuery<Contrat> query = em.createQuery("SELECT c FROM Contrat c", Contrat.class);
        return query.getResultList();
    }

    public List<Contrat> listerParProprietaire(Long proprietaireId) {
        TypedQuery<Contrat> query = em.createQuery(
                "SELECT c FROM Contrat c WHERE c.unite.immeuble.proprietaire.id = :id", Contrat.class);
        query.setParameter("id", proprietaireId);
        return query.getResultList();
    }
}
