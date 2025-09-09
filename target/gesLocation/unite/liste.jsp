<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestion.location.entities.Unite" %>
<%@ page import="java.util.List" %>

<%
    List<Unite> unites = (List<Unite>) request.getAttribute("unites");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Unités</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f8f9fa; }
        .card { border-radius: 12px; }
        .table thead { background-color: #0d6efd; color: white; }
        .table tbody tr:hover { background-color: #e9ecef; }
        h2 { color: #0d6efd; }
    </style>
</head>
<body>

<div class="container mt-5">

    <!-- Header avec boutons -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Liste des Unités</h2>
        <div>
            <a href="<%= request.getContextPath() %>/contrats?action=list" class="btn btn-secondary me-2">
                Retour Contrats
            </a>
            <a href="<%= request.getContextPath() %>/unites?action=add" class="btn btn-success">
                Ajouter Unité
            </a>
        </div>
    </div>

    <!-- Tableau des unités -->
    <div class="card shadow-sm">
        <div class="card-body p-0">
            <table class="table table-striped table-hover mb-0">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Numéro</th>
                    <th>Pièces</th>
                    <th>Superficie</th>
                    <th>Loyer</th>
                    <th>Immeuble</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <%
                    if (unites != null && !unites.isEmpty()) {
                        for (Unite u : unites) {
                %>
                <tr>
                    <td><%= u.getId() %></td>
                    <td><%= u.getNumero() %></td>
                    <td><%= u.getPieces() %></td>
                    <td><%= u.getSuperficie() %></td>
                    <td><%= u.getLoyer() %></td>
                    <td><%= u.getImmeuble() != null ? u.getImmeuble().getNom() : "-" %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/unites?action=edit&id=<%= u.getId() %>" class="btn btn-sm btn-primary">Modifier</a>
                        <a href="<%= request.getContextPath() %>/unites?action=delete&id=<%= u.getId() %>" class="btn btn-sm btn-danger"
                           onclick="return confirm('Voulez-vous vraiment supprimer cette unité ?');">Supprimer</a>
                    </td>
                </tr>
                <%
                    }
                } else {
                %>
                <tr>
                    <td colspan="7" class="text-center text-muted">Aucune unité trouvée</td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
