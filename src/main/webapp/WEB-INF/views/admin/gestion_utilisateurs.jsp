<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestion des Utilisateurs</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.5/css/dataTables.bootstrap5.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">

    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #1cc88a;
            --warning-color: #f6c23e;
            --danger-color: #e74a3b;
            --light-color: #f8f9fc;
        }

        .role-badge {
            padding: 0.35em 0.65em;
            font-size: 0.75em;
            font-weight: 700;
            border-radius: 0.25rem;
        }

        .role-badge.admin {
            background-color: var(--primary-color);
            color: white;
        }

        .role-badge.proprietaire {
            background-color: var(--secondary-color);
            color: white;
        }

        .role-badge.locataire {
            background-color: var(--warning-color);
            color: #000;
        }

        .page-header {
            background-color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .form-container {
            background-color: white;
            border-radius: 10px;
            padding: 25px;
            margin-bottom: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .table-container {
            background-color: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .btn-action {
            margin-right: 5px;
        }

        /* Animation pour l'affichage du formulaire */
        .form-visible {
            animation: fadeIn 0.5s;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Style pour la table */
        #usersTable tbody tr {
            transition: background-color 0.2s;
        }

        #usersTable tbody tr:hover {
            background-color: rgba(78, 115, 223, 0.05);
        }

        /* Responsive adjustments */
        @media (max-width: 768px) {
            .btn-action {
                margin-bottom: 5px;
                width: 100%;
            }

            .action-buttons {
                display: flex;
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-3 col-lg-2 sidebar text-white p-0">
            <%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>
        </div>

        <!-- Main content -->
        <main class="col-md-9 col-lg-10 ms-sm-auto px-md-4 main-content">
            <%@ include file="/WEB-INF/views/shared/header.jsp" %>

            <div class="page-header d-flex justify-content-between align-items-center mt-4">
                <div>
                    <h1 class="h3 mb-1 text-gray-800">Gestion des Utilisateurs</h1>
                    <p class="mb-0">Administrez les utilisateurs de votre application</p>
                </div>
                <button class="btn btn-primary" onclick="toggleUserForm()">
                    <i class="fas fa-plus me-2"></i>Nouvel Utilisateur
                </button>
            </div>

            <c:if test="${not empty successMessage}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <i class="fas fa-check-circle me-2"></i>${successMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <!-- Formulaire de création/édition -->
            <div id="userForm" class="form-container form-visible" style="display: ${empty userToEdit ? 'none' : 'block'};">
                <h2 class="h4 mb-4 text-gray-800">
                    <i class="fas ${empty userToEdit ? 'fa-user-plus' : 'fa-user-edit'} me-2"></i>
                    ${empty userToEdit ? 'Créer' : 'Modifier'} un Utilisateur
                </h2>
                <form action="${pageContext.request.contextPath}/admin/utilisateurs" method="post">
                    <input type="hidden" name="action" value="${empty userToEdit ? 'create' : 'update'}">
                    <c:if test="${not empty userToEdit}">
                        <input type="hidden" name="id" value="${userToEdit.id}">
                    </c:if>

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="nom" class="form-label">Nom</label>
                            <input type="text" class="form-control" id="nom" name="nom" value="${userToEdit.nom}" required>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label for="prenom" class="form-label">Prénom</label>
                            <input type="text" class="form-control" id="prenom" name="prenom" value="${userToEdit.prenom}" required>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" value="${userToEdit.email}" required>
                    </div>

                    <c:if test="${empty userToEdit}">
                        <div class="mb-3">
                            <label for="password" class="form-label">Mot de passe</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                            <div class="form-text">Le mot de passe doit contenir au moins 8 caractères.</div>
                        </div>
                    </c:if>

                    <div class="mb-4">
                        <label for="role" class="form-label">Rôle</label>
                        <select class="form-select" id="role" name="role" required>
                            <option value="ADMIN" ${userToEdit.role == 'ADMIN' ? 'selected' : ''}>Administrateur</option>
                            <option value="PROPRIETAIRE" ${userToEdit.role == 'PROPRIETAIRE' ? 'selected' : ''}>Propriétaire</option>
                            <option value="LOCATAIRE" ${userToEdit.role == 'LOCATAIRE' ? 'selected' : ''}>Locataire</option>
                        </select>
                    </div>

                    <div class="d-flex justify-content-end">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="fas ${empty userToEdit ? 'fa-save' : 'fa-edit'} me-2"></i>
                            ${empty userToEdit ? 'Créer' : 'Modifier'}
                        </button>
                        <button type="button" class="btn btn-secondary" onclick="toggleUserForm()">
                            <i class="fas fa-times me-2"></i>Annuler
                        </button>
                    </div>
                </form>
            </div>

            <!-- Liste des utilisateurs -->
            <div class="table-container">
                <h2 class="h4 mb-4 text-gray-800">
                    <i class="fas fa-list me-2"></i>Liste des Utilisateurs
                </h2>

                <table id="usersTable" class="table table-striped table-hover">
                    <thead class="table-light">
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
                                <div class="d-flex action-buttons">
                                    <a href="${pageContext.request.contextPath}/admin/utilisateurs?action=edit&id=${user.id}"
                                       class="btn btn-sm btn-warning btn-action">
                                        <i class="fas fa-edit me-1"></i>Modifier
                                    </a>
                                    <a href="${pageContext.request.contextPath}/admin/utilisateurs?action=delete&id=${user.id}"
                                       class="btn btn-sm btn-danger btn-action"
                                       onclick="return confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')">
                                        <i class="fas fa-trash me-1"></i>Supprimer
                                    </a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
    </div>
</div>

<!-- Bootstrap & JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.7.0.min.js"></script>
<!-- DataTables -->
<script src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.13.5/js/dataTables.bootstrap5.min.js"></script>

<script>
    // Initialisation de DataTables
    $(document).ready(function() {
        $('#usersTable').DataTable({
            language: {
                url: '//cdn.datatables.net/plug-ins/1.13.5/i18n/fr-FR.json'
            },
            pageLength: 10,
            lengthMenu: [5, 10, 25, 50],
            responsive: true
        });
    });

    function toggleUserForm() {
        const form = document.getElementById('userForm');
        const isVisible = form.style.display === 'block';

        if (isVisible) {
            form.style.display = 'none';
            // Réinitialiser le formulaire si on le cache
            form.reset();

            // Si on était en mode édition, rediriger pour nettoyer les paramètres
            if (window.location.href.includes('action=edit')) {
                window.location.href = '${pageContext.request.contextPath}/admin/utilisateurs';
            }
        } else {
            form.style.display = 'block';
            form.scrollIntoView({ behavior: 'smooth' });
        }
    }

    // Si le formulaire doit être affiché au chargement (mode édition)
    <c:if test="${not empty userToEdit}">
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('userForm').scrollIntoView({ behavior: 'smooth' });
    });
    </c:if>
</script>
</body>
</html>