package com.gestion.location.web.servlets;

import com.gestion.location.entities.Unite;
import com.gestion.location.entities.Immeuble;
import com.gestion.location.service.UniteService;
import com.gestion.location.service.ImmeubleService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/unites")
public class UniteServlet extends HttpServlet {

    private final UniteService uniteService = new UniteService();
    private final ImmeubleService immeubleService = new ImmeubleService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Unite> unites = uniteService.lister();
        List<Immeuble> immeubles = immeubleService.lister();

        request.setAttribute("unites", unites);
        request.setAttribute("immeubles", immeubles);
        request.getRequestDispatcher("/unite/liste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String numero = request.getParameter("numero");
        Integer pieces = Integer.parseInt(request.getParameter("pieces"));
        Double superficie = Double.parseDouble(request.getParameter("superficie"));
        Double loyer = Double.parseDouble(request.getParameter("loyer"));
        Integer immeubleId = Integer.parseInt(request.getParameter("immeubleId"));

        Unite u = new Unite();
        u.setNumero(numero);
        u.setPieces(pieces);
        u.setSuperficie(superficie);
        u.setLoyer(loyer);

        Immeuble im = immeubleService.getById(immeubleId);
        u.setImmeuble(im);

        uniteService.ajouter(u);
        response.sendRedirect(request.getContextPath() + "/unites");
    }
}
