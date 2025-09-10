package com.gestion.location.web.admin;

import com.gestion.location.service.ContratService;
import com.gestion.location.service.PaiementService;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/rapports")
public class RapportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String periode = request.getParameter("periode");
        if (periode == null) {
            // Par défaut, le mois en cours
            periode = YearMonth.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }

        ContratService contratService = new ContratService();
        PaiementService paiementService = new PaiementService();
        ImmeubleService immeubleService = new ImmeubleService();
        UtilisateurService userService = new UtilisateurService();

        try {
            // Générer les rapports
            Map<String, Object> rapports = new HashMap<>();

            // Statistiques générales
            rapports.put("totalImmeubles", immeubleService.listerTousLesImmeubles().size());
            rapports.put("totalProprietaires", userService.listerUtilisateursParRole("PROPRIETAIRE").size());
            rapports.put("totalLocataires", userService.listerUtilisateursParRole("LOCATAIRE").size());
            rapports.put("contratsActifs", contratService.listerContratsActifs().size());
            rapports.put("contratsExpirant", contratService.listerContratsExpirantDans(30).size());

            // Statistiques financières
            rapports.put("paiementsValides", paiementService.listerPaiementsParStatut("VALIDE").size());
            rapports.put("paiementsEnAttente", paiementService.listerPaiementsParStatut("EN_ATTENTE").size());
            rapports.put("paiementsEnRetard", paiementService.listerPaiementsEnRetard().size());
            rapports.put("revenusMensuels", paiementService.calculerTotalPaiementsMois(periode));

            // Passer les données à la JSP
            request.setAttribute("rapports", rapports);
            request.setAttribute("periode", periode);
            request.setAttribute("periodes", generatePeriodes());

            request.getRequestDispatcher("/WEB-INF/views/admin/rapports.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la génération des rapports: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/admin/rapports.jsp").forward(request, response);
        } finally {
            contratService.close();
            paiementService.close();
            immeubleService.close();
            userService.close();
        }
    }

    private Map<String, String> generatePeriodes() {
        Map<String, String> periodes = new HashMap<>();
        LocalDate now = LocalDate.now();

        for (int i = 0; i < 12; i++) {
            YearMonth ym = YearMonth.from(now.minusMonths(i));
            String value = ym.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            String label = ym.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
            periodes.put(value, label);
        }

        return periodes;
    }
}