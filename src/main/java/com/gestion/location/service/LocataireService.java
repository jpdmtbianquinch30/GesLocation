package com.gestion.location.service;

import com.gestion.location.dao.LocataireDAO;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.mindrot.jbcrypt.BCrypt;

public class LocataireService {
    private final EntityManager entityManager;
    private final LocataireDAO locataireDAO;

    public LocataireService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.locataireDAO = new LocataireDAO(entityManager);
    }

    // 🔹 Création complète d'un locataire
    public Locataire creerLocataire(String nom, String prenom, String email, String motDePasse,
                                    String telephone, String profession) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();

            String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

            Locataire locataire = new Locataire();
            locataire.setNom(nom);
            locataire.setPrenom(prenom);
            locataire.setEmail(email);
            locataire.setMotDePasse(motDePasseHash);
            locataire.setRole("LOCATAIRE");
            locataire.setNumeroTelephone(telephone);
            locataire.setProfession(profession);

            locataireDAO.create(locataire);

            tx.commit();
            return locataire;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Erreur lors de la création du locataire : " + e.getMessage(), e);
        }
    }

    // 🔹 Trouver locataire par utilisateurId
    public Locataire trouverLocataireParUtilisateurId(Long utilisateurId) {
        return locataireDAO.findByUtilisateurId(utilisateurId);
    }

    // 🔹 Trouver locataire par Utilisateur (utile pour la session)
    public Locataire trouverLocataireParUtilisateur(Utilisateur utilisateur) {
        if (utilisateur == null) return null;
        return trouverLocataireParUtilisateurId(utilisateur.getId());
    }

    // 🔹 Trouver locataire par email
    public Locataire trouverLocataireParEmail(String email) {
        return locataireDAO.findByEmail(email);
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}
