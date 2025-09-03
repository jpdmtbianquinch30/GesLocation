package com.gestion.location.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // Initialise l'EntityManagerFactory une seule fois pour toute l'application
            emf = Persistence.createEntityManagerFactory("GestionLocationPU");
            sce.getServletContext().setAttribute("emf", emf);
            System.out.println(" EntityManagerFactory initialisé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(" Erreur lors de l'initialisation de l'EntityManagerFactory !");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println(" EntityManagerFactory fermé proprement.");
        }
    }
}
