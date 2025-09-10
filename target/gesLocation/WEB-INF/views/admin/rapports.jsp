<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Rapports et Statistiques</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Rapports et Statistiques</h1>

        <!-- Sélecteur de période -->
        <form method="get" class="period-selector">
            <label for="periode">Période:</label>
            <select id="periode" name="periode" onchange="this.form.submit()">
                <c:forEach var="entry" items="${periodes}">
                    <option value="${entry.key}" ${periode == entry.key ? 'selected' : ''}>${entry.value}</option>
                </c:forEach>
            </select>
        </form>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Statistiques générales -->
    <div class="report-section">
        <h2>Statistiques Générales</h2>
        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-icon immeubles"></div>
                <div class="stat-info">
                    <h3>${rapports.totalImmeubles}</h3>
                    <p>Immeubles</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon proprietaires"></div>
                <div class="stat-info">
                    <h3>${rapports.totalProprietaires}</h3>
                    <p>Propriétaires</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon locataires"></div>
                <div class="stat-info">
                    <h3>${rapports.totalLocataires}</h3>
                    <p>Locataires</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon contrats"></div>
                <div class="stat-info">
                    <h3>${rapports.contratsActifs}</h3>
                    <p>Contrats Actifs</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Statistiques financières -->
    <div class="report-section">
        <h2>Statistiques Financières</h2>
        <div class="stats-grid">
            <div class="stat-card financial">
                <div class="stat-icon revenue"></div>
                <div class="stat-info">
                    <h3>${rapports.revenusMensuels} €</h3>
                    <p>Revenus ${periode}</p>
                </div>
            </div>

            <div class="stat-card">
                <div class="stat-icon paiements-valides"></div>
                <div class="stat-info">
                    <h3>${rapports.paiementsValides}</h3>
                    <p>Paiements Validés</p>
                </div>
            </div>

            <div class="stat-card warning">
                <div class="stat-icon paiements-attente"></div>
                <div class="stat-info">
                    <h3>${rapports.paiementsEnAttente}</h3>
                    <p>Paiements en Attente</p>
                </div>
            </div>

            <div class="stat-card danger">
                <div class="stat-icon paiements-retard"></div>
                <div class="stat-info">
                    <h3>${rapports.paiementsEnRetard}</h3>
                    <p>Paiements en Retard</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Contrats à venir -->
    <div class="report-section">
        <h2>Contrats Expirant Prochainement</h2>
        <div class="info-box">
            <div class="info-icon warning"></div>
            <div class="info-content">
                <h3>${rapports.contratsExpirant} contrats</h3>
                <p>expirent dans les 30 prochains jours</p>
            </div>
        </div>
    </div>

    <!-- Actions -->
    <div class="report-actions">
        <h2>Export des Données</h2>
        <div class="action-buttons">
            <button class="btn btn-primary" onclick="exportPDF()">Exporter en PDF</button>
            <button class="btn btn-secondary" onclick="exportExcel()">Exporter en Excel</button>
        </div>
    </div>
</div>

<script>
    function exportPDF() {
        alert('Fonctionnalité d\'export PDF à implémenter');
    }

    function exportExcel() {
        alert('Fonctionnalité d\'export Excel à implémenter');
    }
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>