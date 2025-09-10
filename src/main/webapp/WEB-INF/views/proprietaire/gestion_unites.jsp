<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Unités</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Gestion des Unités</h1>
        <button class="btn btn-primary" onclick="toggleUniteForm()">+ Nouvelle Unité</button>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Filtre par immeuble -->
    <div class="filter-section">
        <form method="get" class="filter-form">
            <label for="immeubleId">Filtrer par immeuble:</label>
            <select id="immeubleId" name="immeubleId" onchange="this.form.submit()">
                <option value="">Tous les immeubles</option>
                <c:forEach var="immeuble" items="${immeubles}">
                    <option value="${immeuble.id}" ${selectedImmeubleId == immeuble.id ? 'selected' : ''}>
                            ${immeuble.nom}
                    </option>
                </c:forEach>
            </select>
        </form>
    </div>

    <!-- Formulaire de création/édition -->
    <div id="uniteForm" class="form-container" style="display: ${empty uniteToEdit ? 'none' : 'block'};">
        <h2>${empty uniteToEdit ? 'Créer' : 'Modifier'} une Unité</h2>
        <form action="${pageContext.request.contextPath}/proprietaire/unites" method="post">
            <input type="hidden" name="action" value="${empty uniteToEdit ? 'create' : 'update'}">
            <c:if test="${not empty uniteToEdit}">
                <input type="hidden" name="id" value="${uniteToEdit.id}">
            </c:if>

            <div class="form-group">
                <label for="numeroUnite">Numéro d'unité:</label>
                <input type="text" id="numeroUnite" name="numeroUnite" value="${uniteToEdit.numeroUnite}" required>
            </div>

            <div class="form-group">
                <label for="nombrePieces">Nombre de pièces:</label>
                <input type="number" id="nombrePieces" name="nombrePieces" value="${uniteToEdit.nombrePieces}" min="1" required>
            </div>

            <div class="form-group">
                <label for="superficie">Superficie (m²):</label>
                <input type="number" id="superficie" name="superficie" value="${uniteToEdit.superficie}" step="0.01" min="0" required>
            </div>

            <div class="form-group">
                <label for="loyerMensuel">Loyer mensuel (€):</label>
                <input type="number" id="loyerMensuel" name="loyerMensuel" value="${uniteToEdit.loyerMensuel}" step="0.01" min="0" required>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description">${uniteToEdit.description}</textarea>
            </div>

            <div class="form-group">
                <label for="immeubleId">Immeuble:</label>
                <select id="immeubleId" name="immeubleId" required>
                    <c:forEach var="immeuble" items="${immeubles}">
                        <option value="${immeuble.id}" ${uniteToEdit.immeuble.id == immeuble.id ? 'selected' : ''}>
                                ${immeuble.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${not empty uniteToEdit}">
                <div class="form-group">
                    <label class="checkbox-label">
                        <input type="checkbox" name="estDisponible" ${uniteToEdit.estDisponible ? 'checked' : ''}>
                        Disponible à la location
                    </label>
                </div>
            </c:if>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${empty uniteToEdit ? 'Créer' : 'Modifier'}</button>
                <button type="button" class="btn btn-secondary" onclick="toggleUniteForm()">Annuler</button>
            </div>
        </form>
    </div>

    <!-- Liste des unités -->
    <div class="table-container">
        <table class="data-table">
            <thead>
            <tr>
                <th>Numéro</th>
                <th>Immeuble</th>
                <th>Pièces</th>
                <th>Superficie</th>
                <th>Loyer</th>
                <th>Statut</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="unite" items="${unites}">
                <tr>
                    <td>${unite.numeroUnite}</td>
                    <td>${unite.immeuble.nom}</td>
                    <td>${unite.nombrePieces}</td>
                    <td>${unite.superficie} m²</td>
                    <td>${unite.loyerMensuel} €</td>
                    <td>
                                <span class="status-badge ${unite.estDisponible ? 'disponible' : 'occupee'}">
                                        ${unite.estDisponible ? 'Disponible' : 'Occupée'}
                                </span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/proprietaire/unites?action=edit&id=${unite.id}"
                           class="btn btn-sm btn-warning">Modifier</a>
                        <a href="${pageContext.request.contextPath}/proprietaire/unites?action=delete&id=${unite.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette unité ?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function toggleUniteForm() {
        const form = document.getElementById('uniteForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';

        // Réinitialiser le formulaire si on le cache
        if (form.style.display === 'none') {
            form.reset();
        }
    }
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>