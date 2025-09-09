package com.gestion.location.dao;

import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class UtilisateurDAO {
    private final EntityManager em;

    public UtilisateurDAO(EntityManager em) { this.em = em; }

    public void ajouter(Utilisateur u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public void modifier(Utilisateur u) {
        em.getTransaction().begin();
        em.merge(u);
        em.getTransaction().commit();
    }

    public void supprimer(Utilisateur u) {
        em.getTransaction().begin();
        em.remove(em.contains(u) ? u : em.merge(u));
        em.getTransaction().commit();
    }

    public Utilisateur trouverParId(Long id) { return em.find(Utilisateur.class, id); }

    public List<Utilisateur> lister() {
        TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class);
        return query.getResultList();
    }

    public List<Utilisateur> listerParRole(String role) {
        TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u WHERE u.role = :role", Utilisateur.class);
        query.setParameter("role", role);
        return query.getResultList();
    }
}
