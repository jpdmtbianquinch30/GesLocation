<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Contrats</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <h1>Contrats de Location</h1>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Filtre par statut -->
    <form method="get" class="filter-form">
        <label for="statut">Filtrer par statut:</label>
        <select id="statut" name="statut" onchange="this.form.submit()">
            <option value="">Tous</option>
            <option value="EN_ATTENTE" ${param.statut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
            <option value="ACTIF" ${param.statut == 'ACTIF' ? 'selected' : ''}>Actif</option>
            <option value="RESILIE" ${param.statut == 'RESILIE' ? 'selected' : ''}>Résilié</option>
            <option value="TERMINE" ${param.statut == 'TERMINE' ? 'selected' : ''}>Terminé</option>
        </select>
    </form>

    <!-- Tableau des contrats -->
    <c:choose>
        <c:when test="${not empty contrats}">
            <table class="data-table">
                <thead>
                <tr>
                    <th>Référence</th>
                    <th>Locataire</th>
                    <th>Unité</th>
                    <th>Début</th>
                    <th>Fin</th>
                    <th>Loyer</th>
                    <th>Statut</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="contrat" items="${contrats}">
                    <tr>
                        <td>#${contrat.id}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty contrat.locataire and not empty contrat.locataire.utilisateur}">
                                    ${contrat.locataire.utilisateur.prenom} ${contrat.locataire.utilisateur.nom}
                                </c:when>
                                <c:otherwise>
                                    Locataire inconnu
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty contrat.unite}">
                                    ${contrat.unite.numeroUnite} -
                                    <c:if test="${not empty contrat.unite.immeuble}">
                                        ${contrat.unite.immeuble.nom}
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    Unité inconnue
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${not empty contrat.dateDebut}">
                                <fmt:formatDate value="${contrat.dateDebut}" pattern="dd/MM/yyyy"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty contrat.dateFin}">
                                <fmt:formatDate value="${contrat.dateFin}" pattern="dd/MM/yyyy"/>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${not empty contrat.loyerMensuel}">
                                <fmt:formatNumber value="${contrat.loyerMensuel}" type="currency" currencyCode="XOF"/>
                            </c:if>
                        </td>
                        <td>
                                <span class="status-badge ${contrat.etatContrat.toLowerCase()}">
                                        ${contrat.etatContrat}
                                </span>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/proprietaire/contrats?action=view&id=${contrat.id}"
                               class="btn btn-sm btn-info">Voir</a>
                            <c:if test="${contrat.etatContrat == 'ACTIF'}">
                                <a href="${pageContext.request.contextPath}/proprietaire/contrats?action=resilier&id=${contrat.id}"
                                   class="btn btn-sm btn-warning"
                                   onclick="return confirm('Voulez-vous résilier ce contrat ?')">Résilier</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:when>
        <c:otherwise>
            <div class="no-data">
                <p>Aucun contrat trouvé.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>