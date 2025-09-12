package com.gestion.location.utils;

import org.mindrot.jbcrypt.BCrypt;

public class HashPassword {
    public static void main(String[] args) {
        // Mot de passe en clair que tu veux hacher
        String rawPassword = "motdepasse"; // par ex. pour l'utilisateur id 19
        // Génère le hash BCrypt
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());

        // Affiche le mot de passe haché
        System.out.println("Mot de passe haché : " + hashedPassword);
    }
}
