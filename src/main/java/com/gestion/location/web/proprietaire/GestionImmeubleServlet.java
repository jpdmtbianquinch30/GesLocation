package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/immeubles")
public class GestionImmeubleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");
        ImmeubleService immeubleService = new ImmeubleService();

        try {
            if ("edit".equals(action)) {
                // Afficher le formulaire d'édition
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Immeuble immeuble = immeubleService.trouverImmeubleParId(id);
                    // Vérifier que l'immeuble appartient au propriétaire
                    if (immeuble != null && immeuble.getProprietaire().getId().equals(proprietaire.getId())) {
                        request.setAttribute("immeubleToEdit", immeuble);
                    }
                }
            } else if ("delete".equals(action)) {
                // Supprimer un immeuble
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Immeuble immeuble = immeubleService.trouverImmeubleParId(id);
                    // Vérifier que l'immeuble appartient au propriétaire
                    if (immeuble != null && immeuble.getProprietaire().getId().equals(proprietaire.getId())) {
                        immeubleService.supprimerImmeuble(id);
                        request.setAttribute("successMessage", "Immeuble supprimé avec succès");
                    }
                }
            }

            // Lister les immeubles du propriétaire
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            request.setAttribute("immeubles", immeubles);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_immeubles.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la gestion des immeubles: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_immeubles.jsp").forward(request, response);
        } finally {
            immeubleService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Proprietaire proprietaire = (Proprietaire) session.getAttribute("user");

        String action = request.getParameter("action");
        ImmeubleService immeubleService = new ImmeubleService();

        try {
            if ("create".equals(action)) {
                // Créer un nouvel immeuble
                String nom = request.getParameter("nom");
                String adresse = request.getParameter("adresse");
                String description = request.getParameter("description");
                String equipements = request.getParameter("equipements");

                Immeuble immeuble = immeubleService.creerImmeuble(nom, adresse, description, equipements, proprietaire);
                request.setAttribute("successMessage", "Immeuble créé avec succès");

            } else if ("update".equals(action)) {
                // Mettre à jour un immeuble
                String idStr = request.getParameter("id");
                String nom = request.getParameter("nom");
                String adresse = request.getParameter("adresse");
                String description = request.getParameter("description");
                String equipements = request.getParameter("equipements");

                if (idStr != null) {
                    Long id = Long.parseLong(idStr);
                    Immeuble immeuble = immeubleService.trouverImmeubleParId(id);
                    // Vérifier que l'immeuble appartient au propriétaire
                    if (immeuble != null && immeuble.getProprietaire().getId().equals(proprietaire.getId())) {
                        immeuble.setNom(nom);
                        immeuble.setAdresse(adresse);
                        immeuble.setDescription(description);
                        immeuble.setEquipements(equipements);
                        immeubleService.modifierImmeuble(immeuble);
                        request.setAttribute("successMessage", "Immeuble modifié avec succès");
                    }
                }
            }

            // Recharger la liste des immeubles
            List<Immeuble> immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            request.setAttribute("immeubles", immeubles);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_immeubles.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'opération: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/proprietaire/gestion_immeubles.jsp").forward(request, response);
        } finally {
            immeubleService.close();
        }
    }
}