<%@ page import="com.gestion.location.entities.Immeuble" %>
<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%@ page import="java.util.List" %>

<%
    // Vérifier l'utilisateur connecté
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Vérifier si on vient de la servlet
    List<Immeuble> immeubles = (List<Immeuble>) request.getAttribute("immeubles");
    if (immeubles == null) {
        // Redirection automatique vers la servlet
        response.sendRedirect(request.getContextPath() + "/immeubles?action=dashboard");
        return;
    }
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Immeubles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Liste des Immeubles</h2>
    <div class="mb-3">
        <a href="<%= request.getContextPath() %>/immeubles?action=add" class="btn btn-success">Ajouter Immeuble</a>
        <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-danger">Quitter</a>
    </div>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Adresse</th>
            <th>Description</th>
            <th>Propriétaire</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% if (!immeubles.isEmpty()) {
            for (Immeuble i : immeubles) { %>
        <tr>
            <td><%= i.getId() %></td>
            <td><%= i.getNom() %></td>
            <td><%= i.getAdresse() %></td>
            <td><%= i.getDescription() %></td>
            <td><%= i.getProprietaire() != null ? i.getProprietaire().getNom() : "N/A" %></td>
            <td>
                <a href="<%= request.getContextPath() %>/immeubles?action=edit&id=<%= i.getId() %>" class="btn btn-primary btn-sm">Modifier</a>
                <a href="<%= request.getContextPath() %>/immeubles?action=delete&id=<%= i.getId() %>"
                   class="btn btn-danger btn-sm"
                   onclick="return confirm('Voulez-vous vraiment supprimer cet immeuble ?');">Supprimer</a>
            </td>
        </tr>
        <%   }
        } else { %>
        <tr>
            <td colspan="6" class="text-center">Aucun immeuble trouvé.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
