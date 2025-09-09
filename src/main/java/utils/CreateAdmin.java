package utils;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CreateAdmin {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/gestion_db";
        String user = "gestion_user";  // ton utilisateur PostgreSQL
        String password = "Thiara3003."; // ton mot de passe PostgreSQL

        String email = "admin@gmail.com";
        String nom = "Admin";
        String prenom = "Principal";
        String role = "ADMIN";

        // Génère le hash BCrypt pour le mot de passe
        String motDePasse = BCrypt.hashpw("admin123", BCrypt.gensalt());

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO utilisateur (email, motdepasse, nom, prenom, role) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                stmt.setString(2, motDePasse);
                stmt.setString(3, nom);
                stmt.setString(4, prenom);
                stmt.setString(5, role);
                stmt.executeUpdate();
                System.out.println("Admin créé avec succès !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
