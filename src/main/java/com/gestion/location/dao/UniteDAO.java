package com.gestion.location.dao;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.entities.Unite;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UniteDAO extends GenericDAO<Unite> {

    public UniteDAO() {
        super(Unite.class);
    }

    // Exemple de méthode personnalisée : lister par immeuble
    public List<Unite> findByImmeubleId(Integer immeubleId) {
        EntityManager em = JPAUtil.getEntityManager();
        List<Unite> unites = em.createQuery(
                        "SELECT u FROM Unite u WHERE u.immeuble.id = :id", Unite.class)
                .setParameter("id", immeubleId)
                .getResultList();
        em.close();
        return unites;
    }
}
