package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.entities.Unite;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/unites")
public class GestionUniteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");
        String immeubleId = request.getParameter("immeubleId");

        ImmeubleService immeubleService = new ImmeubleService();
        UniteService uniteService = new UniteService();

        try {
            // Lister les immeubles du propriétaire pour le select
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            request.setAttribute("immeubles", immeubles);

            if ("edit".equals(action)) {
                // Afficher le formulaire d'édition
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Unite unite = uniteService.trouverUniteParId(id);
                    // Vérifier que l'unité appartient au propriétaire
                    if (unite != null && unite.getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        request.setAttribute("uniteToEdit", unite);
                    }
                }
            } else if ("delete".equals(action)) {
                // Supprimer une unité
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Unite unite = uniteService.trouverUniteParId(id);
                    // Vérifier que l'unité appartient au propriétaire
                    if (unite != null && unite.getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        uniteService.supprimerUnite(id);
                        request.setAttribute("successMessage", "Unité supprimée avec succès");
                    }
                }
            }

            // Lister les unités selon le filtre
            List<Unite> unites;
            if (immeubleId != null && !immeubleId.isEmpty()) {
                Long id = Long.parseLong(immeubleId);
                Immeuble immeuble = immeubleService.trouverImmeubleParId(id);
                // Vérifier que l'immeuble appartient au propriétaire
                if (immeuble != null && immeuble.getProprietaire().getId().equals(proprietaire.getId())) {
                    unites = uniteService.listerUnitesParImmeuble(immeuble);
                    request.setAttribute("selectedImmeubleId", immeubleId);
                } else {
                    unites = List.of();
                }
            } else {
                // Toutes les unités du propriétaire
                unites = immeubles.stream()
                        .flatMap(immeuble -> uniteService.listerUnitesParImmeuble(immeuble).stream())
                        .toList();
            }

            request.setAttribute("unites", unites);
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_unites.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la gestion des unités: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_unites.jsp").forward(request, response);
        } finally {
            immeubleService.close();
            uniteService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");
        ImmeubleService immeubleService = new ImmeubleService();
        UniteService uniteService = new UniteService();

        try {
            if ("create".equals(action)) {
                // Créer une nouvelle unité
                String numeroUnite = request.getParameter("numeroUnite");
                int nombrePieces = Integer.parseInt(request.getParameter("nombrePieces"));
                double superficie = Double.parseDouble(request.getParameter("superficie"));
                double loyerMensuel = Double.parseDouble(request.getParameter("loyerMensuel"));
                String description = request.getParameter("description");
                Long immeubleId = Long.parseLong(request.getParameter("immeubleId"));

                Immeuble immeuble = immeubleService.trouverImmeubleParId(immeubleId);
                // Vérifier que l'immeuble appartient au propriétaire
                if (immeuble != null && immeuble.getProprietaire().getId().equals(proprietaire.getId())) {
                    Unite unite = uniteService.creerUnite(numeroUnite, nombrePieces, superficie, loyerMensuel, description, immeuble);
                    request.setAttribute("successMessage", "Unité créée avec succès");
                }

            } else if ("update".equals(action)) {
                // Mettre à jour une unité
                String idStr = request.getParameter("id");
                String numeroUnite = request.getParameter("numeroUnite");
                int nombrePieces = Integer.parseInt(request.getParameter("nombrePieces"));
                double superficie = Double.parseDouble(request.getParameter("superficie"));
                double loyerMensuel = Double.parseDouble(request.getParameter("loyerMensuel"));
                String description = request.getParameter("description");
                boolean estDisponible = "on".equals(request.getParameter("estDisponible"));

                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Unite unite = uniteService.trouverUniteParId(id);
                    // Vérifier que l'unité appartient au propriétaire
                    if (unite != null && unite.getImmeuble().getProprietaire().getId().equals(proprietaire.getId())) {
                        unite.setNumeroUnite(numeroUnite);
                        unite.setNombrePieces(nombrePieces);
                        unite.setSuperficie(superficie);
                        unite.setLoyerMensuel(loyerMensuel);
                        unite.setDescription(description);
                        unite.setEstDisponible(estDisponible);
                        uniteService.modifierUnite(unite);
                        request.setAttribute("successMessage", "Unité modifiée avec succès");
                    }
                }
            }

            // Recharger les données
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            request.setAttribute("immeubles", immeubles);

            List<Unite> unites = immeubles.stream()
                    .flatMap(immeuble -> uniteService.listerUnitesParImmeuble(immeuble).stream())
                    .toList();
            request.setAttribute("unites", unites);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_unites.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'opération: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_unites.jsp").forward(request, response);
        } finally {
            immeubleService.close();
            uniteService.close();
        }
    }
}