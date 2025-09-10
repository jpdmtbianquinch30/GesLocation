package com.gestion.location.dao;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Unite;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class ContratDAO {
    private final EntityManager entityManager;

    public ContratDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void create(Contrat contrat) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(contrat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Contrat findById(Long id) {
        return entityManager.find(Contrat.class, id);
    }

    public List<Contrat> findAll() {
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c", Contrat.class);
        return query.getResultList();
    }

    public List<Contrat> findByLocataire(Locataire locataire) {
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c WHERE c.locataire = :locataire", Contrat.class);
        query.setParameter("locataire", locataire);
        return query.getResultList();
    }

    public List<Contrat> findByUnite(Unite unite) {
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c WHERE c.unite = :unite", Contrat.class);
        query.setParameter("unite", unite);
        return query.getResultList();
    }

    public List<Contrat> findByEtat(String etat) {
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c WHERE c.etatContrat = :etat", Contrat.class);
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    public List<Contrat> findContratsActifs() {
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c WHERE c.etatContrat = 'ACTIF'", Contrat.class);
        return query.getResultList();
    }

    public List<Contrat> findContratsExpirantDans(int jours) {
        LocalDate dateLimite = LocalDate.now().plusDays(jours);
        TypedQuery<Contrat> query = entityManager.createQuery(
                "SELECT c FROM Contrat c WHERE c.dateFin BETWEEN :aujourdhui AND :dateLimite", Contrat.class);
        query.setParameter("aujourdhui", LocalDate.now());
        query.setParameter("dateLimite", dateLimite);
        return query.getResultList();
    }

    public void update(Contrat contrat) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(contrat);
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
            Contrat contrat = entityManager.find(Contrat.class, id);
            if (contrat != null) {
                entityManager.remove(contrat);
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