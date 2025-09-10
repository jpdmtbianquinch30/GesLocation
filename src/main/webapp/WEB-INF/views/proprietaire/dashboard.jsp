<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord Propriétaire</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="dashboard-header">
        <h1>Tableau de Bord Propriétaire</h1>
        <p>Bienvenue, ${sessionScope.userPrenom} ${sessionScope.userNom}</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon immeubles"></div>
            <div class="stat-info">
                <h3>${totalImmeubles}</h3>
                <p>Immeubles</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon contrats"></div>
            <div class="stat-info">
                <h3>${totalContratsActifs}</h3>
                <p>Contrats Actifs</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon paiements"></div>
            <div class="stat-info">
                <h3>${totalPaiementsEnAttente}</h3>
                <p>Paiements en Attente</p>
            </div>
        </div>

        <div class="stat-card financial">
            <div class="stat-icon revenue"></div>
            <div class="stat-info">
                <h3>${revenusMensuels} €</h3>
                <p>Revenus ce mois</p>
            </div>
        </div>
    </div>

    <div class="quick-actions">
        <h2>Actions Rapides</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/proprietaire/immeubles" class="btn btn-primary">
                Gérer mes Immeubles
            </a>
            <a href="${pageContext.request.contextPath}/proprietaire/unites" class="btn btn-secondary">
                Gérer les Unités
            </a>
            <a href="${pageContext.request.contextPath}/proprietaire/contrats" class="btn btn-success">
                Voir les Contrats
            </a>
        </div>
    </div>

    <div class="recent-activity">
        <h2>Activité Récente</h2>
        <div class="activity-list">
            <div class="activity-item">
                <div class="activity-icon nouveau"></div>
                <div class="activity-content">
                    <p>Nouvelle demande de location pour l'appartement B12</p>
                    <span class="activity-time">Il y a 2 heures</span>
                </div>
            </div>
            <div class="activity-item">
                <div class="activity-icon paiement"></div>
                <div class="activity-content">
                    <p>Paiement reçu de M. Dupont pour le mois de Novembre</p>
                    <span class="activity-time">Il y a 1 jour</span>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>