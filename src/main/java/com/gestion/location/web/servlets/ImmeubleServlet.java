package com.gestion.location.web.servlets;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/immeubles")
public class ImmeubleServlet extends HttpServlet {

    private final ImmeubleService service = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        // Ajouter un immeuble
        if ("add".equals(action)) {
            request.getRequestDispatcher("/immeuble/form.jsp").forward(request, response);
            return;
        }

        // Éditer un immeuble
        if ("edit".equals(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.valueOf(idParam); // ✅ ID en Long
                Immeuble immeuble = service.trouverParId(id);
                if (immeuble != null && (user.getRole().equalsIgnoreCase("ADMIN") ||
                        (immeuble.getProprietaire() != null && immeuble.getProprietaire().getId().equals(user.getId())))) {
                    request.setAttribute("immeuble", immeuble);
                    request.getRequestDispatcher("/immeuble/form.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException ignored) {}
        }

        // Supprimer un immeuble
        if ("delete".equals(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.valueOf(idParam); // ✅ ID en Long
                Immeuble immeuble = service.trouverParId(id);
                if (immeuble != null && (user.getRole().equalsIgnoreCase("ADMIN") ||
                        (immeuble.getProprietaire() != null && immeuble.getProprietaire().getId().equals(user.getId())))) {
                    service.supprimer(id);
                }
            } catch (NumberFormatException ignored) {}
            response.sendRedirect(request.getContextPath() + "/immeubles");
            return;
        }

        // Liste des immeubles
        List<Immeuble> immeubles = user.getRole().equalsIgnoreCase("ADMIN") ?
                service.lister() :
                service.listerParProprietaire(user.getId());

        request.setAttribute("immeubles", immeubles);
        request.getRequestDispatcher("/immeuble/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Utilisateur user = (Utilisateur) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String idParam = request.getParameter("id");
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String description = request.getParameter("description");

        if (nom == null || nom.trim().isEmpty() || adresse == null || adresse.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/immeubles");
            return;
        }

        if (idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.valueOf(idParam); // ✅ ID en Long
                Immeuble existing = service.trouverParId(id);
                if (existing != null && (user.getRole().equalsIgnoreCase("ADMIN") ||
                        (existing.getProprietaire() != null && existing.getProprietaire().getId().equals(user.getId())))) {
                    existing.setNom(nom);
                    existing.setAdresse(adresse);
                    existing.setDescription(description);
                    service.modifier(existing);
                }
            } catch (NumberFormatException ignored) {}
        } else {
            Immeuble i = new Immeuble();
            i.setNom(nom);
            i.setAdresse(adresse);
            i.setDescription(description);
            i.setProprietaire(user);
            service.ajouter(i);
        }

        response.sendRedirect(request.getContextPath() + "/immeubles");
    }
}
