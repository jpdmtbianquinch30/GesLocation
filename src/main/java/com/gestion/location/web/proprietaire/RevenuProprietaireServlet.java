package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/proprietaire/revenus")
public class RevenuProprietaireServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        if (proprietaire == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        PaiementService paiementService = new PaiementService();

        try {
            // Calculer les statistiques de revenus
            double revenuMensuel = paiementService.calculerRevenuMensuel(proprietaire);
            double revenuAnnuel = paiementService.calculerRevenuAnnuel(proprietaire);
            double revenuTotal = paiementService.calculerRevenuTotal(proprietaire);

            // Récupérer l'évolution des revenus
            Map<String, Double> evolutionRevenus = paiementService.getEvolutionRevenusMensuels(proprietaire);

            // Récupérer les paiements en attente
            double totalEnAttente = paiementService.calculerTotalPaiementsEnAttente(proprietaire);

            // Définir les attributs pour la JSP
            request.setAttribute("revenuMensuel", revenuMensuel);
            request.setAttribute("revenuAnnuel", revenuAnnuel);
            request.setAttribute("revenuTotal", revenuTotal);
            request.setAttribute("evolutionRevenus", evolutionRevenus);
            request.setAttribute("totalEnAttente", totalEnAttente);
            request.setAttribute("currentPage", "revenus");

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/revenus.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors du calcul des revenus: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/revenus.jsp").forward(request, response);
        } finally {
            paiementService.close();
        }
    }
}