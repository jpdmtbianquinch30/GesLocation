package com.gestion.location.web.servlets;

import com.gestion.location.service.PaiementService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/paiement")
public class PaiementServlet extends HttpServlet {
    private PaiementService paiementService = new PaiementService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Double montant = Double.parseDouble(req.getParameter("montant"));
        String mode = req.getParameter("mode");

        paiementService.effectuerPaiement(montant, mode);
        resp.sendRedirect("listePaiements.jsp");
    }
}
