<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ImmoManager - Gestion des Locations d'Immeubles</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #2c3e50;
            --secondary-color: #3498db;
            --accent-color: #e74c3c;
            --light-color: #ecf0f1;
            --dark-color: #34495e;
        }

        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .navbar {
            background-color: var(--primary-color);
        }

        .sidebar {
            background-color: var(--dark-color);
            color: white;
            height: calc(100vh - 56px);
            position: fixed;
            width: 250px;
            padding-top: 20px;
        }

        .sidebar .nav-link {
            color: rgba(255, 255, 255, 0.8);
            padding: 12px 20px;
            margin: 4px 0;
            border-radius: 4px;
        }

        .sidebar .nav-link:hover {
            background-color: rgba(255, 255, 255, 0.1);
            color: white;
        }

        .sidebar .nav-link.active {
            background-color: var(--secondary-color);
            color: white;
        }

        .sidebar .nav-link i {
            margin-right: 10px;
            width: 20px;
            text-align: center;
        }

        .main-content {
            margin-left: 250px;
            padding: 20px;
        }

        .dashboard-card {
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s;
        }

        .dashboard-card:hover {
            transform: translateY(-5px);
        }

        .stat-card {
            background: white;
            border-left: 4px solid var(--secondary-color);
        }

        .stat-card.warning {
            border-left-color: var(--accent-color);
        }

        .stat-card.success {
            border-left-color: #2ecc71;
        }

        .login-container {
            max-width: 400px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            background: white;
        }

        .property-card {
            border-radius: 8px;
            overflow: hidden;
            transition: transform 0.3s;
            margin-bottom: 20px;
        }

        .property-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
        }

        .property-image {
            height: 200px;
            background-color: #ddd;
            background-size: cover;
            background-position: center;
        }

        .badge-available {
            background-color: #2ecc71;
        }

        .badge-occupied {
            background-color: #e74c3c;
        }

        .chart-container {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .notification-badge {
            position: absolute;
            top: -5px;
            right: -5px;
            background-color: var(--accent-color);
            color: white;
            border-radius: 50%;
            width: 18px;
            height: 18px;
            font-size: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn-primary {
            background-color: var(--secondary-color);
            border-color: var(--secondary-color);
        }

        .btn-primary:hover {
            background-color: #2980b9;
            border-color: #2980b9;
        }
    </style>
</head>
<body>
<!-- Barre de navigation -->
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <i class="fas fa-building me-2"></i>ImmoManager
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-user-circle me-1"></i> Admin Dupont
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="#"><i class="fas fa-user me-2"></i>Profil</a></li>
                        <li><a class="dropdown-item" href="#"><i class="fas fa-cog me-2"></i>Paramètres</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt me-2"></i>Déconnexion</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link position-relative" href="#">
                        <i class="fas fa-bell"></i>
                        <span class="notification-badge">3</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Conteneur principal -->
<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-2 sidebar d-none d-md-block">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="#">
                        <i class="fas fa-tachometer-alt"></i> Tableau de bord
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-building"></i> Immeubles
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-home"></i> Unités
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-users"></i> Locataires
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-file-contract"></i> Contrats
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-money-bill-wave"></i> Paiements
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-chart-bar"></i> Rapports
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-cog"></i> Administration
                    </a>
                </li>
            </ul>
        </div>

        <!-- Contenu principal -->
        <div class="col-md-10 main-content">
            <h2 class="mb-4">Tableau de bord</h2>

            <!-- Cartes de statistiques -->
            <div class="row mb-4">
                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="stat-card card h-100 py-2 dashboard-card">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                        Immeubles</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">12</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-building fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="stat-card card h-100 py-2 dashboard-card">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                        Unités louées</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">24/36</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-home fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="stat-card card h-100 py-2 dashboard-card warning">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                        Paiements en attente</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">3</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-money-bill-wave fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-xl-3 col-md-6 mb-4">
                    <div class="stat-card card h-100 py-2 dashboard-card success">
                        <div class="card-body">
                            <div class="row no-gutters align-items-center">
                                <div class="col mr-2">
                                    <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                        Revenu mensuel</div>
                                    <div class="h5 mb-0 font-weight-bold text-gray-800">15 240 €</div>
                                </div>
                                <div class="col-auto">
                                    <i class="fas fa-euro-sign fa-2x text-gray-300"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Graphiques et liste des immeubles -->
            <div class="row">
                <div class="col-xl-8 col-lg-7">
                    <div class="chart-container mb-4">
                        <h5 class="card-title mb-4">Revenus mensuels</h5>
                        <canvas id="revenueChart" height="300"></canvas>
                    </div>
                </div>

                <div class="col-xl-4 col-lg-5">
                    <div class="card dashboard-card mb-4">
                        <div class="card-header">
                            <h5 class="card-title mb-0">Derniers paiements</h5>
                        </div>
                        <div class="card-body">
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <div>
                                        <h6 class="my-0">Martin Dupont</h6>
                                        <small class="text-muted">Appartement B12</small>
                                    </div>
                                    <span class="badge bg-success">1 200 €</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <div>
                                        <h6 class="my-0">Sophie Martin</h6>
                                        <small class="text-muted">Appartement A05</small>
                                    </div>
                                    <span class="badge bg-success">950 €</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <div>
                                        <h6 class="my-0">Pierre Leroy</h6>
                                        <small class="text-muted">Appartement C03</small>
                                    </div>
                                    <span class="badge bg-warning text-dark">En retard</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <div>
                                        <h6 class="my-0">Julie Bernard</h6>
                                        <small class="text-muted">Appartement B07</small>
                                    </div>
                                    <span class="badge bg-success">1 050 €</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Liste des immeubles -->
            <div class="row mt-4">
                <div class="col-12">
                    <div class="card dashboard-card">
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="card-title mb-0">Mes immeubles</h5>
                            <button class="btn btn-primary btn-sm">
                                <i class="fas fa-plus me-1"></i> Ajouter un immeuble
                            </button>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <!-- Immeuble 1 -->
                                <div class="col-md-6 col-lg-4 mb-4">
                                    <div class="card property-card">
                                        <div class="property-image" style="background-image: url('https://via.placeholder.com/300x200/3498db/ffffff')"></div>
                                        <div class="card-body">
                                            <h5 class="card-title">Résidence Les Cèdres</h5>
                                            <p class="card-text">
                                                <i class="fas fa-map-marker-alt me-2 text-muted"></i> 25 Avenue Victor Hugo, Paris
                                            </p>
                                            <div class="d-flex justify-content-between">
                                                <span><i class="fas fa-home me-1 text-muted"></i> 12 unités</span>
                                                <span class="badge bg-success">80% occupé</span>
                                            </div>
                                        </div>
                                        <div class="card-footer bg-transparent">
                                            <a href="#" class="btn btn-outline-primary btn-sm">Détails</a>
                                        </div>
                                    </div>
                                </div>

                                <!-- Immeuble 2 -->
                                <div class="col-md-6 col-lg-4 mb-4">
                                    <div class="card property-card">
                                        <div class="property-image" style="background-image: url('https://via.placeholder.com/300x200/e74c3c/ffffff')"></div>
                                        <div class="card-body">
                                            <h5 class="card-title">Résidence Le Parc</h5>
                                            <p class="card-text">
                                                <i class="fas fa-map-marker-alt me-2 text-muted"></i> 8 Rue de la Paix, Lyon
                                            </p>
                                            <div class="d-flex justify-content-between">
                                                <span><i class="fas fa-home me-1 text-muted"></i> 8 unités</span>
                                                <span class="badge bg-warning text-dark">50% occupé</span>
                                            </div>
                                        </div>
                                        <div class="card-footer bg-transparent">
                                            <a href="#" class="btn btn-outline-primary btn-sm">Détails</a>
                                        </div>
                                    </div>
                                </div>

                                <!-- Immeuble 3 -->
                                <div class="col-md-6 col-lg-4 mb-4">
                                    <div class="card property-card">
                                        <div class="property-image" style="background-image: url('https://via.placeholder.com/300x200/2ecc71/ffffff')"></div>
                                        <div class="card-body">
                                            <h5 class="card-title">Les Hauts de Marseille</h5>
                                            <p class="card-text">
                                                <i class="fas fa-map-marker-alt me-2 text-muted"></i> 15 Rue de Rome, Marseille
                                            </p>
                                            <div class="d-flex justify-content-between">
                                                <span><i class="fas fa-home me-1 text-muted"></i> 16 unités</span>
                                                <span class="badge bg-success">95% occupé</span>
                                            </div>
                                        </div>
                                        <div class="card-footer bg-transparent">
                                            <a href="#" class="btn btn-outline-primary btn-sm">Détails</a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
    // Graphique des revenus
    document.addEventListener('DOMContentLoaded', function() {
        const ctx = document.getElementById('revenueChart').getContext('2d');
        const revenueChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ['Jan', 'Fév', 'Mar', 'Avr', 'Mai', 'Juin', 'Juil', 'Août', 'Sep', 'Oct', 'Nov', 'Déc'],
                datasets: [{
                    label: 'Revenus en €',
                    data: [12500, 13200, 14100, 14800, 15600, 16000, 17200, 16800, 15800, 15240, 16200, 17500],
                    backgroundColor: 'rgba(52, 152, 219, 0.7)',
                    borderColor: 'rgba(52, 152, 219, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                responsive: true,
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    });
</script>
</body>
</html>