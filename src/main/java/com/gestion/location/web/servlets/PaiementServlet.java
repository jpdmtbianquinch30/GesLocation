package com.gestion.location.web.servlets;

import com.gestion.location.entities.Paiement;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.entities.Unite;
import com.gestion.location.service.PaiementService;
import com.gestion.location.service.UtilisateurService;
import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/paiement")
public class PaiementServlet extends HttpServlet {

    private final PaiementService paiementService = new PaiementService();
    private final UtilisateurService utilisateurService = new UtilisateurService();
    private final UniteService uniteService = new UniteService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long locataireId = Long.parseLong(req.getParameter("locataireId"));
            Long uniteId = Long.parseLong(req.getParameter("uniteId"));
            Double montant = Double.parseDouble(req.getParameter("montant"));
            String mode = req.getParameter("mode");

            // Création du paiement
            Paiement paiement = new Paiement();
            paiement.setMontant(montant);
            paiement.setMode(mode);
            paiement.setDatePaiement(LocalDate.now());
            paiement.setLocataire(utilisateurService.getById(locataireId));
            paiement.setUnite(uniteService.trouverParId(uniteId));

            // Enregistrement du paiement
            paiementService.ajouter(paiement);

            resp.sendRedirect(req.getContextPath() + "/listePaiements.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "⚠ Erreur lors de l'enregistrement du paiement.");
            req.getRequestDispatcher("/paiement/form.jsp").forward(req, resp);
        }
    }
}
