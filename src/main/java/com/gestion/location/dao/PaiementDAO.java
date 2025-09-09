package com.gestion.location.dao;

import com.gestion.location.entities.Paiement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PaiementDAO {
    private final EntityManager em;

    public PaiementDAO(EntityManager em) { this.em = em; }

    public void ajouter(Paiement p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public void modifier(Paiement p) {
        em.getTransaction().begin();
        em.merge(p);
        em.getTransaction().commit();
    }

    public void supprimer(Paiement p) {
        em.getTransaction().begin();
        em.remove(em.contains(p) ? p : em.merge(p));
        em.getTransaction().commit();
    }

    public Paiement trouverParId(Long id) {
        return em.find(Paiement.class, id);
    }

    public List<Paiement> lister() {
        TypedQuery<Paiement> query = em.createQuery("SELECT p FROM Paiement p", Paiement.class);
        return query.getResultList();
    }

    public List<Paiement> listerParLocataire(Long locataireId) {
        TypedQuery<Paiement> query = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.locataire.id = :id", Paiement.class);
        query.setParameter("id", locataireId);
        return query.getResultList();
    }

    public List<Paiement> listerParUnite(Long uniteId) {
        TypedQuery<Paiement> query = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.unite.id = :id", Paiement.class);
        query.setParameter("id", uniteId);
        return query.getResultList();
    }

    // ✅ ajout pour ProprietaireDashboardServlet
    public List<Paiement> listerParProprietaire(Long proprietaireId) {
        TypedQuery<Paiement> query = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.unite.immeuble.proprietaire.id = :id", Paiement.class);
        query.setParameter("id", proprietaireId);
        return query.getResultList();
    }
}
