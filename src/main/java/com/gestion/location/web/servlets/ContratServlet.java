package com.gestion.location.web.servlets;

import com.gestion.location.entities.Contrat;
import com.gestion.location.service.ContratService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/contrat")
public class ContratServlet extends HttpServlet {

    private final ContratService contratService = new ContratService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("contrats", contratService.listerContrats());
        request.getRequestDispatcher("contrat/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String locataireId = request.getParameter("locataireId");
        String uniteId = request.getParameter("uniteId");
        String dateDebut = request.getParameter("dateDebut");
        String dateFin = request.getParameter("dateFin");

        Contrat contrat = new Contrat();
        contrat.setDateDebut(LocalDate.parse(dateDebut));
        contrat.setDateFin(LocalDate.parse(dateFin));

        // TODO: récupérer les entités Locataire et Unite depuis leurs DAO respectifs
        // exemple : contrat.setLocataire(locataireDAO.findById(Long.parseLong(locataireId)));

        if (idParam != null && !idParam.isEmpty()) {
            contrat.setId(Long.parseLong(idParam));
        }

        contratService.ajouterContrat(contrat);
        response.sendRedirect("contrat");
    }
}
