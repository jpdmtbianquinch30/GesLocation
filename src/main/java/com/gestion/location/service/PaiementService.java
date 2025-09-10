package com.gestion.location.service;

import com.gestion.location.dao.PaiementDAO;
import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Paiement;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

public class PaiementService {
    private final EntityManager entityManager;
    private final PaiementDAO paiementDAO;

    public PaiementService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.paiementDAO = new PaiementDAO(entityManager);
    }

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

            // Ici, vous pourriez ajouter la logique pour calculer le total dû
            // en fonction des contrats actifs du locataire
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