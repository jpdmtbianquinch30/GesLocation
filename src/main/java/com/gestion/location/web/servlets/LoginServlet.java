package com.gestion.location.web.servlets;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Afficher le formulaire de login
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String motDePasse = request.getParameter("motDePasse");

        Utilisateur user = authService.authenticate(email, motDePasse);

        if (user != null) {
            // Créer une session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Redirection selon rôle (exemple : ADMIN → dashboard)
            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
            } else {
                response.sendRedirect(request.getContextPath() + "/dashboard.jsp"); // ou page utilisateur
            }
        } else {
            // Auth échouée
            request.setAttribute("error", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        authService.close(); // fermer EntityManagerFactory
    }
}
