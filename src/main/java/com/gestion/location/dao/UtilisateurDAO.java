package com.gestion.location.dao;

import com.gestion.location.config.JPAUtil;
import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;

public class UtilisateurDAO extends GenericDAO<Utilisateur> {
    public UtilisateurDAO() { super(Utilisateur.class); }

    public Utilisateur findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        } finally { em.close(); }
    }
}
