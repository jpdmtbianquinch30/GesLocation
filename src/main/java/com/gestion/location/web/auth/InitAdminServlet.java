package com.gestion.location.web.auth;

import com.gestion.location.entities.Utilisateur;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

@WebServlet("/init-admin")
public class InitAdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Vérifier s’il existe déjà un admin
            Long count = em.createQuery("SELECT COUNT(u) FROM Utilisateur u WHERE u.role = :role", Long.class)
                    .setParameter("role", "ADMIN")
                    .getSingleResult();

            if (count == 0) {
                Utilisateur admin = new Utilisateur();
                admin.setNom("Admin");
                admin.setPrenom("System");
                admin.setEmail("admin@example.com");
                // ⚡ Ici on utilise setMotDePasse, pas setPassword
                admin.setMotDePasse(BCrypt.hashpw("admin123", BCrypt.gensalt()));
                admin.setRole("ADMIN");

                em.persist(admin);
                em.getTransaction().commit();

                resp.getWriter().write("✅ Admin créé avec succès (email: admin@example.com / mdp: admin123)");
            } else {
                resp.getWriter().write("ℹ️ Un admin existe déjà !");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException("Erreur lors de l'initialisation de l'admin", e);
        } finally {
            em.close();
            emf.close();
        }
    }
}
