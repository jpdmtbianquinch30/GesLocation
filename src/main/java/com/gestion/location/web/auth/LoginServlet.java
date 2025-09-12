package com.gestion.location.web.auth;

import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.service.LocataireService;
import com.gestion.location.service.ProprietaireService;
import com.gestion.location.service.UtilisateurService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String role = (String) session.getAttribute("userRole");
            redirectToDashboard(role, response, request);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");

        UtilisateurService userService = new UtilisateurService();

        try {
            Utilisateur user = userService.authentifier(email, password);

            if (user != null) {
                HttpSession session = request.getSession(true);

                Object fullUser = user; // par défaut (cas ADMIN)

                if ("LOCATAIRE".equalsIgnoreCase(user.getRole())) {
                    LocataireService locataireService = new LocataireService();
                    fullUser = locataireService.trouverLocataireParEmail(email);
                    locataireService.close();
                } else if ("PROPRIETAIRE".equalsIgnoreCase(user.getRole())) {
                    ProprietaireService proprietaireService = new ProprietaireService();
                    fullUser = proprietaireService.trouverProprietaireParEmail(email);
                    proprietaireService.close();
                }

                // Stocker l'entité complète (Locataire ou Propriétaire ou Utilisateur)
                session.setAttribute("user", fullUser);
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole().toUpperCase());
                session.setAttribute("userNom", user.getNom());
                session.setAttribute("userPrenom", user.getPrenom());

                redirectToDashboard(user.getRole(), response, request);

            } else {
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Erreur lors de l'authentification: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } finally {
            userService.close();
        }
    }

    private void redirectToDashboard(String role, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String contextPath = request.getContextPath();
        String r = role.toUpperCase();

        switch (r) {
            case "ADMIN":
                response.sendRedirect(contextPath + "/admin/dashboard");
                break;
            case "PROPRIETAIRE":
                response.sendRedirect(contextPath + "/proprietaire/dashboard");
                break;
            case "LOCATAIRE":
                response.sendRedirect(contextPath + "/locataire/dashboard");
                break;
            default:
                HttpSession session = request.getSession(false);
                if (session != null) session.invalidate();
                response.sendRedirect(contextPath + "/login");
        }
    }
}
