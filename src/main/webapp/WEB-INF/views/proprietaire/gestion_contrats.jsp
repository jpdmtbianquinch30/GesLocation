<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Contrats</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Gestion des Contrats de Location</h1>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Filtre par statut -->
    <div class="filter-section">
        <form method="get" class="filter-form">
            <label for="statut">Filtrer par statut:</label>
            <select id="statut" name="statut" onchange="this.form.submit()">
                <option value="">Tous les statuts</option>
                <option value="EN_ATTENTE" ${selectedStatut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
                <option value="ACTIF" ${selectedStatut == 'ACTIF' ? 'selected' : ''}>Actif</option>
                <option value="RESILIE" ${selectedStatut == 'RESILIE' ? 'selected' : ''}>Résilié</option>
                <option value="TERMINE" ${selectedStatut == 'TERMINE' ? 'selected' : ''}>Terminé</option>
            </select>
        </form>
    </div>

    <!-- Détails du contrat (modal) -->
    <c:if test="${not empty contratDetails}">
        <div class="modal-overlay" id="contratModal">
            <div class="modal-content">
                <div class="modal-header">
                    <h2>Détails du Contrat</h2>
                    <button class="modal-close" onclick="closeModal()">&times;</button>
                </div>
                <div class="modal-body">
                    <div class="contract-details">
                        <h3>Informations du Contrat</h3>
                        <p><strong>Référence:</strong> #${contratDetails.id}</p>
                        <p><strong>Statut:</strong> <span class="status-badge ${contratDetails.etatContrat.toLowerCase()}">${contratDetails.etatContrat}</span></p>
                        <p><strong>Période:</strong> du <fmt:formatDate value="${contratDetails.dateDebut}" pattern="dd/MM/yyyy"/> au <fmt:formatDate value="${contratDetails.dateFin}" pattern="dd/MM/yyyy"/></p>
                        <p><strong>Loyer mensuel:</strong> ${contratDetails.loyerMensuel} €</p>
                        <p><strong>Caution:</strong> ${contratDetails.caution} €</p>

                        <h3>Locataire</h3>
                        <p><strong>Nom:</strong> ${contratDetails.locataire.nom} ${contratDetails.locataire.prenom}</p>
                        <p><strong>Email:</strong> ${contratDetails.locataire.email}</p>
                        <p><strong>Téléphone:</strong> ${contratDetails.locataire.numeroTelephone}</p>

                        <h3>Unité</h3>
                        <p><strong>Immeuble:</strong> ${contratDetails.unite.immeuble.nom}</p>
                        <p><strong>Unité:</strong> ${contratDetails.unite.numeroUnite}</p>
                        <p><strong>Superficie:</strong> ${contratDetails.unite.superficie} m²</p>
                    </div>
                </div>
                <div class="modal-actions">
                    <c:if test="${contratDetails.etatContrat == 'EN_ATTENTE'}">
                        <form action="${pageContext.request.contextPath}/proprietaire/contrats" method="post" style="display: inline;">
                            <input type="hidden" name="action" value="changerStatut">
                            <input type="hidden" name="id" value="${contratDetails.id}">
                            <input type="hidden" name="nouveauStatut" value="ACTIF">
                            <button type="submit" class="btn btn-success">Accepter</button>
                        </form>
                        <form action="${pageContext.request.contextPath}/proprietaire/contrats" method="post" style="display: inline;">
                            <input type="hidden" name="action" value="changerStatut">
                            <input type="hidden" name="id" value="${contratDetails.id}">
                            <input type="hidden" name="nouveauStatut" value="RESILIE">
                            <button type="submit" class="btn btn-danger">Refuser</button>
                        </form>
                    </c:if>
                    <c:if test="${contratDetails.etatContrat == 'ACTIF'}">
                        <a href="${pageContext.request.contextPath}/proprietaire/contrats?action=resilier&id=${contratDetails.id}"
                           class="btn btn-warning"
                           onclick="return confirm('Êtes-vous sûr de vouloir résilier ce contrat ?')">Résilier</a>
                    </c:if>
                    <button class="btn btn-secondary" onclick="closeModal()">Fermer</button>
                </div>
            </div>
        </div>
        <script>
            document.getElementById('contratModal').style.display = 'flex';
        </script>
    </c:if>

    <!-- Liste des contrats -->
    <div class="table-container">
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
                    <td>${contrat.locataire.nom} ${contrat.locataire.prenom}</td>
                    <td>${contrat.unite.numeroUnite} - ${contrat.unite.immeuble.nom}</td>
                    <td><fmt:formatDate value="${contrat.dateDebut}" pattern="dd/MM/yyyy"/></td>
                    <td><fmt:formatDate value="${contrat.dateFin}" pattern="dd/MM/yyyy"/></td>
                    <td>${contrat.loyerMensuel} €</td>
                    <td>
                        <span class="status-badge ${contrat.etatContrat.toLowerCase()}">${contrat.etatContrat}</span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/proprietaire/contrats?action=view&id=${contrat.id}"
                           class="btn btn-sm btn-info">Voir</a>
                        <c:if test="${contrat.etatContrat == 'ACTIF'}">
                            <a href="${pageContext.request.contextPath}/proprietaire/contrats?action=resilier&id=${contrat.id}"
                               class="btn btn-sm btn-warning"
                               onclick="return confirm('Êtes-vous sûr de vouloir résilier ce contrat ?')">Résilier</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function closeModal() {
        document.getElementById('contratModal').style.display = 'none';
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    // Fermer la modal en cliquant à l'extérieur
    document.addEventListener('click', function(event) {
        const modal = document.getElementById('contratModal');
        if (event.target === modal) {
            closeModal();
        }
    });
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>