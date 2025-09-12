package com.gestion.location.utils;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CreateUsers {

    public static void main(String[] args) {

        // Configuration de la base de données
        String url = "jdbc:postgresql://localhost:5432/gestion_db";
        String dbUser = "gestion_user";
        String dbPassword = "Thiara3003.";

        // Liste des utilisateurs à créer : {email, nom, prenom, mot_de_passe, role}
        Object[][] users = {
                {"proprio@gmail.com", "Sophie", "Martin", "proprio123", "PROPRIETAIRE"},
                {"locataire@gmail.com", "Joseph", "Sambou", "locataire123", "LOCATAIRE"}
        };

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {

            String insertSql = "INSERT INTO utilisateur (email, mot_de_passe, nom, prenom, role) VALUES (?, ?, ?, ?, ?)";
            String checkSql = "SELECT COUNT(*) FROM utilisateur WHERE email = ?";

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                 PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

                for (Object[] u : users) {
                    String email = (String) u[0];
                    String nom = (String) u[1];
                    String prenom = (String) u[2];
                    String passwordPlain = (String) u[3];
                    String role = (String) u[4];

                    // Vérifier si l'utilisateur existe déjà
                    checkStmt.setString(1, email);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) > 0) {
                            System.out.println("Utilisateur déjà existant : " + email);
                            continue; // Passer à l'utilisateur suivant
                        }
                    }

                    // Hash du mot de passe
                    String hashedPassword = BCrypt.hashpw(passwordPlain, BCrypt.gensalt(12));

                    insertStmt.setString(1, email);
                    insertStmt.setString(2, hashedPassword);
                    insertStmt.setString(3, nom);
                    insertStmt.setString(4, prenom);
                    insertStmt.setString(5, role);

                    insertStmt.executeUpdate();
                    System.out.println("Utilisateur créé avec succès : " + email + " (" + role + ")");
                }
            }

            System.out.println("\nCréation des utilisateurs terminée !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
