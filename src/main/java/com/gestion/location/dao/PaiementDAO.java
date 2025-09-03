package com.gestion.location.dao;

import com.gestion.location.entities.Paiement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class PaiementDAO {

    private final EntityManager em;

    // Constructeur obligatoire avec EntityManager
    public PaiementDAO(EntityManager em) {
        this.em = em;
    }

    // Créer / ajouter un paiement
    public void save(Paiement paiement) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(paiement);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace(); // éventuellement remplacer par un logger
        }
    }

    // Mettre à jour un paiement
    public void update(Paiement paiement) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(paiement);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    // Supprimer un paiement
    public void delete(Paiement paiement) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.contains(paiement) ? paiement : em.merge(paiement));
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        }
    }

    // Rechercher un paiement par ID
    public Paiement findById(Long id) {
        return em.find(Paiement.class, id);
    }

    // Lister tous les paiements
    public List<Paiement> findAll() {
        return em.createQuery("SELECT p FROM Paiement p", Paiement.class)
                .getResultList();
    }
}
