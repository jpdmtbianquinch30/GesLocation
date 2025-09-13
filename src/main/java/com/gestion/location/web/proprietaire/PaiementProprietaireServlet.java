package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Paiement;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/paiements")
public class PaiementProprietaireServlet extends HttpServlet {
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
            // Récupérer les paiements du propriétaire
            List<Paiement> paiements = paiementService.listerPaiementsParProprietaire(proprietaire);

            // Calculer les statistiques
            double totalPaiements = paiements.stream()
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            double totalValides = paiements.stream()
                    .filter(p -> "VALIDE".equals(p.getStatutPaiement()))
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            double totalEnAttente = paiements.stream()
                    .filter(p -> "EN_ATTENTE".equals(p.getStatutPaiement()))
                    .mapToDouble(Paiement::getMontant)
                    .sum();

            // Définir les attributs pour la JSP
            request.setAttribute("paiements", paiements);
            request.setAttribute("totalPaiements", totalPaiements);
            request.setAttribute("totalValides", totalValides);
            request.setAttribute("totalEnAttente", totalEnAttente);
            request.setAttribute("currentPage", "paiements");

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/paiements.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors du chargement des paiements: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/paiements.jsp").forward(request, response);
        } finally {
            paiementService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String paiementIdStr = request.getParameter("paiementId");

        if (action != null && paiementIdStr != null) {
            PaiementService paiementService = new PaiementService();

            try {
                Long paiementId = Long.parseLong(paiementIdStr);

                if ("valider".equals(action)) {
                    paiementService.validerPaiement(paiementId);
                    request.setAttribute("successMessage", "Paiement validé avec succès");
                } else if ("refuser".equals(action)) {
                    paiementService.refuserPaiement(paiementId);
                    request.setAttribute("successMessage", "Paiement refusé avec succès");
                }

            } catch (Exception e) {
                request.setAttribute("errorMessage", "Erreur lors de la modification du paiement: " + e.getMessage());
            } finally {
                paiementService.close();
            }
        }

        // Recharger la page
        doGet(request, response);
    }
}