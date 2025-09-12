package com.gestion.location.web.auth;

import com.gestion.location.entities.Locataire;
import com.gestion.location.entities.Proprietaire;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Object user = session.getAttribute("user");

            if (user instanceof Locataire) {
                redirectToDashboard(((Locataire) user).getRole(), response, request);
                return;
            } else if (user instanceof Proprietaire) {
                redirectToDashboard(((Proprietaire) user).getRole(), response, request);
                return;
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String role = request.getParameter("role");
        String telephone = request.getParameter("telephone");

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
            if (userService.emailExiste(email)) {
                request.setAttribute("errorMessage", "Cet email est déjà utilisé");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
                return;
            }

            if ("LOCATAIRE".equals(role)) {
                LocataireService locataireService = new LocataireService();
                Locataire locataireCree = locataireService.creerLocataire(
                        nom, prenom, email, password, telephone, null
                );
                locataireService.close();

                HttpSession session = request.getSession();
                session.setAttribute("user", locataireCree); // 👉 Toujours Locataire ici
                session.setAttribute("userId", locataireCree.getId());
                session.setAttribute("userRole", locataireCree.getRole());
                session.setAttribute("userNom", locataireCree.getNom());
                session.setAttribute("userPrenom", locataireCree.getPrenom());

                redirectToDashboard(locataireCree.getRole(), response, request);

            } else if ("PROPRIETAIRE".equals(role)) {
                ProprietaireService proprietaireService = new ProprietaireService();
                userService.creerUtilisateur(nom, prenom, email, password, role);
                Proprietaire proprietaireCree = proprietaireService.trouverProprietaireParEmail(email);
                proprietaireService.close();

                HttpSession session = request.getSession();
                session.setAttribute("user", proprietaireCree); // 👉 Toujours Proprietaire ici
                session.setAttribute("userId", proprietaireCree.getId());
                session.setAttribute("userRole", proprietaireCree.getRole());
                session.setAttribute("userNom", proprietaireCree.getNom());
                session.setAttribute("userPrenom", proprietaireCree.getPrenom());

                redirectToDashboard(proprietaireCree.getRole(), response, request);

            } else {
                request.setAttribute("errorMessage", "Rôle invalide");
                request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp").forward(request, response);
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
