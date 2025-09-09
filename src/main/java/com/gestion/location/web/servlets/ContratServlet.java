package com.gestion.location.web.servlets;

import com.gestion.location.entities.Contrat;
import com.gestion.location.entities.Unite;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.ContratService;
import com.gestion.location.service.UniteService;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/contrats")
public class ContratServlet extends HttpServlet {

    private final ContratService contratService = new ContratService();
    private final UniteService uniteService = new UniteService();
    private final UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                List<Unite> unites = uniteService.listerUnites();
                List<Utilisateur> locataires = utilisateurService.listerParRole("LOCATAIRE");
                request.setAttribute("unites", unites);
                request.setAttribute("locataires", locataires);
                request.getRequestDispatcher("/contrat/form.jsp").forward(request, response);
                break;
            case "edit":
                Long editId = Long.parseLong(request.getParameter("id"));
                Contrat contrat = contratService.trouverParId(editId);
                if (contrat != null) {
                    request.setAttribute("contrat", contrat);
                    request.getRequestDispatcher("/contrat/form.jsp").forward(request, response);
                } else {
                    response.sendRedirect("contrats");
                }
                break;
            case "delete":
                Long deleteId = Long.parseLong(request.getParameter("id"));
                Contrat c = contratService.trouverParId(deleteId);
                if (c != null) {
                    contratService.supprimer(c);
                }
                response.sendRedirect("contrats");
                break;
            default:
                List<Contrat> contrats = contratService.listerContrats();
                request.setAttribute("contrats", contrats);
                request.getRequestDispatcher("/contrat/liste.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        Long id = (idStr != null && !idStr.isEmpty()) ? Long.parseLong(idStr) : null;

        Long uniteId = Long.parseLong(request.getParameter("uniteId"));
        Long locataireId = Long.parseLong(request.getParameter("locataireId"));
        LocalDate dateDebut = LocalDate.parse(request.getParameter("dateDebut"));
        LocalDate dateFin = LocalDate.parse(request.getParameter("dateFin"));
        String etat = request.getParameter("etat");

        Unite unite = uniteService.trouverParId(uniteId);
        Utilisateur locataire = utilisateurService.trouverParId(locataireId);

        Contrat contrat;
        if (id == null) {
            contrat = new Contrat();
        } else {
            contrat = contratService.trouverParId(id);
        }

        contrat.setUnite(unite);
        contrat.setLocataire(locataire);
        contrat.setDateDebut(dateDebut);
        contrat.setDateFin(dateFin);
        contrat.setEtat(etat);

        if (id == null) {
            contratService.ajouter(contrat);
        } else {
            contratService.modifier(contrat);
        }

        response.sendRedirect("contrats");
    }
}
