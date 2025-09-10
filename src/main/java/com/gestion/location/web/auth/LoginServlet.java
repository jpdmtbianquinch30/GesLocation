package com.gestion.location.web.auth;

import com.gestion.location.entities.Utilisateur;
import com.gestion.location.entities.Proprietaire;
import com.gestion.location.entities.Locataire;
import com.gestion.location.service.UtilisateurService;
import com.gestion.location.service.ProprietaireService;
import com.gestion.location.service.LocataireService;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            redirectToDashboard(user.getRole(), response, request);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtilisateurService userService = new UtilisateurService();
        ProprietaireService proprietaireService = new ProprietaireService();
        LocataireService locataireService = new LocataireService();

        try {
            Utilisateur user = userService.authentifier(email, password);

            if (user != null) {
                HttpSession session = request.getSession();

                // STOCKER LE BON TYPE D'OBJET SELON LE RÔLE
                if ("PROPRIETAIRE".equals(user.getRole())) {
                    Proprietaire proprietaire = proprietaireService.trouverProprietaireParId(user.getId());
                    if (proprietaire != null) {
                        session.setAttribute("user", proprietaire);
                    } else {
                        // Fallback si le propriétaire n'est pas trouvé
                        session.setAttribute("user", user);
                    }
                }
                else if ("LOCATAIRE".equals(user.getRole())) {
                    Locataire locataire = locataireService.trouverLocataireParId(user.getId());
                    if (locataire != null) {
                        session.setAttribute("user", locataire);
                    } else {
                        // Fallback si le locataire n'est pas trouvé
                        session.setAttribute("user", user);
                    }
                }
                else {
                    // Pour ADMIN et autres rôles
                    session.setAttribute("user", user);
                }

                // Stocker les informations de base en session
                session.setAttribute("userId", user.getId());
                session.setAttribute("userRole", user.getRole());
                session.setAttribute("userNom", user.getNom());
                session.setAttribute("userPrenom", user.getPrenom());

                redirectToDashboard(user.getRole(), response, request);
            } else {
                request.setAttribute("errorMessage", "Email ou mot de passe incorrect");
                request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'authentification: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp").forward(request, response);
        } finally {
            userService.close();
            proprietaireService.close();
            locataireService.close();
        }
    }

    private void redirectToDashboard(String role, HttpServletResponse response, HttpServletRequest request) throws IOException {
        String contextPath = request.getContextPath();

        switch (role) {
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
                response.sendRedirect(contextPath + "/login");
        }
    }
}