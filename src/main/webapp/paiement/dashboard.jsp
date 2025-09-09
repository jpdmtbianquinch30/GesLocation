<%@ page import="com.gestion.location.service.ContratService" %>
<%@ page import="com.gestion.location.service.PaiementService" %>
<%@ page import="com.gestion.location.entities.Contrat" %>
<%@ page import="com.gestion.location.entities.Paiement" %>
<%@ page import="java.util.List" %>

<%
  ContratService contratService = new ContratService();
  PaiementService paiementService = new PaiementService();

  List<Contrat> contrats = contratService.listerContrats();
  List<Paiement> paiements = paiementService.listerPaiements();

  double totalPaiements = paiements.stream().mapToDouble(Paiement::getMontant).sum();
%>

<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
  <h2>Dashboard Contrats & Paiements</h2>

  <div class="row mt-4">
    <div class="col-md-6">
      <div class="card text-white bg-primary mb-3">
        <div class="card-header">Contrats</div>
        <div class="card-body">
          <h5 class="card-title">Nombre de contrats : <%= contrats.size() %></h5>
          <a href="<%= request.getContextPath() %>/contrats?action=dashboard" class="btn btn-light">Voir tous</a>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <div class="card text-white bg-success mb-3">
        <div class="card-header">Paiements</div>
        <div class="card-body">
          <h5 class="card-title">Montant total : <%= totalPaiements %> CFA</h5>
          <a href="<%= request.getContextPath() %>/paiement?action=dashboard" class="btn btn-light">Voir tous</a>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
