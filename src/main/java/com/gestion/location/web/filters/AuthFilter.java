package com.gestion.location.web.filters;

import com.gestion.location.entities.Utilisateur;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Intercepter toutes les URLs sensibles
@WebFilter({"/unites/*", "/immeubles/*", "/contrats/*", "/locataires/*", "/paiement/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation si nécessaire
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // ne pas créer une session si inexistante

        Utilisateur user = (session != null) ? (Utilisateur) session.getAttribute("user") : null;

        if (user == null) {
            // Non connecté → redirection vers login.jsp
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            // Connecté → laisser passer la requête
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}
