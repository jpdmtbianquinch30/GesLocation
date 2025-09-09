package com.gestion.location.service;

import com.gestion.location.dao.PaiementDAO;
import com.gestion.location.entities.Paiement;
import com.gestion.location.config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class PaiementService {
    private final EntityManager em = JPAUtil.getEntityManager();
    private final PaiementDAO dao = new PaiementDAO(em);

    public void ajouter(Paiement p) { dao.ajouter(p); }
    public void modifier(Paiement p) { dao.modifier(p); }
    public void supprimer(Paiement p) { dao.supprimer(p); }
    public Paiement trouverParId(Long id) { return dao.trouverParId(id); }
    public List<Paiement> lister() { return dao.lister(); }

    public List<Paiement> listerParLocataire(Long locataireId) {
        return dao.listerParLocataire(locataireId);
    }

    public List<Paiement> listerParUnite(Long uniteId) {
        return dao.listerParUnite(uniteId);
    }

    // ✅ ajout pour ProprietaireDashboardServlet
    public List<Paiement> listerParProprietaire(Long proprietaireId) {
        return dao.listerParProprietaire(proprietaireId);
    }
}
