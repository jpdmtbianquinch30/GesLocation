package com.gestion.location.service;

import com.gestion.location.dao.UtilisateurDAO;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class UtilisateurService {
    private final EntityManager entityManager;
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.utilisateurDAO = new UtilisateurDAO(entityManager);
    }

    public Utilisateur creerUtilisateur(String nom, String prenom, String email, String motDePasse, String role) {
        try {
            if (utilisateurDAO.emailExists(email)) {
                throw new RuntimeException("L'email existe déjà");
            }

            String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
            Utilisateur utilisateur = new Utilisateur(nom, prenom, email, motDePasseHash, role);

            utilisateurDAO.create(utilisateur);
            return utilisateur;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'utilisateur: " + e.getMessage(), e);
        }
    }

    public Utilisateur authentifier(String email, String motDePasse) {
        try {
            Utilisateur utilisateur = utilisateurDAO.findByEmail(email);
            if (utilisateur != null && BCrypt.checkpw(motDePasse, utilisateur.getMotDePasse())) {
                return utilisateur;
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'authentification: " + e.getMessage(), e);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        try {
            return utilisateurDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur: " + e.getMessage(), e);
        }
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return utilisateurDAO.findByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'utilisateur: " + e.getMessage(), e);
        }
    }

    public List<Utilisateur> listerTousLesUtilisateurs() {
        try {
            return utilisateurDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des utilisateurs: " + e.getMessage(), e);
        }
    }

    public List<Utilisateur> listerUtilisateursParRole(String role) {
        try {
            return utilisateurDAO.findByRole(role);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des utilisateurs par rôle: " + e.getMessage(), e);
        }
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        try {
            utilisateurDAO.update(utilisateur);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de l'utilisateur: " + e.getMessage(), e);
        }
    }

    public void supprimerUtilisateur(Long id) {
        try {
            utilisateurDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'utilisateur: " + e.getMessage(), e);
        }
    }

    public boolean emailExiste(String email) {
        try {
            return utilisateurDAO.emailExists(email);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'email: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}