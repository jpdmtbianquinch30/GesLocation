<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des Contrats</title>
</head>
<body>
<h2>Contrats</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Locataire</th>
        <th>Unité</th>
        <th>Date début</th>
        <th>Date fin</th>
    </tr>
    <c:forEach var="contrat" items="${contrats}">
        <tr>
            <td>${contrat.id}</td>
            <td>${contrat.locataire.nom}</td>
            <td>${contrat.unite.numero}</td>
            <td>${contrat.dateDebut}</td>
            <td>${contrat.dateFin}</td>
        </tr>
    </c:forEach>
</table>
<a href="contrat/form.jsp">Ajouter un contrat</a>
</body>
</html>
