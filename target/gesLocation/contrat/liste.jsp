<%@ page import="com.gestion.location.entities.Contrat" %>
<%@ page import="java.util.List" %>

<%
    List<Contrat> contrats = (List<Contrat>) request.getAttribute("contrats");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Contrats</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <!-- En-tête -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold text-primary">
            📑 Gestion des Contrats
        </h2>
        <div>
            <a href="<%= request.getContextPath() %>/contrats?action=add" class="btn btn-success me-2">
                ➕ Ajouter Contrat
            </a>
            <a href="<%= request.getContextPath() %>/unites" class="btn btn-success">
                ➕ Ajouter Unité
            </a>
            <a href="<%= request.getContextPath() %>/locataires?action=add" class="btn btn-warning me-2">
                👤 Ajouter Locataire
            </a>
            <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-danger">
                ⬅ Retour Dashboard
            </a>
        </div>
    </div>

    <!-- Tableau -->
    <div class="card shadow">
        <div class="card-body">
            <table class="table table-hover align-middle">
                <thead class="table-dark">
                <tr>
                    <th>#</th>
                    <th>Locataire</th>
                    <th>Unité</th>
                    <th>Date Début</th>
                    <th>Date Fin</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <% if (contrats != null && !contrats.isEmpty()) {
                    for (Contrat c : contrats) { %>
                <tr>
                    <td><span class="badge bg-secondary"><%= c.getId() %></span></td>
                    <td><%= c.getLocataire() != null ? c.getLocataire().getNom() : "❌ Aucun" %></td>
                    <td><%= c.getUnite() != null ? c.getUnite().getNumero() : "❌ Aucune" %></td>
                    <td><span class="badge bg-info text-dark"><%= c.getDateDebut() %></span></td>
                    <td><span class="badge bg-success"><%= c.getDateFin() %></span></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/contrats?action=edit&id=<%= c.getId() %>"
                           class="btn btn-sm btn-outline-primary">✏ Modifier</a>
                        <a href="<%= request.getContextPath() %>/contrats?action=delete&id=<%= c.getId() %>"
                           class="btn btn-sm btn-outline-danger"
                           onclick="return confirm('Voulez-vous vraiment supprimer ce contrat ?');">🗑 Supprimer</a>
                    </td>
                </tr>
                <%   }
                } else { %>
                <tr>
                    <td colspan="6" class="text-center text-muted">⚠ Aucun contrat trouvé</td>
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
