package com.gestion.location.web.admin;

import com.gestion.location.service.UtilisateurService;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtilisateurService userService = new UtilisateurService();
        ImmeubleService immeubleService = new ImmeubleService();
        ContratService contratService = new ContratService();
        PaiementService paiementService = new PaiementService();

        try {
            // Récupérer les statistiques pour le dashboard
            long totalUsers = userService.listerTousLesUtilisateurs().size();
            long totalProprietaires = userService.listerUtilisateursParRole("PROPRIETAIRE").size();
            long totalLocataires = userService.listerUtilisateursParRole("LOCATAIRE").size();
            long totalImmeubles = immeubleService.listerTousLesImmeubles().size();
            long totalContratsActifs = contratService.listerContratsActifs().size();
            long totalPaiementsEnAttente = paiementService.listerPaiementsParStatut("EN_ATTENTE").size();

            // Passer les données à la JSP
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("totalProprietaires", totalProprietaires);
            request.setAttribute("totalLocataires", totalLocataires);
            request.setAttribute("totalImmeubles", totalImmeubles);
            request.setAttribute("totalContratsActifs", totalContratsActifs);
            request.setAttribute("totalPaiementsEnAttente", totalPaiementsEnAttente);

            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement du dashboard: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
        } finally {
            userService.close();
            immeubleService.close();
            contratService.close();
            paiementService.close();
        }
    }
}