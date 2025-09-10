package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.entities.Unite;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/proprietaire/contrats")
public class GestionContratServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");
        String statut = request.getParameter("statut");

        ContratService contratService = new ContratService();
        ImmeubleService immeubleService = new ImmeubleService();

        try {
            if ("view".equals(action)) {
                // Voir les détails d'un contrat
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Contrat contrat = contratService.trouverContratParId(id);
                    // Vérifier que le contrat appartient au propriétaire
                    if (contrat != null && contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        request.setAttribute("contratDetails", contrat);
                    }
                }
            } else if ("resilier".equals(action)) {
                // Résilier un contrat
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Contrat contrat = contratService.trouverContratParId(id);
                    // Vérifier que le contrat appartient au propriétaire
                    if (contrat != null && contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        contratService.resilierContrat(id);
                        request.setAttribute("successMessage", "Contrat résilié avec succès");
                    }
                }
            }

            // Lister les contrats selon le filtre
            List<Contrat> contrats;
            if (statut != null && !statut.isEmpty()) {
                contrats = contratService.listerContratsParEtat(statut).stream()
                        .filter(contrat -> contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId()))
                        .toList();
            } else {
                contrats = contratService.listerTousLesContrats().stream()
                        .filter(contrat -> contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId()))
                        .toList();
            }

            // Pour le formulaire de création
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            request.setAttribute("immeubles", immeubles);

            request.setAttribute("contrats", contrats);
            request.setAttribute("selectedStatut", statut);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_contrats.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la gestion des contrats: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_contrats.jsp").forward(request, response);
        } finally {
            contratService.close();
            immeubleService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");

        if ("changerStatut".equals(action)) {
            ContratService contratService = new ContratService();

            try {
                String idStr = request.getParameter("id");
                String nouveauStatut = request.getParameter("nouveauStatut");

                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Contrat contrat = contratService.trouverContratParId(id);
                    // Vérifier que le contrat appartient au propriétaire
                    if (contrat != null && contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        contrat.setEtatContrat(nouveauStatut);
                        contratService.modifierContrat(contrat);
                        request.setAttribute("successMessage", "Statut du contrat modifié avec succès");
                    }
                }
            } catch (Exception e) {
                request.setAttribute("errorMessage", "Erreur lors du changement de statut: " + e.getMessage());
            } finally {
                contratService.close();
            }
        }

        // Recharger la page
        doGet(request, response);
    }
}