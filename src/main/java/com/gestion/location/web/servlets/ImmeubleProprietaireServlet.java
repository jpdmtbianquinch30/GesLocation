package com.gestion.location.web.servlets;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/proprietaire/immeubles")
public class ImmeubleProprietaireServlet extends HttpServlet {

    private final ImmeubleService service = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Utilisateur proprietaire = (Utilisateur) req.getSession().getAttribute("user");

        if ("supprimer".equals(action)) {
            long id = Long.parseLong(req.getParameter("id"));
            service.supprimer(id);
            resp.sendRedirect(req.getContextPath() + "/proprietaire/immeubles");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam != null) { // Modification
            long id = Long.parseLong(idParam);
            Immeuble i = service.trouverParId(id);
            req.setAttribute("immeuble", i);
            req.getRequestDispatcher("/proprietaire/immeuble_form.jsp").forward(req, resp);
            return;
        }

        // Liste
        List<Immeuble> immeubles = service.listerParProprietaire(proprietaire.getId());
        req.setAttribute("immeubles", immeubles);
        req.getRequestDispatcher("/proprietaire/immeuble_liste.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nom = req.getParameter("nom");
        String adresse = req.getParameter("adresse");
        String description = req.getParameter("description");
        String equipements = req.getParameter("equipements");
        String idParam = req.getParameter("id");

        Utilisateur proprietaire = (Utilisateur) req.getSession().getAttribute("user");

        Immeuble i;
        if (idParam == null || idParam.isEmpty()) { // Ajouter
            i = new Immeuble();
        } else { // Modifier
            i = service.trouverParId(Long.parseLong(idParam));
        }

        i.setNom(nom);
        i.setAdresse(adresse);
        i.setDescription(description);
        i.setEquipements(equipements);
        i.setProprietaire(proprietaire);

        if (idParam == null || idParam.isEmpty()) {
            service.ajouter(i);
        } else {
            service.modifier(i);
        }

        resp.sendRedirect(req.getContextPath() + "/proprietaire/immeubles");
    }
}

