<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Liste des unités</title>
</head>
<body>
<h1>Liste des unités</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Numéro</th>
        <th>Pièces</th>
        <th>Superficie</th>
        <th>Loyer</th>
        <th>Immeuble</th>
    </tr>
    <c:forEach var="u" items="${unites}">
        <tr>
            <td>${u.id}</td>
            <td>${u.numero}</td>
            <td>${u.pieces}</td>
            <td>${u.superficie}</td>
            <td>${u.loyer}</td>
            <td>${u.immeuble.nom}</td>
        </tr>
    </c:forEach>
</table>

<h2>Ajouter une unité</h2>
<form method="post" action="${pageContext.request.contextPath}/unites">
    Numéro: <input type="text" name="numero" required/><br/>
    Pièces: <input type="number" name="pieces" required/><br/>
    Superficie: <input type="text" name="superficie" required/><br/>
    Loyer: <input type="text" name="loyer" required/><br/>
    Immeuble:
    <select name="immeubleId" required>
        <c:forEach var="im" items="${immeubles}">
            <option value="${im.id}">${im.nom}</option>
        </c:forEach>
    </select><br/>
    <button type="submit">Ajouter</button>
</form>

</body>
</html>
