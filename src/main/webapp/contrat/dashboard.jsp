<%@ page import="java.util.List" %>
<%@ page import="com.gestion.location.entities.Contrat" %>

<h3>Dashboard Contrats</h3>
<p>Nombre de contrats en retard : <strong>${retards}</strong></p>
<p>Chiffre d’affaires total : <strong>${chiffre} FCFA</strong></p>

<a href="contrats?action=add" class="btn btn-success mb-3">Ajouter un Contrat</a>

<table class="table table-striped">
    <thead>
    <tr>
        <th>ID</th>
        <th>Montant</th>
        <th>Immeuble</th>
        <th>Locataire</th>
        <th>Payé</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="c" items="${contrats}">
        <tr>
            <td>${c.id}</td>
            <td>${c.montant}</td>
            <td>${c.immeuble.nom}</td>
            <td>${c.locataire.nom}</td>
            <td>${c.paye ? "Oui" : "Non"}</td>
            <td>
                <a href="contrats?action=edit&id=${c.id}" class="btn btn-warning btn-sm">Modifier</a>
                <a href="contrats?action=delete&id=${c.id}" class="btn btn-danger btn-sm">Supprimer</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

