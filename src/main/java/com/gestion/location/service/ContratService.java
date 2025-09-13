package com.gestion.location.service;

import com.gestion.location.dao.ContratDAO;
import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.entities.Unite;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.util.List;

public class ContratService {

    private final EntityManager entityManager;
    private final ContratDAO contratDAO;

    public ContratService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.contratDAO = new ContratDAO(entityManager);
    }

    // ============================
    // MÉTHODES CRUD
    // ============================

    /**
     * Crée un nouveau contrat
     */
    public void creerContrat(Contrat contrat) {
        contratDAO.create(contrat);
    }

    /**
     * Crée un contrat avec les paramètres fournis
     */
    public void creerContrat(LocalDate dateDebut, LocalDate dateFin, double loyerMensuel,
                             double caution, String etatContrat, Locataire locataire, Unite unite) {
        Contrat contrat = new Contrat();
        contrat.setDateDebut(dateDebut);
        contrat.setDateFin(dateFin);
        contrat.setLoyerMensuel(loyerMensuel);
        contrat.setCaution(caution);
        contrat.setEtatContrat(etatContrat);
        contrat.setLocataire(locataire);
        contrat.setUnite(unite);

        contratDAO.create(contrat);
    }

    /**
     * Met à jour un contrat existant
     */
    public void modifierContrat(Contrat contrat) {
        contratDAO.update(contrat);
    }

    /**
     * Supprime définitivement un contrat
     */
    public void supprimerContrat(Long id) {
        contratDAO.delete(id);
    }

    /**
     * Résilie un contrat (change son statut à RESILIE)
     */
    public void resilierContrat(Long id) {
        Contrat contrat = contratDAO.findById(id);
        if (contrat != null) {
            contrat.setEtatContrat("RESILIE");
            contratDAO.update(contrat);
        }
    }

    /**
     * Trouve un contrat par son ID
     */
    public Contrat trouverContratParId(Long id) {
        return contratDAO.findById(id);
    }

    // ============================
    // MÉTHODES DE LISTING
    // ============================

    /**
     * Liste tous les contrats d'un propriétaire avec jointures
     */
    public List<Contrat> listerContratsParProprietaire(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT c FROM Contrat c " +
                    "JOIN FETCH c.unite u " +
                    "JOIN FETCH u.immeuble i " +
                    "JOIN FETCH c.locataire l " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "ORDER BY c.dateCreation DESC";

            return entityManager.createQuery(jpql, Contrat.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des contrats: " + e.getMessage(), e);
        }
    }

    /**
     * Liste tous les contrats sans filtre
     */
    public List<Contrat> listerTousLesContrats() {
        return contratDAO.findAll();
    }

    /**
     * Liste les contrats par état (EN_ATTENTE, ACTIF, RESILIE, TERMINE)
     */
    public List<Contrat> listerContratsParEtat(String etat) {
        return contratDAO.findByEtat(etat);
    }

    /**
     * Liste uniquement les contrats actifs
     */
    public List<Contrat> listerContratsActifs() {
        return contratDAO.findContratsActifs();
    }

    /**
     * Liste les contrats qui expirent dans un certain nombre de jours
     */
    public List<Contrat> listerContratsExpirantDans(int jours) {
        return contratDAO.findContratsExpirantDans(jours);
    }

    /**
     * Liste les contrats d'un locataire spécifique
     */
    public List<Contrat> listerContratsParLocataire(Locataire locataire) {
        return contratDAO.findByLocataire(locataire);
    }

    // ============================
    // MÉTHODES UTILITAIRES
    // ============================

    /**
     * Vérifie la disponibilité d'une unité pour une période donnée
     */
    public boolean verifierDisponibiliteUnite(Unite unite, LocalDate dateDebut, LocalDate dateFin) {
        String jpql = "SELECT COUNT(c) FROM Contrat c " +
                "WHERE c.unite.id = :uniteId " +
                "AND c.etatContrat IN ('ACTIF', 'EN_ATTENTE') " +
                "AND ((c.dateDebut BETWEEN :dateDebut AND :dateFin) " +
                "OR (c.dateFin BETWEEN :dateDebut AND :dateFin) " +
                "OR (:dateDebut BETWEEN c.dateDebut AND c.dateFin) " +
                "OR (:dateFin BETWEEN c.dateDebut AND c.dateFin))";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("uniteId", unite.getId())
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getSingleResult();

        return count == 0;
    }

    /**
     * Ferme l'EntityManager
     */
    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}