package com.gestion.location.web.servlets;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/utilisateurs")
public class UtilisateurServlet extends HttpServlet {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        // Suppression
        if ("supprimer".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Utilisateur u = utilisateurService.getById(id); // ✅ récupérer l'objet
            if (u != null) {
                utilisateurService.supprimer(u);
            }
            resp.sendRedirect(req.getContextPath() + "/utilisateurs");
            return;
        }

        // Récupérer un utilisateur pour modification
        if ("modifier".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            Utilisateur u = utilisateurService.getById(id); // ✅ utiliser getById
            req.setAttribute("utilisateur", u);
            req.getRequestDispatcher("/utilisateur/modifier.jsp").forward(req, resp);
            return;
        }

        // Liste des utilisateurs
        List<Utilisateur> utilisateurs = utilisateurService.lister();
        req.setAttribute("utilisateurs", utilisateurs);
        req.setAttribute("totalUtilisateurs", utilisateurs.size());
        req.getRequestDispatcher("/utilisateur/liste.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        String nom = req.getParameter("nom");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        Utilisateur u;
        if (idParam != null && !idParam.isEmpty()) {
            Long id = Long.parseLong(idParam);
            u = utilisateurService.getById(id); // ✅ récupérer l'objet existant
            u.setNom(nom);
            u.setEmail(email);
            u.setRole(role);
            utilisateurService.modifier(u);
        } else {
            u = new Utilisateur();
            u.setNom(nom);
            u.setEmail(email);
            u.setRole(role);
            utilisateurService.ajouter(u);
        }

        resp.sendRedirect(req.getContextPath() + "/utilisateurs");
    }
}
