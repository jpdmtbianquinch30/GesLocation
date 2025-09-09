<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Ajouter un Utilisateur</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light p-4">

<div class="container">
  <h2 class="mb-4">Ajouter un Utilisateur</h2>

  <form action="<%= request.getContextPath() %>/utilisateurs" method="post" class="w-50">
    <div class="mb-3">
      <label for="nom" class="form-label">Nom</label>
      <input type="text" class="form-control" name="nom" id="nom" placeholder="Entrez le nom" required>
    </div>

    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" class="form-control" name="email" id="email" placeholder="Entrez l'email" required>
    </div>

    <div class="mb-3">
      <label for="role" class="form-label">Rôle</label>
      <select class="form-select" name="role" id="role" required>
        <option value="" disabled selected>-- Choisir un rôle --</option>
        <option value="UTILISATEUR">Utilisateur</option>
        <option value="PROPRIETAIRE">Propriétaire</option>
      </select>
    </div>

    <button type="submit" class="btn btn-success">Ajouter</button>
    <a href="<%= request.getContextPath() %>/utilisateurs" class="btn btn-secondary ms-2">Retour à la liste</a>
    <a href="<%= request.getContextPath() %>/dashboard.jsp" class="btn btn-dark ms-2">Quitter</a>
  </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
