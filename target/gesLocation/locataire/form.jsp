<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Ajouter un Locataire</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<h2>Formulaire Locataire</h2>
<form action="locataire" method="post">
    <label>Nom :</label>
    <input type="text" name="nom" required><br>

    <label>Prénom :</label>
    <input type="text" name="prenom" required><br>

    <label>Email :</label>
    <input type="email" name="email" required><br>

    <label>Téléphone :</label>
    <input type="text" name="telephone" required><br>

    <button type="submit">Enregistrer</button>
</form>

<br>
<a href="listeLocataires.jsp">Voir la liste des locataires</a>
</body>
</html>
