package com.gestion.location.dao;

import com.gestion.location.entities.Unite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UniteDAO {
    private final EntityManager em;

    public UniteDAO(EntityManager em) {
        this.em = em;
    }

    public void ajouter(Unite u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public void modifier(Unite u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public void supprimer(Unite u) {
        em.getTransaction().begin();
        em.remove(em.contains(u) ? u : em.merge(u));
        em.getTransaction().commit();
    }

    public Unite trouverParId(Long id) {
        return em.find(Unite.class, id);
    }

    public List<Unite> lister() {
        TypedQuery<Unite> query = em.createQuery("SELECT u FROM Unite u", Unite.class);
        return query.getResultList();
    }

    public List<Unite> listerParImmeuble(Long immeubleId) {
        TypedQuery<Unite> query = em.createQuery(
                "SELECT u FROM Unite u WHERE u.immeuble.id = :id", Unite.class);
        query.setParameter("id", immeubleId);
        return query.getResultList();
    }

    public List<Unite> listerParProprietaire(Long proprietaireId) {
        TypedQuery<Unite> query = em.createQuery(
                "SELECT u FROM Unite u WHERE u.immeuble.proprietaire.id = :id", Unite.class);
        query.setParameter("id", proprietaireId);
        return query.getResultList();
    }
}
