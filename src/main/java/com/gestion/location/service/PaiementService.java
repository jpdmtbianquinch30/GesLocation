package com.gestion.location.service;

import com.gestion.location.dao.PaiementDAO;
import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Paiement;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaiementService {
    private final EntityManager entityManager;
    private final PaiementDAO paiementDAO;

    public PaiementService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.paiementDAO = new PaiementDAO(entityManager);
    }

    // AJOUTER ces méthodes pour les propriétaires :

    /**
     * Récupère tous les paiements associés aux propriétés d'un propriétaire
     */
    public List<Paiement> listerPaiementsParProprietaire(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT p FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "ORDER BY p.datePaiement DESC";

            return entityManager.createQuery(jpql, Paiement.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements par propriétaire: " + e.getMessage(), e);
        }
    }

    /**
     * Calcule le revenu mensuel d'un propriétaire
     */
    public double calculerRevenuMensuel(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "AND YEAR(p.datePaiement) = YEAR(CURRENT_DATE) " +
                    "AND MONTH(p.datePaiement) = MONTH(CURRENT_DATE) " +
                    "AND p.statutPaiement = 'VALIDE'";

            return entityManager.createQuery(jpql, Double.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du revenu mensuel: " + e.getMessage(), e);
        }
    }

    /**
     * Calcule le revenu annuel d'un propriétaire
     */
    public double calculerRevenuAnnuel(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "AND YEAR(p.datePaiement) = YEAR(CURRENT_DATE) " +
                    "AND p.statutPaiement = 'VALIDE'";

            return entityManager.createQuery(jpql, Double.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du revenu annuel: " + e.getMessage(), e);
        }
    }

    /**
     * Calcule le revenu total d'un propriétaire
     */
    public double calculerRevenuTotal(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "AND p.statutPaiement = 'VALIDE'";

            return entityManager.createQuery(jpql, Double.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du revenu total: " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'évolution des revenus mensuels sur les 6 derniers mois
     */
    public Map<String, Double> getEvolutionRevenusMensuels(Proprietaire proprietaire) {
        try {
            Map<String, Double> evolution = new LinkedHashMap<>();

            // Obtenir les 6 derniers mois
            for (int i = 5; i >= 0; i--) {
                YearMonth mois = YearMonth.now().minusMonths(i);
                String moisAnnee = mois.getMonth().toString() + " " + mois.getYear();

                String jpql = "SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p " +
                        "JOIN p.contrat c " +
                        "JOIN c.unite u " +
                        "JOIN u.immeuble i " +
                        "WHERE i.proprietaire.id = :proprietaireId " +
                        "AND YEAR(p.datePaiement) = :annee " +
                        "AND MONTH(p.datePaiement) = :mois " +
                        "AND p.statutPaiement = 'VALIDE'";

                Double revenuMensuel = entityManager.createQuery(jpql, Double.class)
                        .setParameter("proprietaireId", proprietaire.getId())
                        .setParameter("annee", mois.getYear())
                        .setParameter("mois", mois.getMonthValue())
                        .getSingleResult();

                evolution.put(moisAnnee, revenuMensuel);
            }

            return evolution;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul de l'évolution des revenus: " + e.getMessage(), e);
        }
    }

    /**
     * Récupère les paiements en attente pour un propriétaire
     */
    public List<Paiement> listerPaiementsEnAttenteParProprietaire(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT p FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "AND p.statutPaiement = 'EN_ATTENTE' " +
                    "ORDER BY p.datePaiement DESC";

            return entityManager.createQuery(jpql, Paiement.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements en attente: " + e.getMessage(), e);
        }
    }

    /**
     * Calcule le montant total des paiements en attente
     */
    public double calculerTotalPaiementsEnAttente(Proprietaire proprietaire) {
        try {
            String jpql = "SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p " +
                    "JOIN p.contrat c " +
                    "JOIN c.unite u " +
                    "JOIN u.immeuble i " +
                    "WHERE i.proprietaire.id = :proprietaireId " +
                    "AND p.statutPaiement = 'EN_ATTENTE'";

            return entityManager.createQuery(jpql, Double.class)
                    .setParameter("proprietaireId", proprietaire.getId())
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul des paiements en attente: " + e.getMessage(), e);
        }
    }

    // Les méthodes existantes restent inchangées ci-dessous...
    public Paiement creerPaiement(LocalDate datePaiement, double montant, String moisCouvert,
                                  String methodePaiement, Locataire locataire, Contrat contrat) {
        try {
            Paiement paiement = new Paiement(datePaiement, montant, moisCouvert, methodePaiement);
            paiement.setLocataire(locataire);
            paiement.setContrat(contrat);

            paiementDAO.create(paiement);
            return paiement;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du paiement: " + e.getMessage(), e);
        }
    }

    public Paiement trouverPaiementParId(Long id) {
        try {
            return paiementDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du paiement: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerTousLesPaiements() {
        try {
            return paiementDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerPaiementsParLocataire(Locataire locataire) {
        try {
            return paiementDAO.findByLocataire(locataire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements par locataire: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerPaiementsParContrat(Contrat contrat) {
        try {
            return paiementDAO.findByContrat(contrat);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements par contrat: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerPaiementsParStatut(String statut) {
        try {
            return paiementDAO.findByStatut(statut);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements par statut: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerPaiementsParMois(String moisCouvert) {
        try {
            return paiementDAO.findByMoisCouvert(moisCouvert);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements par mois: " + e.getMessage(), e);
        }
    }

    public List<Paiement> listerPaiementsEnRetard() {
        try {
            return paiementDAO.findPaiementsEnRetard();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des paiements en retard: " + e.getMessage(), e);
        }
    }

    public double calculerTotalPaiementsMois(String moisCouvert) {
        try {
            return paiementDAO.getTotalPaiementsMois(moisCouvert);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du total des paiements: " + e.getMessage(), e);
        }
    }

    public void validerPaiement(Long id) {
        try {
            Paiement paiement = paiementDAO.findById(id);
            if (paiement != null) {
                paiement.setStatutPaiement("VALIDE");
                paiementDAO.update(paiement);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la validation du paiement: " + e.getMessage(), e);
        }
    }

    public void refuserPaiement(Long id) {
        try {
            Paiement paiement = paiementDAO.findById(id);
            if (paiement != null) {
                paiement.setStatutPaiement("REFUSE");
                paiementDAO.update(paiement);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du refus du paiement: " + e.getMessage(), e);
        }
    }

    public void modifierPaiement(Paiement paiement) {
        try {
            paiementDAO.update(paiement);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du paiement: " + e.getMessage(), e);
        }
    }

    public void supprimerPaiement(Long id) {
        try {
            paiementDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du paiement: " + e.getMessage(), e);
        }
    }

    public double calculerSoldeLocataire(Locataire locataire) {
        try {
            List<Paiement> paiements = paiementDAO.findByLocataire(locataire);
            double totalPaye = paiements.stream()
                    .filter(p -> "VALIDE".equals(p.getStatutPaiement()))
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            return totalPaye;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du calcul du solde: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}