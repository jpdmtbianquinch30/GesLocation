package com.gestion.location.dao;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Paiement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class PaiementDAO {
    private final EntityManager entityManager;

    public PaiementDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Paiement paiement) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(paiement);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Paiement findById(Long id) {
        return entityManager.find(Paiement.class, id);
    }

    public List<Paiement> findAll() {
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p", Paiement.class);
        return query.getResultList();
    }

    public List<Paiement> findByLocataire(Locataire locataire) {
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p WHERE p.locataire = :locataire", Paiement.class);
        query.setParameter("locataire", locataire);
        return query.getResultList();
    }

    public List<Paiement> findByContrat(Contrat contrat) {
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p WHERE p.contrat = :contrat", Paiement.class);
        query.setParameter("contrat", contrat);
        return query.getResultList();
    }

    public List<Paiement> findByStatut(String statut) {
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p WHERE p.statutPaiement = :statut", Paiement.class);
        query.setParameter("statut", statut);
        return query.getResultList();
    }

    public List<Paiement> findByMoisCouvert(String moisCouvert) {
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p WHERE p.moisCouvert = :moisCouvert", Paiement.class);
        query.setParameter("moisCouvert", moisCouvert);
        return query.getResultList();
    }

    public List<Paiement> findPaiementsEnRetard() {
        LocalDate aujourdhui = LocalDate.now();
        TypedQuery<Paiement> query = entityManager.createQuery(
                "SELECT p FROM Paiement p WHERE p.statutPaiement = 'EN_ATTENTE' AND p.datePaiement < :aujourdhui", Paiement.class);
        query.setParameter("aujourdhui", aujourdhui);
        return query.getResultList();
    }

    public double getTotalPaiementsMois(String moisCouvert) {
        TypedQuery<Double> query = entityManager.createQuery(
                "SELECT SUM(p.montant) FROM Paiement p WHERE p.moisCouvert = :moisCouvert AND p.statutPaiement = 'VALIDE'", Double.class);
        query.setParameter("moisCouvert", moisCouvert);
        Double result = query.getSingleResult();
        return result != null ? result : 0.0;
    }

    public void update(Paiement paiement) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(paiement);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void delete(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Paiement paiement = entityManager.find(Paiement.class, id);
            if (paiement != null) {
                entityManager.remove(paiement);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }
}