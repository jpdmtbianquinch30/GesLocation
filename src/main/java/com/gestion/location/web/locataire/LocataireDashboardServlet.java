package com.gestion.location.web.locataire;

import com.gestion.location.entities.Locataire;
import com.gestion.location.service.LocataireService;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/locataire/dashboard")
public class LocataireDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || !"LOCATAIRE".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Long sessionUserId = (Long) session.getAttribute("userId");
        if (sessionUserId == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        LocataireService locataireService = new LocataireService();
        ContratService contratService = new ContratService();
        PaiementService paiementService = new PaiementService();

        try {
            // 🔹 Récupération correcte via utilisateur_id
            Locataire locataire = locataireService.trouverLocataireParUtilisateurId(sessionUserId);

            if (locataire == null) {
                request.setAttribute("errorMessage", "Locataire introuvable. Veuillez contacter l'administrateur.");
                request.getRequestDispatcher("/WEB-INF/views/locataire/dashboard.jsp")
                        .forward(request, response);
                return;
            }

            // Listes sécurisées
            List<?> contrats = contratService.listerContratsParLocataire(locataire);
            List<?> paiements = paiementService.listerPaiementsParLocataire(locataire);

            contrats = (contrats != null) ? contrats : List.of();
            paiements = (paiements != null) ? paiements : List.of();

            // Statistiques
            long contratsActifs = contrats.stream()
                    .filter(c -> "ACTIF".equals(((com.gestion.location.entities.Contrat)c).getEtatContrat()))
                    .count();
            long paiementsEnAttente = paiements.stream()
                    .filter(p -> "EN_ATTENTE".equals(((com.gestion.location.entities.Paiement)p).getStatutPaiement()))
                    .count();
            double totalPaye = paiements.stream()
                    .filter(p -> "VALIDE".equals(((com.gestion.location.entities.Paiement)p).getStatutPaiement()))
                    .mapToDouble(p -> ((com.gestion.location.entities.Paiement)p).getMontant())
                    .sum();

            request.setAttribute("contrats", contrats);
            request.setAttribute("contratsActifs", contratsActifs);
            request.setAttribute("paiementsEnAttente", paiementsEnAttente);
            request.setAttribute("totalPaye", totalPaye);

            // Prochain paiement
            Object prochainPaiement = paiements.stream()
                    .filter(p -> "EN_ATTENTE".equals(((com.gestion.location.entities.Paiement)p).getStatutPaiement()))
                    .findFirst()
                    .orElse(null);
            request.setAttribute("prochainPaiement", prochainPaiement);

            request.getRequestDispatcher("/WEB-INF/views/locataire/dashboard.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement du dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/locataire/dashboard.jsp")
                    .forward(request, response);
        } finally {
            locataireService.close();
            contratService.close();
            paiementService.close();
        }
    }
}
