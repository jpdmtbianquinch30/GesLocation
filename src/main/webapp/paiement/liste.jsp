<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.gestion.location.entities.Paiement" %>
<%@ page import="com.gestion.location.service.PaiementService" %>

<%
    PaiementService paiementService = new PaiementService();
    List<Paiement> paiements = paiementService.listerPaiements();
%>

<html>
<head>
    <title>Liste des Paiements</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<h2>Liste des Paiements</h2>
<table border="1" cellpadding="5" cellspacing="0">
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Mode</th>
        <th>Statut</th>
        <th>Date</th>
    </tr>
    <%
        for(Paiement p : paiements){
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getMontant() %></td>
        <td><%= p.getMode() %></td>
        <td><%= p.getStatut() %></td>
        <td><%= p.getDatePaiement() %></td>
    </tr>
    <% } %>
</table>

<br>
<a href="formPaiement.jsp">Effectuer un nouveau paiement</a>
</body>
</html>
