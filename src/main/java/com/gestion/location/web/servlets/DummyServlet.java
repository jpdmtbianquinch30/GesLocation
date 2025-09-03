package com.gestion.location.web.servlets;

import com.gestion.location.web.dto.DummyDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dummy")
public class DummyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Création d'un objet DummyDTO
        DummyDTO dummy = new DummyDTO(1L, "Test", "Valeur test");

        // On met l'objet dans la requête
        request.setAttribute("dummy", dummy);

        // On redirige vers dummy.jsp
        request.getRequestDispatcher("/dummy.jsp").forward(request, response);
    }
}
