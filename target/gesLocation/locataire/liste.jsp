<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestion.location.entities.Locataire" %>
<%@ page import="com.gestion.location.service.LocataireService" %>

<%
    LocataireService locataireService = new LocataireService();
    List<Locataire> locataires = locataireService.listerLocataires();
%>

<html>
<head>
    <title>Liste des Locataires</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<h2>Liste des Locataires</h2>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Téléphone</th>
    </tr>
    <%
        for(Locataire l : locataires){
    %>
    <tr>
        <td><%= l.getId() %></td>
        <td><%= l.getNom() %></td>
        <td><%= l.getPrenom() %></td>
        <td><%= l.getEmail() %></td>
        <td><%= l.getTelephone() %></td>
    </tr>
    <% } %>
</table>

<br>
<a href="formLocataire.jsp">Ajouter un nouveau locataire</a>
</body>
</html>
