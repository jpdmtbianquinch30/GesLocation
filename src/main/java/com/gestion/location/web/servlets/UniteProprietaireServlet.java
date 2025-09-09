package com.gestion.location.web.servlets;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Unite;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.ImmeubleService;
import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/unites")
public class UniteProprietaireServlet extends HttpServlet {

    private final UniteService uniteService = new UniteService();
    private final ImmeubleService immeubleService = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Utilisateur proprietaire = (Utilisateur) session.getAttribute("utilisateur");

        String action = req.getParameter("action");
        if ("supprimer".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            uniteService.supprimer(id);
            resp.sendRedirect(req.getContextPath() + "/proprietaire/unites?immeubleId=" + req.getParameter("immeubleId"));
            return;
        }

        String idParam = req.getParameter("id");
        String immeubleIdParam = req.getParameter("immeubleId");

        if (idParam != null && !idParam.isEmpty()) { // Modification
            Unite u = uniteService.trouverParId(Long.parseLong(idParam));
            req.setAttribute("unite", u);
            req.getRequestDispatcher("/proprietaire/unite_form.jsp").forward(req, resp);
            return;
        }

        List<Unite> unites;
        if (immeubleIdParam != null && !immeubleIdParam.isEmpty()) {
            Long immeubleId = Long.parseLong(immeubleIdParam);
            unites = uniteService.listerParImmeuble(immeubleId);
            Immeuble immeuble = immeubleService.trouverParId(immeubleId);
            req.setAttribute("immeuble", immeuble);
        } else {
            unites = uniteService.listerParProprietaire(proprietaire.getId());
        }

        req.setAttribute("unites", unites);
        req.getRequestDispatcher("/proprietaire/unite_liste.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String numero = req.getParameter("numero");
        int pieces = Integer.parseInt(req.getParameter("pieces"));
        double superficie = Double.parseDouble(req.getParameter("superficie"));
        double loyer = Double.parseDouble(req.getParameter("loyer"));
        long immeubleId = Long.parseLong(req.getParameter("immeubleId"));
        String idParam = req.getParameter("id");

        Unite u;
        if (idParam == null || idParam.isEmpty()) {
            u = new Unite();
        } else {
            u = uniteService.trouverParId(Long.parseLong(idParam));
        }

        u.setNumero(numero);
        u.setPieces(pieces);
        u.setSuperficie(superficie);
        u.setLoyer(loyer);
        u.setImmeuble(immeubleService.trouverParId(immeubleId));

        if (idParam == null || idParam.isEmpty()) {
            uniteService.ajouter(u);
        } else {
            uniteService.modifier(u);
        }

        resp.sendRedirect(req.getContextPath() + "/proprietaire/unites?immeubleId=" + immeubleId);
    }
}
