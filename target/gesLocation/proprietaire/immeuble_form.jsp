<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    com.gestion.location.entities.Utilisateur user =
            (com.gestion.location.entities.Utilisateur) session.getAttribute("user");
    if (user == null || !user.getRole().equals("PROPRIETAIRE")) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    com.gestion.location.entities.Immeuble immeuble =
            (com.gestion.location.entities.Immeuble) request.getAttribute("immeuble");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= (immeuble != null) ? "Modifier Immeuble" : "Ajouter Immeuble" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h2><%= (immeuble != null) ? "Modifier Immeuble" : "Ajouter Immeuble" %></h2>
    <form action="<%= request.getContextPath() %>/immeubles" method="post">
        <% if (immeuble != null) { %>
        <input type="hidden" name="id" value="<%= immeuble.getId() %>"/>
        <% } %>

        <div class="mb-3">
            <label for="nom" class="form-label">Nom de l'immeuble</label>
            <input type="text" class="form-control" id="nom" name="nom"
                   value="<%= (immeuble != null) ? immeuble.getNom() : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="adresse" class="form-label">Adresse</label>
            <input type="text" class="form-control" id="adresse" name="adresse"
                   value="<%= (immeuble != null) ? immeuble.getAdresse() : "" %>" required>
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3"><%= (immeuble != null) ? immeuble.getDescription() : "" %></textarea>
        </div>

        <button type="submit" class="btn btn-success">
            <%= (immeuble != null) ? "Modifier" : "Ajouter" %>
        </button>
        <a href="<%= request.getContextPath() %>/proprietaire.jsp" class="btn btn-secondary">Annuler</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
