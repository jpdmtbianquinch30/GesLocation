<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Formulaire Utilisateur</title>
</head>
<body>
<h2>Utilisateur</h2>
<form action="utilisateur" method="post">
    <input type="hidden" name="id" value="${utilisateur.id}">
    Nom: <input type="text" name="nom" value="${utilisateur.nom}"><br>
    Email: <input type="email" name="email" value="${utilisateur.email}"><br>
    Mot de passe: <input type="password" name="password" value="${utilisateur.password}"><br>
    Role:
    <select name="role">
        <option value="ADMIN" ${utilisateur.role=='ADMIN'?'selected':''}>Admin</option>
        <option value="PROPRIETAIRE" ${utilisateur.role=='PROPRIETAIRE'?'selected':''}>Propriétaire</option>
        <option value="LOCATAIRE" ${utilisateur.role=='LOCATAIRE'?'selected':''}>Locataire</option>
    </select><br>
    <button type="submit">Enregistrer</button>
</form>
</body>
</html>
