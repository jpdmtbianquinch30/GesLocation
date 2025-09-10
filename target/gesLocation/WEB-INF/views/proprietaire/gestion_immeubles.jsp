<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Immeubles</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Gestion des Immeubles</h1>
        <button class="btn btn-primary" onclick="toggleImmeubleForm()">+ Nouvel Immeuble</button>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Formulaire de création/édition -->
    <div id="immeubleForm" class="form-container" style="display: ${empty immeubleToEdit ? 'none' : 'block'};">
        <h2>${empty immeubleToEdit ? 'Créer' : 'Modifier'} un Immeuble</h2>
        <form action="${pageContext.request.contextPath}/proprietaire/immeubles" method="post">
            <input type="hidden" name="action" value="${empty immeubleToEdit ? 'create' : 'update'}">
            <c:if test="${not empty immeubleToEdit}">
                <input type="hidden" name="id" value="${immeubleToEdit.id}">
            </c:if>

            <div class="form-group">
                <label for="nom">Nom de l'immeuble:</label>
                <input type="text" id="nom" name="nom" value="${immeubleToEdit.nom}" required>
            </div>

            <div class="form-group">
                <label for="adresse">Adresse:</label>
                <textarea id="adresse" name="adresse" required>${immeubleToEdit.adresse}</textarea>
            </div>

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description">${immeubleToEdit.description}</textarea>
            </div>

            <div class="form-group">
                <label for="equipements">Équipements (séparés par des virgules):</label>
                <input type="text" id="equipements" name="equipements" value="${immeubleToEdit.equipements}">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${empty immeubleToEdit ? 'Créer' : 'Modifier'}</button>
                <button type="button" class="btn btn-secondary" onclick="toggleImmeubleForm()">Annuler</button>
            </div>
        </form>
    </div>

    <!-- Liste des immeubles -->
    <div class="cards-grid">
        <c:forEach var="immeuble" items="${immeubles}">
            <div class="property-card">
                <div class="property-header">
                    <h3>${immeuble.nom}</h3>
                    <span class="property-badge">${immeuble.unites.size()} unités</span>
                </div>

                <div class="property-details">
                    <p><strong>Adresse:</strong> ${immeuble.adresse}</p>
                    <p><strong>Équipements:</strong> ${immeuble.equipements}</p>
                    <p class="property-description">${immeuble.description}</p>
                </div>

                <div class="property-actions">
                    <a href="${pageContext.request.contextPath}/proprietaire/immeubles?action=edit&id=${immeuble.id}"
                       class="btn btn-sm btn-warning">Modifier</a>
                    <a href="${pageContext.request.contextPath}/proprietaire/unites?immeubleId=${immeuble.id}"
                       class="btn btn-sm btn-info">Voir Unités</a>
                    <a href="${pageContext.request.contextPath}/proprietaire/immeubles?action=delete&id=${immeuble.id}"
                       class="btn btn-sm btn-danger"
                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet immeuble ?')">Supprimer</a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>
    function toggleImmeubleForm() {
        const form = document.getElementById('immeubleForm');
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