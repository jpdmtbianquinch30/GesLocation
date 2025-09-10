<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tableau de Bord Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="dashboard-header">
        <h1>Tableau de Bord Administrateur</h1>
        <p>Bienvenue, ${sessionScope.userPrenom} ${sessionScope.userNom}</p>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="stats-grid">
        <div class="stat-card">
            <div class="stat-icon users"></div>
            <div class="stat-info">
                <h3>${totalUsers}</h3>
                <p>Utilisateurs Totaux</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon proprietaires"></div>
            <div class="stat-info">
                <h3>${totalProprietaires}</h3>
                <p>Propriétaires</p>
            </div>
        </div>

        <div class="stat-card">
            <div class="stat-icon locataires"></div>
            <div class="stat-info">
                <h3>${totalLocataires}</h3>
                <p>Locataires</p>
            </div>
        </div>

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
    </div>

    <div class="quick-actions">
        <h2>Actions Rapides</h2>
        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/admin/utilisateurs" class="btn btn-primary">
                Gérer les Utilisateurs
            </a>
            <a href="${pageContext.request.contextPath}/admin/rapports" class="btn btn-secondary">
                Voir les Rapports
            </a>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>