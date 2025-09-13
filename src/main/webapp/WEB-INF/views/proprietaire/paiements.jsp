<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Paiements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<div class="dashboard-container">
    <%@ include file="../shared/header.jsp" %>

    <div class="main-content">
        <%@ include file="../shared/sidebar.jsp" %>

        <div class="content">
            <h1>Gestion des Paiements</h1>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success">${successMessage}</div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-error">${errorMessage}</div>
            </c:if>

            <!-- Statistiques -->
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Total des Paiements</h3>
                    <p class="stat-number"><fmt:formatNumber value="${totalPaiements}" type="currency"/></p>
                </div>
                <div class="stat-card">
                    <h3>Paiements Validés</h3>
                    <p class="stat-number"><fmt:formatNumber value="${totalValides}" type="currency"/></p>
                </div>
                <div class="stat-card">
                    <h3>En Attente</h3>
                    <p class="stat-number"><fmt:formatNumber value="${totalEnAttente}" type="currency"/></p>
                </div>
            </div>

            <!-- Liste des paiements -->
            <div class="table-container">
                <h2>Liste des Paiements</h2>

                <c:choose>
                    <c:when test="${not empty paiements}">
                        <table class="data-table">
                            <thead>
                            <tr>
                                <th>Locataire</th>
                                <th>Unité</th>
                                <th>Montant</th>
                                <th>Date</th>
                                <th>Mois Couvert</th>
                                <th>Statut</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${paiements}" var="paiement">
                                <tr>
                                    <td>${paiement.locataire.prenom} ${paiement.locataire.nom}</td>
                                    <td>${paiement.contrat.unite.numeroUnite}</td>
                                    <td><fmt:formatNumber value="${paiement.montant}" type="currency"/></td>
                                    <td><fmt:formatDate value="${paiement.datePaiement}" pattern="dd/MM/yyyy"/></td>
                                    <td>${paiement.moisCouvert}</td>
                                    <td>
                                                <span class="statut-badge statut-${paiement.statutPaiement.toLowerCase()}">
                                                        ${paiement.statutPaiement}
                                                </span>
                                    </td>
                                    <td>
                                        <c:if test="${paiement.statutPaiement == 'EN_ATTENTE'}">
                                            <form action="${pageContext.request.contextPath}/proprietaire/paiements" method="post" style="display: inline;">
                                                <input type="hidden" name="paiementId" value="${paiement.id}">
                                                <input type="hidden" name="action" value="valider">
                                                <button type="submit" class="btn-success">Valider</button>
                                            </form>
                                            <form action="${pageContext.request.contextPath}/proprietaire/paiements" method="post" style="display: inline;">
                                                <input type="hidden" name="paiementId" value="${paiement.id}">
                                                <input type="hidden" name="action" value="refuser">
                                                <button type="submit" class="btn-danger">Refuser</button>
                                            </form>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            Aucun paiement trouvé pour vos propriétés.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <%@ include file="../shared/footer.jsp" %>
</div>
</body>
</html>