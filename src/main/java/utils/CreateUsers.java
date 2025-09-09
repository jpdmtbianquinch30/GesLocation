package utils;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreateUsers {

    public static void main(String[] args) {
        // Configuration de la base
        String url = "jdbc:postgresql://localhost:5432/gestion_db";
        String dbUser = "gestion_user";
        String dbPassword = "Thiara3003.";

        // Données des utilisateurs à créer
        Object[][] users = {
                {"user@gmail.com", "Jean", "Mouha", "user123", "UTILISATEUR"},
                {"proprio@gmail.com", "Sophie", "Martin", "proprio123", "PROPRIETAIRE"}
        };

        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {

            String sql = "INSERT INTO utilisateur (email, motdepasse, nom, prenom, role) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

                for (Object[] u : users) {
                    String email = (String) u[0];
                    String nom = (String) u[1];
                    String prenom = (String) u[2];
                    String passwordPlain = (String) u[3];
                    String role = (String) u[4];

                    // Hash du mot de passe
                    String hashedPassword = BCrypt.hashpw(passwordPlain, BCrypt.gensalt());

                    stmt.setString(1, email);
                    stmt.setString(2, hashedPassword);
                    stmt.setString(3, nom);
                    stmt.setString(4, prenom);
                    stmt.setString(5, role);

                    stmt.addBatch();
                }

                stmt.executeBatch();
                System.out.println("Utilisateur et propriétaire créés avec succès !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
