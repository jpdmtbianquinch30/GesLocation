<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tableau de Bord Admin</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">

    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #1cc88a;
            --info-color: #36b9cc;
            --warning-color: #f6c23e;
            --danger-color: #e74a3b;
            --light-color: #f8f9fc;
            --dark-color: #5a5c69;
        }

        .sidebar {
            min-height: 100vh;
            background: linear-gradient(180deg, var(--primary-color) 10%, #224abe 100%);
        }

        .stat-card {
            border-left: 5px solid;
            border-radius: 8px;
            transition: transform 0.3s;
        }

        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.1);
        }

        .stat-card.users {
            border-color: var(--primary-color);
        }

        .stat-card.proprietaires {
            border-color: var(--info-color);
        }

        .stat-card.locataires {
            border-color: var(--secondary-color);
        }

        .stat-card.immeubles {
            border-color: var(--warning-color);
        }

        .stat-card.contrats {
            border-color: var(--danger-color);
        }

        .stat-card.paiements {
            border-color: var(--dark-color);
        }

        .stat-icon {
            font-size: 2rem;
            opacity: 0.7;
        }

        .dashboard-header {
            background-color: var(--light-color);
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        .quick-actions {
            background-color: white;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            margin-top: 30px;
        }

        .action-buttons .btn {
            margin-right: 15px;
            margin-bottom: 10px;
            min-width: 200px;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 600;
        }

        .main-content {
            background-color: #f8f9fc;
            min-height: 100vh;
            padding: 20px;
        }

        @media (max-width: 768px) {
            .action-buttons .btn {
                min-width: 100%;
                margin-right: 0;
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

            <div class="dashboard-header mt-4">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h1 class="h3 mb-1 text-gray-800">Tableau de Bord Administrateur</h1>
                        <p class="mb-0">Bienvenue, ${sessionScope.userPrenom} ${sessionScope.userNom}</p>
                    </div>
                    <div>
                        <span id="datetime" class="text-dark"></span>
                    </div>
                </div>
            </div>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${errorMessage}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>

            <div class="row">
                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card users h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Utilisateurs Totaux</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalUsers}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-users stat-icon text-primary"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card proprietaires h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                        Propriétaires</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalProprietaires}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-user-tie stat-icon text-info"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card locataires h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                        Locataires</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalLocataires}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-user stat-icon text-success"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card immeubles h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                        Immeubles</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalImmeubles}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-building stat-icon text-warning"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card contrats h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                        Contrats Actifs</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalContratsActifs}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-file-contract stat-icon text-danger"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="card stat-card paiements h-100 shadow">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-dark text-uppercase mb-1">
                                        Paiements en Attente</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">${totalPaiementsEnAttente}</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-money-bill-wave stat-icon text-dark"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="quick-actions">
                <h2 class="h4 mb-4 text-gray-800">Actions Rapides</h2>
                <div class="action-buttons">
                    <a href="${pageContext.request.contextPath}/admin/utilisateurs" class="btn btn-primary">
                        <i class="fas fa-users me-2"></i>Gérer les Utilisateurs
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/rapports" class="btn btn-info text-white">
                        <i class="fas fa-chart-bar me-2"></i>Voir les Rapports
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/immeubles" class="btn btn-success">
                        <i class="fas fa-building me-2"></i>Gérer les Immeubles
                    </a>
                    <a href="${pageContext.request.contextPath}/admin/contrats" class="btn btn-warning">
                        <i class="fas fa-file-contract me-2"></i>Contrats
                    </a>
                </div>
            </div>
        </main>
    </div>
</div>

<!-- Bootstrap & JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Fonction pour afficher la date et l'heure actuelles
    function updateDateTime() {
        const now = new Date();
        const options = {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        };
        document.getElementById('datetime').textContent = now.toLocaleDateString('fr-FR', options);
    }

    // Mettre à jour toutes les secondes
    setInterval(updateDateTime, 1000);
    updateDateTime(); // Appel initial
</script>
</body>
</html>