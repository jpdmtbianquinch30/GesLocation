package com.gestion.location.web.locataire;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Unite;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/locataire/demande-location")
public class DemandeLocationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String uniteId = request.getParameter("uniteId");
        UniteService uniteService = new UniteService();

        try {
            if (uniteId != null) {
                Long id = Long.parseLong(uniteId);
                Unite unite = uniteService.trouverUniteParId(id);

                if (unite != null && unite.isEstDisponible()) {
                    request.setAttribute("uniteSelectionnee", unite);
                }
            }

            request.getRequestDispatcher("/WEB-INF/views/locataire/demande_location.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors du chargement du formulaire: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/locataire/demande_location.jsp").forward(request, response);
        } finally {
            uniteService.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Locataire locataire = (Locataire) session.getAttribute("user");

        String uniteIdStr = request.getParameter("uniteId");
        String dateDebutStr = request.getParameter("dateDebut");
        String dateFinStr = request.getParameter("dateFin");
        String message = request.getParameter("message");

        UniteService uniteService = new UniteService();
        ContratService contratService = new ContratService();

        try {
            if (uniteIdStr == null || uniteIdStr.isEmpty()) {
                throw new Exception("Aucune unité sélectionnée");
            }

            Long uniteId = Long.parseLong(uniteIdStr);
            Unite unite = uniteService.trouverUniteParId(uniteId);

            if (unite == null || !unite.isEstDisponible()) {
                throw new Exception("L'unité sélectionnée n'est plus disponible");
            }

            // Convertir les dates
            LocalDate dateDebut = LocalDate.parse(dateDebutStr);
            LocalDate dateFin = LocalDate.parse(dateFinStr);

            if (dateDebut.isBefore(LocalDate.now())) {
                throw new Exception("La date de début doit être dans le futur");
            }

            if (dateFin.isBefore(dateDebut)) {
                throw new Exception("La date de fin doit être après la date de début");
            }

            // Vérifier la disponibilité
            boolean disponible = contratService.verifierDisponibiliteUnite(unite, dateDebut, dateFin);

            if (!disponible) {
                throw new Exception("L'unité n'est pas disponible pour la période sélectionnée");
            }

            // Créer la demande de location (contrat en attente)
            Contrat contrat = new Contrat();
            contrat.setDateDebut(dateDebut);
            contrat.setDateFin(dateFin);
            contrat.setLoyerMensuel(unite.getLoyerMensuel());
            contrat.setCaution(unite.getLoyerMensuel() * 2); // Caution = 2 mois de loyer
            contrat.setEtatContrat("EN_ATTENTE");
            contrat.setLocataire(locataire);
            contrat.setUnite(unite);

            contratService.creerContrat(dateDebut, dateFin, unite.getLoyerMensuel(),
                    unite.getLoyerMensuel() * 2, "EN_ATTENTE", locataire, unite);

            request.setAttribute("successMessage", "Votre demande de location a été envoyée avec succès. " +
                    "Vous serez notifié lorsque le propriétaire aura traité votre demande.");

            request.getRequestDispatcher("/WEB-INF/views/locataire/demande_location.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'envoi de la demande: " + e.getMessage());

            // Recharger l'unité sélectionnée si possible
            if (uniteIdStr != null) {
                try {
                    Long uniteId = Long.parseLong(uniteIdStr);
                    Unite unite = uniteService.trouverUniteParId(uniteId);
                    request.setAttribute("uniteSelectionnee", unite);
                } catch (Exception ex) {
                    // Ignorer si l'unité ne peut pas être chargée
                }
            }

            request.getRequestDispatcher("/WEB-INF/views/locataire/demande_location.jsp").forward(request, response);
        } finally {
            uniteService.close();
            contratService.close();
        }
    }
}