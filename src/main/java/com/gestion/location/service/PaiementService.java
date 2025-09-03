package com.gestion.location.service;

import com.gestion.location.dao.PaiementDAO;
import com.gestion.location.entities.Paiement;
import com.gestion.location.config.JPAUtil; // ton utilitaire JPA
import jakarta.persistence.EntityManager;

import java.util.List;

public class PaiementService {

    private final PaiementDAO paiementDAO;

    public PaiementService() {
        EntityManager em = JPAUtil.getEntityManager(); // récupérer l'EntityManager
        this.paiementDAO = new PaiementDAO(em);
    }

    public void effectuerPaiement(Double montant, String mode) {
        Paiement paiement = new Paiement(montant, mode, "valide");
        paiementDAO.save(paiement);
    }

    public List<Paiement> listerPaiements() {
        return paiementDAO.findAll();
    }
}
