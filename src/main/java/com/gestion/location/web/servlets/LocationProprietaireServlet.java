package com.gestion.location.web.servlets;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.ContratService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/locations")
public class LocationProprietaireServlet extends HttpServlet {

    private final ContratService contratService = new ContratService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Utilisateur proprietaire = (Utilisateur) session.getAttribute("utilisateur");

        String action = req.getParameter("action");
        if ("supprimer".equals(action)) {
            Long id = Long.parseLong(req.getParameter("id"));
            contratService.supprimer(id);
            resp.sendRedirect(req.getContextPath() + "/proprietaire/locations");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isEmpty()) { // Modification
            Contrat c = contratService.trouverParId(Long.parseLong(idParam));
            req.setAttribute("contrat", c);
            req.getRequestDispatcher("/proprietaire/location_form.jsp").forward(req, resp);
            return;
        }

        // Liste des contrats du propriétaire
        List<Contrat> contrats = contratService.listerParProprietaire(proprietaire.getId());
        req.setAttribute("contrats", contrats);
        req.getRequestDispatcher("/proprietaire/location_liste.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = req.getParameter("id") != null && !req.getParameter("id").isEmpty()
                ? Long.parseLong(req.getParameter("id")) : null;
        String statut = req.getParameter("statut"); // par exemple "En cours", "Terminé"

        Contrat c;
        if (id == null) {
            c = new Contrat();
        } else {
            c = contratService.trouverParId(id);
        }

        c.setEtat(statut);

        if (id == null) {
            contratService.ajouter(c);
        } else {
            contratService.modifier(c);
        }

        resp.sendRedirect(req.getContextPath() + "/proprietaire/locations");
    }
}
