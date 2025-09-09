<%--
  Created by IntelliJ IDEA.
  User: jeanp
  Date: 07/09/2025
  Time: 07:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="unite_form.jsp?immeubleId=${immeuble.id}">Ajouter une unité</a>
<table>
    <tr><th>Numéro</th><th>Pièces</th><th>Superficie</th><th>Loyer</th><th>Actions</th></tr>
    <c:forEach var="u" items="${unites}">
        <tr>
            <td>${u.numero}</td>
            <td>${u.pieces}</td>
            <td>${u.superficie}</td>
            <td>${u.loyer}</td>
            <td>
                <a href="unites?id=${u.id}">Modifier</a>
                <a href="unites?action=supprimer&id=${u.id}&immeubleId=${immeuble.id}">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
