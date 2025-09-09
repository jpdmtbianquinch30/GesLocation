<%@ page import="com.gestion.location.entities.Immeuble" %>
<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%@ page import="com.gestion.location.service.UtilisateurService" %>
<%@ page import="java.util.List" %>

<%
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    Immeuble immeuble = (Immeuble) request.getAttribute("immeuble");

    // Liste des propriétaires pour ADMIN
    List<Utilisateur> proprietaires = null;
    if ("ADMIN".equalsIgnoreCase(user.getRole())) {
        proprietaires = new UtilisateurService().listerParRole("PROPRIETAIRE");
    }

    // Si l'accès direct au JSP est fait sans passer par la servlet → redirection
    if (immeuble == null && !"ADMIN".equalsIgnoreCase(user.getRole()) && request.getParameter("action") == null) {
        response.sendRedirect(request.getContextPath() + "/immeubles?action=dashboard");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= immeuble != null ? "Modifier Immeuble" : "Ajouter Immeuble" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4"><%= immeuble != null ? "Modifier Immeuble" : "Ajouter Immeuble" %></h2>

    <form action="<%= request.getContextPath() %>/immeubles" method="post">
        <% if (immeuble != null) { %>
        <input type="hidden" name="id" value="<%= immeuble.getId() %>"/>
        <% } %>

        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom"
                   value="<%= immeuble != null ? immeuble.getNom() : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="adresse" class="form-label">Adresse</label>
            <input type="text" class="form-control" id="adresse" name="adresse"
                   value="<%= immeuble != null ? immeuble.getAdresse() : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"><%= immeuble != null ? immeuble.getDescription() : "" %></textarea>
        </div>

        <% if (proprietaires != null) { %>
        <div class="mb-3">
            <label for="proprietaire" class="form-label">Propriétaire</label>
            <select class="form-select" id="proprietaire" name="proprietaireId" required>
                <option value="">-- Sélectionner un propriétaire --</option>
                <% for (Utilisateur p : proprietaires) { %>
                <option value="<%= p.getId() %>"
                        <%= (immeuble != null && immeuble.getProprietaire() != null && immeuble.getProprietaire().getId() == p.getId()) ? "selected" : "" %>>
                    <%= p.getNom() %>
                </option>
                <% } %>
            </select>
        </div>
        <% } %>

        <!-- Boutons -->
        <button type="submit" class="btn btn-success"><%= immeuble != null ? "Modifier" : "Ajouter" %></button>
        <a href="<%= request.getContextPath() %>/immeubles?action=dashboard" class="btn btn-secondary">Annuler</a>
        <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-danger">Quitter</a>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
