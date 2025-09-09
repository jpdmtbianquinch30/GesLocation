package com.gestion.location.web.servlets;

import com.gestion.location.entities.Unite;
import com.gestion.location.entities.Paiement;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.UniteService;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/dashboard")
public class ProprietaireDashboardServlet extends HttpServlet {

    private final UniteService uniteService = new UniteService();
    private final PaiementService paiementService = new PaiementService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Récupérer les unités et paiements pour ce propriétaire
        List<Unite> unites = uniteService.listerParProprietaire(user.getId());
        List<Paiement> paiements = paiementService.listerParProprietaire(user.getId());

        // Ajouter les listes dans la requête pour JSP
        request.setAttribute("unites", unites);
        request.setAttribute("paiements", paiements);

        // Ajouter des totaux
        request.setAttribute("totalUnites", unites.size());
        request.setAttribute("totalPaiements", paiements.size());
        request.setAttribute("totalRevenus", paiements.stream().mapToDouble(Paiement::getMontant).sum());

        // Forward vers JSP dashboard
        request.getRequestDispatcher("/proprietaire/dashboard.jsp").forward(request, response);
    }
}
