package com.gestion.location.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitaire pour gérer la connexion JPA avec Hibernate.
 * Fournit un EntityManager prêt à l'emploi.
 */
public class JPAUtil {

    // Factory unique pour toute l'application
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("GestionLocationPU");

    /**
     * Récupère un EntityManager.
     * @return EntityManager pour interagir avec la base
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * Ferme la factory à la fermeture de l'application
     */
    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }

    /**
     * Test rapide de connexion
     */
    public static void main(String[] args) {
        EntityManager em = getEntityManager();
        if (em.isOpen()) {
            System.out.println("Connexion JPA réussie !");
        } else {
            System.out.println("Erreur de connexion JPA.");
        }
        em.close();
        close();
    }
}
