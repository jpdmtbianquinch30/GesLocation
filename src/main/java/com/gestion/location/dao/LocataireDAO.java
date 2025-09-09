package com.gestion.location.dao;

import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LocataireDAO {

    private final EntityManager em;

    public LocataireDAO(EntityManager em) {
        this.em = em;
    }

    public List<Utilisateur> findLocatairesByProprietaireId(Long proprietaireId) {
        return em.createQuery(
                        "SELECT DISTINCT c.locataire FROM Contrat c WHERE c.unite.immeuble.proprietaire.id = :pid", Utilisateur.class)
                .setParameter("pid", proprietaireId)
                .getResultList();
    }
}
