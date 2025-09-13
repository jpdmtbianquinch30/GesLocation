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

    // Méthodes CRUD de base
    public void create(Contrat contrat) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(contrat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erreur lors de la création du contrat: " + e.getMessage(), e);
        }
    }

    public Contrat findById(Long id) {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.id = :id", Contrat.class
            );
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du contrat: " + e.getMessage(), e);
        }
    }

    public List<Contrat> findAll() {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT DISTINCT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i", Contrat.class
            );
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats: " + e.getMessage(), e);
        }
    }

    // NOUVELLE MÉTHODE IMPORTANTE
    public List<Contrat> findByProprietaire(Long proprietaireId) {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT DISTINCT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE i.proprietaire.id = :proprietaireId", Contrat.class
            );
            query.setParameter("proprietaireId", proprietaireId);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats par propriétaire: " + e.getMessage(), e);
        }
    }

    public void update(Contrat contrat) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(contrat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erreur lors de la modification du contrat: " + e.getMessage(), e);
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
            if (transaction.isActive()) transaction.rollback();
            throw new RuntimeException("Erreur lors de la suppression du contrat: " + e.getMessage(), e);
        }
    }

    // Autres méthodes de recherche...
    public List<Contrat> findByLocataire(Locataire locataire) {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.locataire.id = :locataireId", Contrat.class
            );
            query.setParameter("locataireId", locataire.getId());
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats par locataire: " + e.getMessage(), e);
        }
    }

    public List<Contrat> findByUnite(Unite unite) {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.unite.id = :uniteId", Contrat.class
            );
            query.setParameter("uniteId", unite.getId());
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats par unité: " + e.getMessage(), e);
        }
    }

    public List<Contrat> findByEtat(String etat) {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.etatContrat = :etat", Contrat.class
            );
            query.setParameter("etat", etat);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats par état: " + e.getMessage(), e);
        }
    }

    public List<Contrat> findContratsActifs() {
        try {
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.etatContrat = 'ACTIF'", Contrat.class
            );
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats actifs: " + e.getMessage(), e);
        }
    }

    public List<Contrat> findContratsExpirantDans(int jours) {
        try {
            LocalDate dateLimite = LocalDate.now().plusDays(jours);
            TypedQuery<Contrat> query = entityManager.createQuery(
                    "SELECT c FROM Contrat c " +
                            "JOIN FETCH c.locataire l " +
                            "JOIN FETCH c.unite un " +
                            "JOIN FETCH un.immeuble i " +
                            "WHERE c.dateFin BETWEEN :aujourdhui AND :dateLimite " +
                            "AND c.etatContrat = 'ACTIF'", Contrat.class
            );
            query.setParameter("aujourdhui", LocalDate.now());
            query.setParameter("dateLimite", dateLimite);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des contrats expirant bientôt: " + e.getMessage(), e);
        }
    }

    public boolean hasContratActif(Unite unite) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(c) FROM Contrat c " +
                            "WHERE c.unite.id = :uniteId AND c.etatContrat = 'ACTIF'", Long.class
            );
            query.setParameter("uniteId", unite.getId());
            return query.getSingleResult() > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification des contrats actifs: " + e.getMessage(), e);
        }
    }
}