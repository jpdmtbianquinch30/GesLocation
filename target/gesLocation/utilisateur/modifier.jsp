<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.gestion.location.entities.Utilisateur" %>

<%
  Utilisateur u = (Utilisateur) request.getAttribute("utilisateur");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Modifier un Utilisateur</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light p-4">

<div class="container">
  <h2 class="mb-4">Modifier un Utilisateur</h2>

  <% if (u != null) { %>
  <form action="<%= request.getContextPath() %>/utilisateurs" method="post" class="w-50">
    <input type="hidden" name="id" value="<%= u.getId() %>">

    <div class="mb-3">
      <label for="nom" class="form-label">Nom</label>
      <input type="text" class="form-control" name="nom" id="nom" value="<%= u.getNom() %>" required>
    </div>

    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" class="form-control" name="email" id="email" value="<%= u.getEmail() %>" required>
    </div>

    <div class="mb-3">
      <label for="role" class="form-label">Rôle</label>
      <select class="form-select" name="role" id="role" required>
        <option value="UTILISATEUR" <%= "UTILISATEUR".equals(u.getRole()) ? "selected" : "" %>>Utilisateur</option>
        <option value="PROPRIETAIRE" <%= "PROPRIETAIRE".equals(u.getRole()) ? "selected" : "" %>>Propriétaire</option>
      </select>
    </div>

    <button type="submit" class="btn btn-warning">Modifier</button>
    <a href="<%= request.getContextPath() %>/utilisateurs" class="btn btn-secondary ms-2">Retour à la liste</a>
    <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-dark ms-2">Quitter</a>
  </form>
  <% } else { %>
  <p class="text-danger">Utilisateur introuvable.</p>
  <a href="<%= request.getContextPath() %>/utilisateurs" class="btn btn-secondary">Retour à la liste</a>
  <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
