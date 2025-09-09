package com.gestion.location.web.servlets;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class AuthServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Utilisateur user = authService.authenticate(username, password);

        String contextPath = req.getContextPath(); // ex: /gesLocation

        if (user != null) {
            req.getSession().setAttribute("user", user);

            String role = user.getRole() != null ? user.getRole().toUpperCase() : "";

            switch (role) {
                case "ADMIN":
                    resp.sendRedirect(contextPath + "/dashboard.jsp");
                    break;
                case "PROPRIETAIRE":
                    resp.sendRedirect(contextPath + "/proprietaire.jsp");
                    break;
                case "UTILISATEUR":
                    resp.sendRedirect(contextPath + "/index.jsp");
                    break;
                default:
                    // Redirection vers login dans /assets avec erreur
                    resp.sendRedirect(contextPath + "/login.jsp?error=invalid");
                    break;
            }
        } else {
            resp.sendRedirect(contextPath + "/login.jsp?error=invalid");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Redirection vers login.jsp dans /assets
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
