package com.gestion.location.service;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.dao.ContratDAO;
import com.gestion.location.entities.Contrat;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ContratService {

    private final EntityManager em;
    private final ContratDAO contratDAO;

    public ContratService() {
        this.em = JPAUtil.getEntityManager();
        this.contratDAO = new ContratDAO(em);
    }

    public void ajouterContrat(Contrat contrat) {
        if (contrat.getId() == null) {
            contratDAO.create(contrat);
        } else {
            contratDAO.update(contrat);
        }
    }

    public List<Contrat> listerContrats() {
        return contratDAO.findAll();
    }

    public void supprimerContrat(Long id) {
        contratDAO.delete(id);
    }

    public Contrat trouverParId(Long id) {
        return contratDAO.findById(id);
    }
}
