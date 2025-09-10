package com.gestion.location.web.filter;

import com.gestion.location.entities.Utilisateur;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class RoleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Conversion des requêtes/réponses HTTP
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // URLs publiques qui ne nécessitent pas d'authentification
        if (path.startsWith("/login") || path.startsWith("/register") ||
                path.startsWith("/assets/") || path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        // Vérifier l'authentification pour les URLs protégées
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }

        Utilisateur user = (Utilisateur) session.getAttribute("user");
        String userRole = user.getRole();

        // Vérifier les autorisations basées sur les rôles
        if (path.startsWith("/admin/") && !"ADMIN".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé - Réservé aux administrateurs");
            return;
        }

        if (path.startsWith("/proprietaire/") && !"PROPRIETAIRE".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé - Réservé aux propriétaires");
            return;
        }

        if (path.startsWith("/locataire/") && !"LOCATAIRE".equals(userRole)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès refusé - Réservé aux locataires");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage du filtre
    }
}