package com.gestion.location.web.servlets;

import com.gestion.location.entities.Immeuble;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/immeubles")
public class ImmeubleServlet extends HttpServlet {

    private final ImmeubleService service = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("immeubles", service.lister());
        request.getRequestDispatcher("/immeuble/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String description = request.getParameter("description");

        Immeuble i = new Immeuble();
        i.setNom(nom);
        i.setAdresse(adresse);
        i.setDescription(description);

        service.ajouter(i);
        response.sendRedirect(request.getContextPath() + "/immeubles");
    }
}
