<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ajouter/Modifier un Contrat</title>
</head>
<body>
<h2>Contrat</h2>
<form action="contrat" method="post">
    <input type="hidden" name="id" value="${contrat.id}"/>
    Locataire ID: <input type="text" name="locataireId" value="${contrat.locataire.id}"/><br/>
    Unité ID: <input type="text" name="uniteId" value="${contrat.unite.id}"/><br/>
    Date début: <input type="date" name="dateDebut" value="${contrat.dateDebut}"/><br/>
    Date fin: <input type="date" name="dateFin" value="${contrat.dateFin}"/><br/>
    <input type="submit" value="Enregistrer"/>
</form>
<a href="contrat">Retour à la liste</a>
</body>
</html>
