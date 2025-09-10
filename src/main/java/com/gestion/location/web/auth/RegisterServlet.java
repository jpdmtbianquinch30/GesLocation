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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Utilisateur user = (Utilisateur) session.getAttribute("user");
            // CORRECTION : Ajouter le paramètre request
            redirectToDashboard(user.getRole(), response, request);
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");
        String telephone = request.getParameter("telephone");

        // Validation des données
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Les mots de passe ne correspondent pas");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        if (nom == null || nom.trim().isEmpty() ||
                prenom == null || prenom.trim().isEmpty() ||
                email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Tous les champs obligatoires doivent être remplis");
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
            return;
        }

        UtilisateurService userService = new UtilisateurService();
        try {
            // Vérifier si l'email existe déjà
            if (userService.emailExiste(email)) {
                request.setAttribute("errorMessage", "Cet email est déjà utilisé");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            Utilisateur newUser = null; // DÉCLARATION CORRIGÉE

            if ("LOCATAIRE".equals(role)) {
                // Créer un locataire
                LocataireService locataireService = new LocataireService();
                try {
                    userService.creerUtilisateur(nom, prenom, email, password, role);
                    newUser = locataireService.trouverLocataireParEmail(email);
                    locataireService.close();
                } catch (Exception e) {
                    request.setAttribute("errorMessage", "Erreur lors de la création du locataire: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                    return;
                }
            } else if ("PROPRIETAIRE".equals(role)) {
                // Créer un propriétaire
                ProprietaireService proprietaireService = new ProprietaireService();
                try {
                    userService.creerUtilisateur(nom, prenom, email, password, role);
                    newUser = proprietaireService.trouverProprietaireParEmail(email);
                    proprietaireService.close();
                } catch (Exception e) {
                    request.setAttribute("errorMessage", "Erreur lors de la création du propriétaire: " + e.getMessage());
                    request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                    return;
                }
            } else {
                request.setAttribute("errorMessage", "Rôle invalide");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            // Connecter automatiquement l'utilisateur après l'inscription
            if (newUser != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", newUser);
                session.setAttribute("userId", newUser.getId());
                session.setAttribute("userRole", newUser.getRole());
                session.setAttribute("userNom", newUser.getNom());
                session.setAttribute("userPrenom", newUser.getPrenom());

                // CORRECTION : Ajouter le paramètre request
                redirectToDashboard(newUser.getRole(), response, request);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de l'inscription: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
        } finally {
            userService.close();
        }
    }

    private void redirectToDashboard(String role, HttpServletResponse response, HttpServletRequest request) throws IOException {
        switch (role) {
            case "ADMIN":
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                break;
            case "PROPRIETAIRE":
                response.sendRedirect(request.getContextPath() + "/proprietaire/dashboard");
                break;
            case "LOCATAIRE":
                response.sendRedirect(request.getContextPath() + "/locataire/dashboard");
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}