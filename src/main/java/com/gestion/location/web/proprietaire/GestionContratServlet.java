package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/contrats")
public class GestionContratServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        if (proprietaire == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String statut = request.getParameter("statut");

        ContratService contratService = new ContratService();
        ImmeubleService immeubleService = new ImmeubleService();

        try {
            // Voir les détails d’un contrat
            if ("view".equals(action)) {
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Contrat contrat = contratService.trouverContratParId(id);
                    if (contrat != null && contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        request.setAttribute("contratDetails", contrat);
                    }
                }
            }

            // Liste des contrats du propriétaire
            List<Contrat> contrats = contratService.listerContratsParProprietaire(proprietaire);

            // Filtrage par statut
            if (statut != null && !statut.isEmpty()) {
                contrats = contrats.stream()
                        .filter(c -> statut.equals(c.getEtatContrat()))
                        .toList();
            }

            // Liste des immeubles pour les formulaires
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);

            request.setAttribute("contrats", contrats);
            request.setAttribute("immeubles", immeubles);
            request.setAttribute("selectedStatut", statut);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_contrats.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la gestion des contrats : " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_contrats.jsp")
                    .forward(request, response);
        } finally {
            contratService.close();
            immeubleService.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        if (proprietaire == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        ContratService contratService = new ContratService();

        try {
            if ("changerStatut".equals(action)) {
                String idStr = request.getParameter("id");
                String nouveauStatut = request.getParameter("nouveauStatut");

                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Contrat contrat = contratService.trouverContratParId(id);

                    if (contrat != null && contrat.getUnite().getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        contrat.setEtatContrat(nouveauStatut);
                        contratService.modifierContrat(contrat);
                        request.setAttribute("successMessage", "Statut du contrat modifié avec succès.");
                    }
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du changement de statut : " + e.getMessage());
        } finally {
            contratService.close();
        }

        // Recharger la page
        doGet(request, response);
    }
}
