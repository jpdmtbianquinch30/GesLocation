<%@ page import="java.util.List" %>
<%@ page import="com.gestion.location.entities.Paiement" %>

<h3>Dashboard Paiements</h3>
<p>Nombre de paiements en retard : <strong>${retards}</strong></p>

<a href="paiements?action=add" class="btn btn-success mb-3">Ajouter un Paiement</a>

<table class="table table-striped">
  <thead>
  <tr>
    <th>ID</th>
    <th>Montant</th>
    <th>Contrat</th>
    <th>Date</th>
    <th>Payé</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="p" items="${paiements}">
    <tr>
      <td>${p.id}</td>
      <td>${p.montant}</td>
      <td>${p.contrat.id}</td>
      <td>${p.date}</td>
      <td>${p.paye ? "Oui" : "Non"}</td>
      <td>
        <a href="paiements?action=edit&id=${p.id}" class="btn btn-warning btn-sm">Modifier</a>
        <a href="paiements?action=delete&id=${p.id}" class="btn btn-danger btn-sm">Supprimer</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
