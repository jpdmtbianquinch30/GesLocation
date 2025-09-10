package com.gestion.location.web.locataire;

import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Paiement;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/locataire/paiements")
public class MesPaiementsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Locataire locataire = (Locataire) session.getAttribute("user");

        String statut = request.getParameter("statut");

        PaiementService paiementService = new PaiementService();

        try {
            List<Paiement> paiements;

            if (statut != null && !statut.isEmpty()) {
                paiements = paiementService.listerPaiementsParStatut(statut).stream()
                        .filter(p -> p.getLocataire().getId().equals(locataire.getId()))
                        .toList();
            } else {
                paiements = paiementService.listerPaiementsParLocataire(locataire);
            }

            // Calculer le total payé
            double totalPaye = paiements.stream()
                    .filter(p -> "VALIDE".equals(p.getStatutPaiement()))
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            // Prochain paiement à effectuer
            var prochainPaiement = paiements.stream()
                    .filter(p -> "EN_ATTENTE".equals(p.getStatutPaiement()))
                    .findFirst()
                    .orElse(null);

            request.setAttribute("paiements", paiements);
            request.setAttribute("totalPaye", totalPaye);
            request.setAttribute("prochainPaiement", prochainPaiement);
            request.setAttribute("selectedStatut", statut);

            request.getRequestDispatcher("/WEB-INF/views/locataire/mes_paiements.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement des paiements: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/locataire/mes_paiements.jsp").forward(request, response);
        } finally {
            paiementService.close();
        }
    }
}