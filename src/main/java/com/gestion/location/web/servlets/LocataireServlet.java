package com.gestion.location.web.servlets;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/locataires")
public class LocataireServlet extends HttpServlet {

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        // Ajouter un locataire
        if ("add".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/locataire/form.jsp").forward(request, response);
            return;
        }

        // Éditer un locataire
        if ("edit".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Utilisateur locataire = utilisateurService.trouverParId(id); // récupère l'entité
                if (locataire != null && "LOCATAIRE".equalsIgnoreCase(locataire.getRole())) {
                    request.setAttribute("locataire", locataire);
                    request.getRequestDispatcher("/locataire/form.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException ignored) {}
        }

        // Supprimer un locataire
        if ("delete".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Utilisateur locataire = utilisateurService.trouverParId(id); // récupère l'entité
                if (locataire != null && "LOCATAIRE".equalsIgnoreCase(locataire.getRole())) {
                    utilisateurService.supprimer(locataire); // supprime l'objet
                }
            } catch (NumberFormatException ignored) {}
            response.sendRedirect(request.getContextPath() + "/locataires");
            return;
        }

        // Lister tous les locataires
        List<Utilisateur> locataires = utilisateurService.listerParRole("LOCATAIRE");
        request.setAttribute("locataires", locataires);
        request.getRequestDispatcher("/locataire/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");

        try {
            Utilisateur locataire;
            if (idParam != null && !idParam.isEmpty()) {
                Long id = Long.parseLong(idParam);
                locataire = utilisateurService.trouverParId(id); // récupère l'entité existante
            } else {
                locataire = new Utilisateur(); // nouvelle entité
            }

            locataire.setNom(nom);
            locataire.setPrenom(prenom);
            locataire.setEmail(email);
            locataire.setTelephone(telephone); // assure-toi que le getter/setter existe
            locataire.setRole("LOCATAIRE"); // rôle fixé automatiquement

            if (locataire.getId() == null) {
                utilisateurService.ajouter(locataire);
            } else {
                utilisateurService.modifier(locataire);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/locataires");
    }
}
