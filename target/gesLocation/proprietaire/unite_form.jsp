<%--
  Created by IntelliJ IDEA.
  User: jeanp
  Date: 07/09/2025
  Time: 07:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/proprietaire/unites" method="post">
    <input type="hidden" name="id" value="${unite.id}"/>
    <input type="hidden" name="immeubleId" value="${unite.immeuble.id != null ? unite.immeuble.id : param.immeubleId}"/>
    Numéro : <input type="text" name="numero" value="${unite.numero}" required/><br>
    Pièces : <input type="number" name="pieces" value="${unite.pieces}" required/><br>
    Superficie : <input type="text" name="superficie" value="${unite.superficie}" required/><br>
    Loyer : <input type="text" name="loyer" value="${unite.loyer}" required/><br>
    <button type="submit">Enregistrer</button>
</form>

</body>
</html>
