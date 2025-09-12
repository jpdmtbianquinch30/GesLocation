package com.gestion.location.web.locataire;

import com.gestion.location.entities.Paiement;
import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/locataire/envoyer-paiement")
public class EnvoyerPaiementServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String paiementIdStr = request.getParameter("paiementId");

        if (paiementIdStr == null || paiementIdStr.isEmpty()) {
            request.setAttribute("errorMessage", "Aucun paiement sélectionné.");
            request.getRequestDispatcher("/locataire/paiements").forward(request, response);
            return;
        }

        PaiementService paiementService = new PaiementService();

        try {
            Long paiementId = Long.parseLong(paiementIdStr);
            Paiement paiement = paiementService.trouverPaiementParId(paiementId);

            if (paiement != null && "VALIDE".equals(paiement.getStatutPaiement())) {
                // Ici, tu peux envoyer un email ou notification au propriétaire
                // Exemple fictif :
                System.out.println("Paiement #" + paiement.getId() + " envoyé au propriétaire de l'unité "
                        + paiement.getContrat().getUnite().getNumeroUnite());

                request.setAttribute("successMessage", "Le paiement a été envoyé au propriétaire avec succès !");
            } else {
                request.setAttribute("errorMessage", "Paiement non valide ou introuvable.");
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'envoi du paiement: " + e.getMessage());
        } finally {
            paiementService.close();
        }

        request.getRequestDispatcher("/WEB-INF/views/locataire/mes_paiements.jsp").forward(request, response);
    }
}
