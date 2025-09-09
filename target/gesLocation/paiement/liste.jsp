<%@ page import="com.gestion.location.entities.Paiement" %>
<%@ page import="java.util.List" %>

<%
    List<Paiement> paiements = (List<Paiement>) request.getAttribute("paiements");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Paiements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Liste des Paiements</h2>
    <div class="mb-3">
        <a href="<%= request.getContextPath() %>/paiement?action=add" class="btn btn-success">Ajouter Paiement</a>
        <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-danger">Retour Dashboard</a>
    </div>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>ID</th>
            <th>Montant</th>
            <th>Mode</th>
            <th>Statut</th>
            <th>Date paiement</th>
        </tr>
        </thead>
        <tbody>
        <% if (paiements != null && !paiements.isEmpty()) {
            for (Paiement p : paiements) { %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getMontant() %> CFA</td>
            <td><%= p.getMode() %></td>
            <td><%= p.getStatut() %></td>
            <td><%= p.getDatePaiement() %></td>
        </tr>
        <%   }
        } else { %>
        <tr>
            <td colspan="5" class="text-center">Aucun paiement trouvé.</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
