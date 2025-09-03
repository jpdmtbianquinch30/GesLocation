<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.ee/jstl/core" prefix="c" %>
<html>
<head>
    <title>Liste des Immeubles</title>
</head>
<body>
<h1>Immeubles</h1>
<table border="1">
    <tr>
        <th>Nom</th>
        <th>Adresse</th>
        <th>Description</th>
    </tr>
    <c:forEach var="i" items="${immeubles}">
        <tr>
            <td>${i.nom}</td>
            <td>${i.adresse}</td>
            <td>${i.description}</td>
        </tr>
    </c:forEach>
</table>
<a href="form.jsp">Ajouter un immeuble</a>
</body>
</html>
