package com.gestion.location.web.locataire;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.entities.Locataire;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/locataire/dashboard")
public class LocataireDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");

        // VÉRIFICATION DU TYPE AVANT CASTING
        if (!(userObj instanceof Locataire)) {
            response.sendRedirect(request.getContextPath() + "/login?error=not_locataire");
            return;
        }

        Locataire locataire = (Locataire) userObj;

        ContratService contratService = new ContratService();
        PaiementService paiementService = new PaiementService();

        try {
            // Récupérer les données du locataire
            var contrats = contratService.listerContratsParLocataire(locataire);
            var paiements = paiementService.listerPaiementsParLocataire(locataire);

            // Calculer les statistiques
            long contratsActifs = contrats.stream()
                    .filter(contrat -> "ACTIF".equals(contrat.getEtatContrat()))
                    .count();

            long paiementsEnAttente = paiements.stream()
                    .filter(paiement -> "EN_ATTENTE".equals(paiement.getStatutPaiement()))
                    .count();

            double totalPaye = paiements.stream()
                    .filter(paiement -> "VALIDE".equals(paiement.getStatutPaiement()))
                    .mapToDouble(paiement -> paiement.getMontant())
                    .sum();

            // Passer les données à la JSP
            request.setAttribute("contrats", contrats);
            request.setAttribute("contratsActifs", contratsActifs);
            request.setAttribute("paiementsEnAttente", paiementsEnAttente);
            request.setAttribute("totalPaye", totalPaye);

            request.getRequestDispatcher("/WEB-INF/views/locataire/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement du dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/locataire/dashboard.jsp").forward(request, response);
        } finally {
            contratService.close();
            paiementService.close();
        }
    }
}