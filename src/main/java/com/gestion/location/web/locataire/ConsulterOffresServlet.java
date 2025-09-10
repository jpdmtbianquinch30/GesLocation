package com.gestion.location.web.locataire;

import com.gestion.location.service.UniteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/locataire/offres")
public class ConsulterOffresServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UniteService uniteService = new UniteService();

        try {
            // Récupérer les paramètres de filtrage
            String prixMinStr = request.getParameter("prixMin");
            String prixMaxStr = request.getParameter("prixMax");
            String piecesStr = request.getParameter("pieces");
            String superficieStr = request.getParameter("superficie");

            Map<String, Object> filtres = new HashMap<>();

            // Appliquer les filtres
            if (prixMinStr != null && !prixMinStr.isEmpty()) {
                double prixMin = Double.parseDouble(prixMinStr);
                filtres.put("prixMin", prixMin);
            }

            if (prixMaxStr != null && !prixMaxStr.isEmpty()) {
                double prixMax = Double.parseDouble(prixMaxStr);
                filtres.put("prixMax", prixMax);
            }

            if (piecesStr != null && !piecesStr.isEmpty()) {
                int pieces = Integer.parseInt(piecesStr);
                filtres.put("pieces", pieces);
            }

            if (superficieStr != null && !superficieStr.isEmpty()) {
                double superficie = Double.parseDouble(superficieStr);
                filtres.put("superficie", superficie);
            }

            // Récupérer les unités disponibles avec filtres
            var offres = uniteService.listerUnitesDisponibles().stream()
                    .filter(unite -> {
                        if (filtres.containsKey("prixMin") && unite.getLoyerMensuel() < (double) filtres.get("prixMin")) {
                            return false;
                        }
                        if (filtres.containsKey("prixMax") && unite.getLoyerMensuel() > (double) filtres.get("prixMax")) {
                            return false;
                        }
                        if (filtres.containsKey("pieces") && unite.getNombrePieces() != (int) filtres.get("pieces")) {
                            return false;
                        }
                        if (filtres.containsKey("superficie") && unite.getSuperficie() < (double) filtres.get("superficie")) {
                            return false;
                        }
                        return true;
                    })
                    .toList();

            // Passer les données à la JSP
            request.setAttribute("offres", offres);
            request.setAttribute("filtres", filtres);

            request.getRequestDispatcher("/WEB-INF/views/locataire/consulter_offres.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Erreur lors de la consultation des offres: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/locataire/consulter_offres.jsp").forward(request, response);
        } finally {
            uniteService.close();
        }
    }
}