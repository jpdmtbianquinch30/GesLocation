package com.gestion.location.service;

import com.gestion.location.dao.ContratDAO;
import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
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

    public Contrat creerContrat(LocalDate dateDebut, LocalDate dateFin, double loyerMensuel,
                                double caution, String etatContrat, Locataire locataire, Unite unite) {
        try {
            // Vérifier si l'unité est disponible
            if (!unite.isEstDisponible()) {
                throw new RuntimeException("L'unité n'est pas disponible");
            }

            Contrat contrat = new Contrat(dateDebut, dateFin, loyerMensuel, caution, etatContrat);
            contrat.setLocataire(locataire);
            contrat.setUnite(unite);

            // Marquer l'unité comme non disponible
            unite.setEstDisponible(false);

            contratDAO.create(contrat);
            return contrat;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du contrat: " + e.getMessage(), e);
        }
    }

    public Contrat trouverContratParId(Long id) {
        try {
            return contratDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du contrat: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerTousLesContrats() {
        try {
            return contratDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerContratsParLocataire(Locataire locataire) {
        try {
            return contratDAO.findByLocataire(locataire);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats par locataire: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerContratsParUnite(Unite unite) {
        try {
            return contratDAO.findByUnite(unite);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats par unité: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerContratsParEtat(String etat) {
        try {
            return contratDAO.findByEtat(etat);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats par état: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerContratsActifs() {
        try {
            return contratDAO.findContratsActifs();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats actifs: " + e.getMessage(), e);
        }
    }

    public List<Contrat> listerContratsExpirantDans(int jours) {
        try {
            return contratDAO.findContratsExpirantDans(jours);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des contrats expirant bientôt: " + e.getMessage(), e);
        }
    }

    public void modifierContrat(Contrat contrat) {
        try {
            contratDAO.update(contrat);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du contrat: " + e.getMessage(), e);
        }
    }

    public void resilierContrat(Long id) {
        try {
            Contrat contrat = contratDAO.findById(id);
            if (contrat != null) {
                contrat.setEtatContrat("RESILIE");

                // Libérer l'unité
                if (contrat.getUnite() != null) {
                    contrat.getUnite().setEstDisponible(true);
                }

                contratDAO.update(contrat);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la résiliation du contrat: " + e.getMessage(), e);
        }
    }

    public void supprimerContrat(Long id) {
        try {
            contratDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression du contrat: " + e.getMessage(), e);
        }
    }

    public boolean verifierDisponibiliteUnite(Unite unite, LocalDate dateDebut, LocalDate dateFin) {
        try {
            List<Contrat> contrats = contratDAO.findByUnite(unite);
            for (Contrat contrat : contrats) {
                if (contrat.getEtatContrat().equals("ACTIF") &&
                        !(dateFin.isBefore(contrat.getDateDebut()) || dateDebut.isAfter(contrat.getDateFin()))) {
                    return false; // Conflit de dates
                }
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de disponibilité: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}