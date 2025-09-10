package com.gestion.location.web.admin;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/utilisateurs")
public class GestionUtilisateurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UtilisateurService userService = new UtilisateurService();

        try {
            if ("edit".equals(action)) {
                // Afficher le formulaire d'édition
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Utilisateur user = userService.trouverUtilisateurParId(id);
                    request.setAttribute("userToEdit", user);
                }
            } else if ("delete".equals(action)) {
                // Supprimer un utilisateur
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    userService.supprimerUtilisateur(id);
                    request.setAttribute("successMessage", "Utilisateur supprimé avec succès");
                }
            }

            // Lister tous les utilisateurs
            List<Utilisateur> users = userService.listerTousLesUtilisateurs();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/views/admin/gestion_utilisateurs.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la gestion des utilisateurs: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/gestion_utilisateurs.jsp").forward(request, response);
        } finally {
            userService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        UtilisateurService userService = new UtilisateurService();

        try {
            if ("create".equals(action)) {
                // Créer un nouvel utilisateur
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");
                String password = request.getParameter("password");
                String role = request.getParameter("role");

                userService.creerUtilisateur(nom, prenom, email, password, role);
                request.setAttribute("successMessage", "Utilisateur créé avec succès");

            } else if ("update".equals(action)) {
                // Mettre à jour un utilisateur
                String idStr = request.getParameter("id");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String email = request.getParameter("email");
                String role = request.getParameter("role");

                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Utilisateur user = userService.trouverUtilisateurParId(id);
                    if (user != null) {
                        user.setNom(nom);
                        user.setPrenom(prenom);
                        user.setEmail(email);
                        user.setRole(role);
                        userService.modifierUtilisateur(user);
                        request.setAttribute("successMessage", "Utilisateur modifié avec succès");
                    }
                }
            }

            // Recharger la liste des utilisateurs
            List<Utilisateur> users = userService.listerTousLesUtilisateurs();
            request.setAttribute("users", users);

            request.getRequestDispatcher("/WEB-INF/views/admin/gestion_utilisateurs.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'opération: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/gestion_utilisateurs.jsp").forward(request, response);
        } finally {
            userService.close();
        }
    }
}