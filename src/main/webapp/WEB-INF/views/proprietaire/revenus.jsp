<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Mes Revenus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="dashboard-container">
    <%@ include file="../shared/header.jsp" %>

    <div class="main-content">
        <%@ include file="../shared/sidebar.jsp" %>

        <div class="content">
            <h1>Mes Revenus</h1>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <!-- Statistiques principales -->
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Revenu Mensuel</h3>
                    <p class="stat-number"><fmt:formatNumber value="${revenuMensuel}" type="currency"/></p>
                </div>
                <div class="stat-card">
                    <h3>Revenu Annuel</h3>
                    <p class="stat-number"><fmt:formatNumber value="${revenuAnnuel}" type="currency"/></p>
                </div>
                <div class="stat-card">
                    <h3>Revenu Total</h3>
                    <p class="stat-number"><fmt:formatNumber value="${revenuTotal}" type="currency"/></p>
                </div>
                <div class="stat-card">
                    <h3>En Attente</h3>
                    <p class="stat-number"><fmt:formatNumber value="${totalEnAttente}" type="currency"/></p>
                </div>
            </div>

            <!-- Évolution des revenus -->
            <div class="chart-container">
                <h2>Évolution des Revenus (6 derniers mois)</h2>

                <c:choose>
                    <c:when test="${not empty evolutionRevenus}">
                        <div class="revenue-chart">
                            <c:forEach items="${evolutionRevenus}" var="revenu">
                                <div class="chart-bar">
                                    <div class="bar-label">${revenu.key}</div>
                                    <div class="bar-container">
                                        <div class="bar-fill" style="width: ${revenu.value > 0 ? (revenu.value / revenuMensuel * 100) : 0}%">
                                            <span class="bar-value"><fmt:formatNumber value="${revenu.value}" type="currency"/></span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            Aucune donnée de revenus disponible pour les 6 derniers mois.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Détails par propriété -->
            <div class="property-revenues">
                <h2>Revenus par Propriété</h2>
                <div class="alert alert-info">
                    Cette fonctionnalité sera bientôt disponible.
                </div>
            </div>
        </div>
    </div>

    <%@ include file="../shared/footer.jsp" %>
</div>
</body>
</html>