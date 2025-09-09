<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light p-4">

<div class="container">
    <h2 class="mb-4">Liste des utilisateurs</h2>

    <%
        List<Utilisateur> utilisateurs = (List<Utilisateur>) request.getAttribute("utilisateurs");
        int total = (utilisateurs != null) ? utilisateurs.size() : 0;
    %>
    <p>Total des utilisateurs : <strong><%= total %></strong></p>

    <table class="table table-striped table-bordered table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Rôle</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (utilisateurs != null && !utilisateurs.isEmpty()) {
                for (Utilisateur u : utilisateurs) {
        %>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getNom() %></td>
            <td><%= u.getEmail() %></td>
            <td><%= u.getRole() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/utilisateurs?action=modifier&id=<%= u.getId() %>" class="btn btn-sm btn-warning">Modifier</a>
                <a href="<%= request.getContextPath() %>/utilisateurs?action=supprimer&id=<%= u.getId() %>" class="btn btn-sm btn-danger"
                   onclick="return confirm('Voulez-vous vraiment supprimer cet utilisateur ?');">Supprimer</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="5" class="text-center">Aucun utilisateur trouvé</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>

    <a href="<%= request.getContextPath() %>/utilisateur/ajouter.jsp" class="btn btn-primary">Ajouter un utilisateur</a>
    <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-secondary ms-2">Quitter</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
