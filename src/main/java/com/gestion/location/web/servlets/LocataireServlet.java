package com.gestion.location.web.servlets;

import com.gestion.location.entities.Locataire;
import com.gestion.location.service.LocataireService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/locataire")
public class LocataireServlet extends HttpServlet {

    private LocataireService locataireService = new LocataireService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String email = req.getParameter("email");
        String telephone = req.getParameter("telephone");

        Locataire locataire = new Locataire(nom, prenom, email, telephone);
        locataireService.ajouterLocataire(locataire);

        // Redirection vers la page de liste des locataires
        resp.sendRedirect("listeLocataires.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Optionnel : lister les locataires
        req.setAttribute("locataires", locataireService.listerLocataires());
        req.getRequestDispatcher("/webapp/listeLocataires.jsp").forward(req, resp);
    }
}
