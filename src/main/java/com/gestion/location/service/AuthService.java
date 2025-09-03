package com.gestion.location.service;

import com.gestion.location.dao.UtilisateurDAO;
import com.gestion.location.entities.Utilisateur;

public class AuthService {
    private final UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public Utilisateur login(String email, String motDePasse) {
        Utilisateur user = utilisateurDAO.findByEmail(email);
        if(user != null && user.getMotDePasse().equals(motDePasse)) {
            return user;
        }
        return null;
    }
}
