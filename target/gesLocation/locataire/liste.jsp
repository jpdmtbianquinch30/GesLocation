<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestion.location.entities.Locataire" %>
<%@ page import="com.gestion.location.service.LocataireService" %>

<%
    LocataireService locataireService = new LocataireService();
    List<Locataire> locataires = locataireService.listerLocataires();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>📋 Liste des Locataires</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">

    <!-- En-tête et boutons -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="text-primary">📋 Liste des Locataires</h2>
        <div>
            <!-- Ajouter un locataire -->
            <a href="<%= request.getContextPath() %>/locataires?action=add" class="btn btn-success me-2">
                ➕ Ajouter un nouveau locataire
            </a>
            <!-- Retour vers la liste des contrats -->
            <a href="<%= request.getContextPath() %>/contrats?action=list" class="btn btn-secondary">
                ⬅ Retour Contrats
            </a>
        </div>
    </div>

    <!-- Tableau des locataires -->
    <div class="card shadow-sm border-0 rounded-4">
        <div class="card-body p-0">
            <table class="table table-striped table-hover mb-0">
                <thead class="table-primary">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Téléphone</th>
                </tr>
                </thead>
                <tbody>
                <% if (locataires != null && !locataires.isEmpty()) {
                    for (Locataire l : locataires) { %>
                <tr>
                    <td><%= l.getId() %></td>
                    <td><%= l.getNom() %></td>
                    <td><%= l.getPrenom() %></td>
                    <td><%= l.getEmail() %></td>
                    <td><%= l.getTelephone() %></td>
                </tr>
                <% }
                } else { %>
                <tr>
                    <td colspan="5" class="text-center text-muted">⚠ Aucun locataire trouvé</td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
