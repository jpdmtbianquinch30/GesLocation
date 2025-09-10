<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord Locataire</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="dashboard-header">
        <h1>Tableau de Bord Locataire</h1>
        <p>Bienvenue, ${sessionScope.userPrenom} ${sessionScope.userNom}</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon contrats"></div>
            <div class="stat-info">
                <h3>${contratsActifs}</h3>
                <p>Contrats Actifs</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon paiements"></div>
            <div class="stat-info">
                <h3>${paiementsEnAttente}</h3>
                <p>Paiements en Attente</p>
            </div>
        </div>

        <div class="stat-card financial">
            <div class="stat-icon revenue"></div>
            <div class="stat-info">
                <h3><fmt:formatNumber value="${totalPaye}" type="currency"/></h3>
                <p>Total Payé</p>
            </div>
        </div>
    </div>

    <!-- Prochain paiement -->
    <c:if test="${not empty prochainPaiement}">
        <div class="next-payment-card">
            <h3>Prochain Paiement</h3>
            <div class="payment-details">
                <p><strong>Montant:</strong> <fmt:formatNumber value="${prochainPaiement.montant}" type="currency"/></p>
                <p><strong>Pour le mois:</strong> ${prochainPaiement.moisCouvert}</p>
                <p><strong>Date limite:</strong> <fmt:formatDate value="${prochainPaiement.datePaiement}" pattern="dd/MM/yyyy"/></p>
            </div>
            <a href="${pageContext.request.contextPath}/locataire/paiements" class="btn btn-primary">
                Effectuer le Paiement
            </a>
        </div>
    </c:if>

    <!-- Mes contrats actifs -->
    <div class="section">
        <h2>Mes Contrats Actifs</h2>
        <c:if test="${not empty contrats}">
            <div class="contrats-list">
                <c:forEach var="contrat" items="${contrats}">
                    <c:if test="${contrat.etatContrat == 'ACTIF'}">
                        <div class="contrat-card">
                            <div class="contrat-header">
                                <h4>Contrat #${contrat.id}</h4>
                                <span class="status-badge actif">Actif</span>
                            </div>
                            <div class="contrat-details">
                                <p><strong>Unité:</strong> ${contrat.unite.numeroUnite} - ${contrat.unite.immeuble.nom}</p>
                                <p><strong>Loyer:</strong> <fmt:formatNumber value="${contrat.loyerMensuel}" type="currency"/>/mois</p>
                                <p><strong>Période:</strong> du <fmt:formatDate value="${contrat.dateDebut}" pattern="dd/MM/yyyy"/>
                                    au <fmt:formatDate value="${contrat.dateFin}" pattern="dd/MM/yyyy"/></p>
                            </div>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </c:if>
        <c:if test="${empty contrats}">
            <p class="no-data">Vous n'avez aucun contrat actif.</p>
            <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-primary">
                Consulter les offres
            </a>
        </c:if>
    </div>

    <!-- Actions rapides -->
    <div class="quick-actions">
        <h2>Actions Rapides</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-primary">
                Consulter les Offres
            </a>
            <a href="${pageContext.request.contextPath}/locataire/paiements" class="btn btn-secondary">
                Mes Paiements
            </a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>