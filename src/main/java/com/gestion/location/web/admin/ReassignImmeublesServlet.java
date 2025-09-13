package com.gestion.location.web.admin;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.ProprietaireService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/reassign-immeubles")
public class ReassignImmeublesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ImmeubleService immeubleService = new ImmeubleService();
        ProprietaireService proprietaireService = new ProprietaireService();

        try {
            // 1. Trouver Sophie
            Proprietaire sophie = proprietaireService.trouverProprietaireParEmail("proprio@gmail.com");

            if (sophie == null) {
                request.setAttribute("errorMessage", "Sophie (proprio@gmail.com) non trouvée");
                request.getRequestDispatcher("/WEB-INF/views/admin/result.jsp").forward(request, response);
                return;
            }

            // 2. Lister tous les immeubles
            List<Immeuble> tousImmeubles = immeubleService.listerTousLesImmeubles();

            // 3. Réassigner tous les immeubles à Sophie
            int count = 0;
            for (Immeuble immeuble : tousImmeubles) {
                immeuble.setProprietaire(sophie);
                immeubleService.modifierImmeuble(immeuble);
                count++;
            }

            // 4. Supprimer les autres propriétaires
            List<Proprietaire> tousProprietaires = proprietaireService.listerTousLesProprietaires();
            int deletedCount = 0;

            for (Proprietaire proprio : tousProprietaires) {
                if (!proprio.getId().equals(sophie.getId())) {
                    proprietaireService.supprimerProprietaire(proprio.getId());
                    deletedCount++;
                }
            }

            request.setAttribute("successMessage",
                    count + " immeubles réassignés à Sophie. " +
                            deletedCount + " autres propriétaires supprimés.");

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur: " + e.getMessage());
        } finally {
            immeubleService.close();
            proprietaireService.close();
        }

        request.getRequestDispatcher("/WEB-INF/views/admin/result.jsp").forward(request, response);
    }
}