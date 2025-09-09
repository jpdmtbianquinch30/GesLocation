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

@WebServlet("/unites")
public class UniteServlet extends HttpServlet {

    private final UniteService uniteService = new UniteService();
    private final ImmeubleService immeubleService = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Utilisateur user = (Utilisateur) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        // Charger les immeubles accessibles à l'utilisateur
        List<Immeuble> immeubles = user.getRole().equalsIgnoreCase("ADMIN") ?
                immeubleService.lister() :
                immeubleService.listerParProprietaire(user.getId());
        request.setAttribute("immeubles", immeubles);

        // --- Ajouter une unité ---
        if ("add".equalsIgnoreCase(action)) {
            request.getRequestDispatcher("/unite/form.jsp").forward(request, response);
            return;
        }

        // --- Modifier une unité ---
        if ("edit".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Unite unite = uniteService.trouverParId(id);
                if (unite != null && (user.getRole().equalsIgnoreCase("ADMIN") ||
                        (unite.getImmeuble() != null
                                && unite.getImmeuble().getProprietaire() != null
                                && unite.getImmeuble().getProprietaire().getId().equals(user.getId())))) {
                    request.setAttribute("unite", unite);
                    request.getRequestDispatcher("/unite/form.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException ignored) {}
        }

        // --- Supprimer une unité ---
        if ("delete".equalsIgnoreCase(action) && idParam != null && !idParam.isEmpty()) {
            try {
                Long id = Long.parseLong(idParam);
                Unite unite = uniteService.trouverParId(id);
                if (unite != null && (user.getRole().equalsIgnoreCase("ADMIN") ||
                        (unite.getImmeuble() != null
                                && unite.getImmeuble().getProprietaire() != null
                                && unite.getImmeuble().getProprietaire().getId().equals(user.getId())))) {
                    uniteService.supprimer(id); // ✅ on supprime directement par ID
                }
            } catch (NumberFormatException ignored) {}
            response.sendRedirect(request.getContextPath() + "/unites");
            return;
        }

        // --- Liste des unités accessibles ---
        List<Unite> unites = user.getRole().equalsIgnoreCase("ADMIN") ?
                uniteService.lister() :
                uniteService.listerParProprietaire(user.getId());
        request.setAttribute("unites", unites);

        request.getRequestDispatcher("/unite/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idParam = request.getParameter("id");
            String numero = request.getParameter("numero");
            String piecesStr = request.getParameter("pieces");
            String superficieStr = request.getParameter("superficie");
            String loyerStr = request.getParameter("loyer");
            String immeubleIdStr = request.getParameter("immeubleId");

            Long id = (idParam != null && !idParam.isEmpty()) ? Long.parseLong(idParam) : null;
            int pieces = (piecesStr != null && !piecesStr.isEmpty()) ? Integer.parseInt(piecesStr) : 0;
            double superficie = (superficieStr != null && !superficieStr.isEmpty()) ? Double.parseDouble(superficieStr) : 0.0;
            double loyer = (loyerStr != null && !loyerStr.isEmpty()) ? Double.parseDouble(loyerStr) : 0.0;
            Long immeubleId = (immeubleIdStr != null && !immeubleIdStr.isEmpty()) ? Long.parseLong(immeubleIdStr) : null;

            Unite unite = (id != null) ? uniteService.trouverParId(id) : new Unite();

            if (unite == null) {
                unite = new Unite(); // sécurité si id invalide
            }

            unite.setNumero(numero);
            unite.setPieces(pieces);
            unite.setSuperficie(superficie);
            unite.setLoyer(loyer);

            if (immeubleId != null) {
                Immeuble immeuble = immeubleService.trouverParId(immeubleId);
                unite.setImmeuble(immeuble);
            }

            if (id == null) {
                uniteService.ajouter(unite);
            } else {
                uniteService.modifier(unite);
            }

            response.sendRedirect(request.getContextPath() + "/unites");

        } catch (Exception e) {
            request.setAttribute("error", "⚠ Veuillez entrer des valeurs valides.");
            request.setAttribute("immeubles", immeubleService.lister());
            request.getRequestDispatcher("/unite/form.jsp").forward(request, response);
        }
    }
}
