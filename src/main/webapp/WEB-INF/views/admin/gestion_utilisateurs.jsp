<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Gestion des Utilisateurs</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Gestion des Utilisateurs</h1>
        <button class="btn btn-primary" onclick="toggleUserForm()">+ Nouvel Utilisateur</button>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Formulaire de création/édition -->
    <div id="userForm" class="form-container" style="display: ${empty userToEdit ? 'none' : 'block'};">
        <h2>${empty userToEdit ? 'Créer' : 'Modifier'} un Utilisateur</h2>
        <form action="${pageContext.request.contextPath}/admin/utilisateurs" method="post">
            <input type="hidden" name="action" value="${empty userToEdit ? 'create' : 'update'}">
            <c:if test="${not empty userToEdit}">
                <input type="hidden" name="id" value="${userToEdit.id}">
            </c:if>

            <div class="form-group">
                <label for="nom">Nom:</label>
                <input type="text" id="nom" name="nom" value="${userToEdit.nom}" required>
            </div>

            <div class="form-group">
                <label for="prenom">Prénom:</label>
                <input type="text" id="prenom" name="prenom" value="${userToEdit.prenom}" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${userToEdit.email}" required>
            </div>

            <c:if test="${empty userToEdit}">
                <div class="form-group">
                    <label for="password">Mot de passe:</label>
                    <input type="password" id="password" name="password" required>
                </div>
            </c:if>

            <div class="form-group">
                <label for="role">Rôle:</label>
                <select id="role" name="role" required>
                    <option value="ADMIN" ${userToEdit.role == 'ADMIN' ? 'selected' : ''}>Administrateur</option>
                    <option value="PROPRIETAIRE" ${userToEdit.role == 'PROPRIETAIRE' ? 'selected' : ''}>Propriétaire</option>
                    <option value="LOCATAIRE" ${userToEdit.role == 'LOCATAIRE' ? 'selected' : ''}>Locataire</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary">${empty userToEdit ? 'Créer' : 'Modifier'}</button>
                <button type="button" class="btn btn-secondary" onclick="toggleUserForm()">Annuler</button>
            </div>
        </form>
    </div>

    <!-- Liste des utilisateurs -->
    <div class="table-container">
        <table class="data-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Email</th>
                <th>Rôle</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.nom}</td>
                    <td>${user.prenom}</td>
                    <td>${user.email}</td>
                    <td>
                        <span class="role-badge ${user.role.toLowerCase()}">${user.role}</span>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/utilisateurs?action=edit&id=${user.id}"
                           class="btn btn-sm btn-warning">Modifier</a>
                        <a href="${pageContext.request.contextPath}/admin/utilisateurs?action=delete&id=${user.id}"
                           class="btn btn-sm btn-danger"
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')">Supprimer</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script>
    function toggleUserForm() {
        const form = document.getElementById('userForm');
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