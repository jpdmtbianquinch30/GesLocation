<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Locataire</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- CSS custom -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">

    <style>
        /* Ajustements responsives personnalisés */
        .main-content {
            margin-left: 0;
            transition: margin-left 0.3s;
        }
        @media (min-width: 992px) {
            .main-content {
                margin-left: 250px; /* Largeur sidebar */
            }
        }
        .card h3.card-title {
            font-size: 1.8rem;
        }
        .section h2 {
            font-weight: 600;
        }
    </style>
</head>
<body class="bg-light">

<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<!-- Contenu principal -->
<div class="container-fluid main-content py-4">

    <!-- Dashboard Header -->
    <div class="dashboard-header mb-4">
        <h1 class="h3">Tableau de Bord Locataire</h1>
        <p class="text-muted">Bienvenue, ${sessionScope.userPrenom} ${sessionScope.userNom}</p>
    </div>

    <!-- Alertes -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Statistiques -->
    <div class="row g-3 mb-4">
        <div class="col-12 col-md-4">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">${contratsActifs}</h3>
                    <p class="card-text text-muted">Contrats Actifs</p>
                </div>
            </div>
        </div>
        <div class="col-12 col-md-4">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">${paiementsEnAttente}</h3>
                    <p class="card-text text-muted">Paiements en Attente</p>
                </div>
            </div>
        </div>
        <div class="col-12 col-md-4">
            <div class="card text-center shadow-sm h-100">
                <div class="card-body">
                    <h3 class="card-title">
                        <fmt:formatNumber value="${totalPaye}" type="currency"/>
                    </h3>
                    <p class="card-text text-muted">Total Payé</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Prochain paiement -->
    <c:if test="${not empty prochainPaiement}">
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Prochain Paiement</h5>
            </div>
            <div class="card-body">
                <div class="row g-2">
                    <div class="col-12 col-md-4">
                        <p><strong>Montant:</strong> <fmt:formatNumber value="${prochainPaiement.montant}" type="currency"/></p>
                    </div>
                    <div class="col-12 col-md-4">
                        <p><strong>Mois:</strong> ${prochainPaiement.moisCouvert}</p>
                    </div>
                    <div class="col-12 col-md-4">
                        <p><strong>Date limite:</strong> <fmt:formatDate value="${prochainPaiement.datePaiement}" pattern="dd/MM/yyyy"/></p>
                    </div>
                </div>
                <a href="${pageContext.request.contextPath}/locataire/paiements" class="btn btn-success mt-2">
                    Effectuer le Paiement
                </a>
            </div>
        </div>
    </c:if>

    <!-- Mes contrats actifs -->
    <div class="section mb-4">
        <h2 class="h5 mb-3">Mes Contrats Actifs</h2>
        <c:if test="${not empty contrats}">
            <div class="row g-3">
                <c:forEach var="contrat" items="${contrats}">
                    <c:if test="${contrat.etatContrat == 'ACTIF'}">
                        <div class="col-12 col-md-6">
                            <div class="card shadow-sm h-100">
                                <div class="card-header d-flex justify-content-between align-items-center">
                                    <h6 class="mb-0">Contrat #${contrat.id}</h6>
                                    <span class="badge bg-success">Actif</span>
                                </div>
                                <div class="card-body">
                                    <p><strong>Unité:</strong> ${contrat.unite.numeroUnite} - ${contrat.unite.immeuble.nom}</p>
                                    <p><strong>Loyer:</strong> <fmt:formatNumber value="${contrat.loyerMensuel}" type="currency"/> / mois</p>
                                    <p><strong>Période:</strong> du
                                        <fmt:formatDate value="${contrat.dateDebut}" pattern="dd/MM/yyyy"/> au
                                        <fmt:formatDate value="${contrat.dateFin}" pattern="dd/MM/yyyy"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty contrats}">
            <div class="alert alert-info">
                Vous n'avez aucun contrat actif.
            </div>
            <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-primary">
                Consulter les offres
            </a>
        </c:if>
    </div>

    <!-- Actions rapides -->
    <div class="section mb-4">
        <h2 class="h5 mb-3">Actions Rapides</h2>
        <div class="d-flex flex-wrap gap-2">
            <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-outline-primary">
                Consulter les Offres
            </a>
            <a href="${pageContext.request.contextPath}/locataire/paiements" class="btn btn-outline-secondary">
                Mes Paiements
            </a>
        </div>
    </div>

</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
