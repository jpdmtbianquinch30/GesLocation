package com.gestion.location.web.proprietaire;

import com.gestion.location.entities.Proprietaire;
import com.gestion.location.service.ProprietaireService;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/proprietaire/dashboard")
public class ProprietaireDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || !"PROPRIETAIRE".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        ProprietaireService proprietaireService = new ProprietaireService();
        ImmeubleService immeubleService = new ImmeubleService();

        try {
            Proprietaire proprietaire = proprietaireService.trouverProprietaireParId(
                    (Long) session.getAttribute("userId")
            );

            if (proprietaire == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            var immeubles = immeubleService.listerImmeublesParProprietaire(proprietaire);
            long nbImmeubles = immeubles.size();

            request.setAttribute("immeubles", immeubles);
            request.setAttribute("nbImmeubles", nbImmeubles);

            request.getRequestDispatcher("/WEB-INF/views/proprietaire/dashboard.jsp").forward(request, response);

        } finally {
            proprietaireService.close();
            immeubleService.close();
        }
    }
}
