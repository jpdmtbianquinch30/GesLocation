package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/proprietaire/dashboard")
public class ProprietaireDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object userObj = session.getAttribute("user");

        // Vérification du type
        if (!(userObj instanceof Proprietaire)) {
            response.sendRedirect(request.getContextPath() + "/login?error=not_proprietaire");
            return;
        }

        Proprietaire proprietaire = (Proprietaire) userObj;

        ImmeubleService immeubleService = new ImmeubleService();
        ContratService contratService = new ContratService();
        PaiementService paiementService = new PaiementService();

        try {
            // Récupérer les statistiques pour le dashboard du propriétaire
            long totalImmeubles = immeubleService.listerImmeublesParProprietaire(proprietaire).size();
            long totalContratsActifs = contratService.listerContratsActifs().stream()
                    .filter(contrat -> contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId()))
                    .count();
            long totalPaiementsEnAttente = paiementService.listerPaiementsParStatut("EN_ATTENTE").stream()
                    .filter(paiement -> paiement.getContrat().getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId()))
                    .count();

            double revenusMensuels = paiementService.listerPaiementsParStatut("VALIDE").stream()
                    .filter(paiement -> paiement.getContrat().getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId()))
                    .mapToDouble(paiement -> {
                        // Vérifier si le paiement est pour le mois en cours
                        String moisCourant = java.time.YearMonth.now().toString();
                        if (moisCourant.equals(paiement.getMoisCouvert())) {
                            return paiement.getMontant();
                        }
                        return 0;
                    })
                    .sum();

            // Passer les données à la JSP
            request.setAttribute("totalImmeubles", totalImmeubles);
            request.setAttribute("totalContratsActifs", totalContratsActifs);
            request.setAttribute("totalPaiementsEnAttente", totalPaiementsEnAttente);
            request.setAttribute("revenusMensuels", revenusMensuels);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement du dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/dashboard.jsp").forward(request, response);
        } finally {
            immeubleService.close();
            contratService.close();
            paiementService.close();
        }
    }
}