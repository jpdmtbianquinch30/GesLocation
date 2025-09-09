package com.gestion.location.service;

import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    // Nom exact du persistence-unit
    private static final String PERSISTENCE_UNIT_NAME = "GestionLocationPU";
    private final EntityManagerFactory emf;

    // Constructeur
    public AuthService() {
        emf = Persistence.createEntityManagerFactory("GestionLocationPU");
    }

    public Utilisateur authenticate(String email, String motDePasse) {
        EntityManager em = emf.createEntityManager();
        try {
            // Récupère l'utilisateur par email uniquement
            TypedQuery<Utilisateur> query = em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class);
            query.setParameter("email", email);
            Utilisateur user = query.getResultStream().findFirst().orElse(null);

            // Vérifie le mot de passe avec BCrypt
            if (user != null && BCrypt.checkpw(motDePasse, user.getMotDePasse())) {
                return user;
            }
            return null;
        } finally {
            em.close();
        }
    }

    // Fermer l'EntityManagerFactory à la fin de l'application
    public void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
