<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%@ page import="com.gestion.location.service.ImmeubleService" %>
<%@ page import="com.gestion.location.service.UtilisateurService" %>
<%
    // Vérification de session et rôle admin
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if(user == null || !"ADMIN".equalsIgnoreCase(user.getRole())){
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }

    // Récupération du total d'immeubles
    ImmeubleService immeubleService = new ImmeubleService();
    int totalImmeubles = immeubleService.lister().size();

    // Récupération du total d'utilisateurs
    UtilisateurService utilisateurService = new UtilisateurService();
    int totalUtilisateurs = utilisateurService.lister().size();
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; }
        .navbar { background-color: #2c3e50; }
        .sidebar { background-color: #34495e; color: white; height: calc(100vh - 56px); position: fixed; width: 220px; padding-top: 20px; }
        .sidebar .nav-link { color: rgba(255,255,255,0.8); padding: 10px 15px; margin: 4px 0; border-radius: 4px; }
        .sidebar .nav-link:hover { background-color: rgba(255,255,255,0.1); color: white; }
        .sidebar .nav-link.active { background-color: #3498db; color: white; }
        .sidebar .nav-link i { margin-right: 10px; width: 20px; text-align: center; }
        .main-content { margin-left: 220px; padding: 20px; }
        .card { border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .stat-card { border-left: 4px solid #3498db; }
        .stat-card.warning { border-left-color: #e74c3c; }
        .stat-card.success { border-left-color: #2ecc71; }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"><i class="fas fa-building me-2"></i>Administateur</a>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <!-- Sidebar -->
        <div class="col-md-2 sidebar d-none d-md-block">
            <ul class="nav flex-column">
                <li class="nav-item"><a class="nav-link active" href="#"><i class="fas fa-tachometer-alt"></i> Tableau de bord</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-users"></i> Utilisateurs</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-building"></i> Immeubles</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-home"></i> Unites</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-file-contract"></i> Contrats</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-money-bill-wave"></i> Paiements</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-chart-line"></i> Rapports</a></li>
                <li class="nav-item"><a class="nav-link" href="#"><i class="fas fa-cogs"></i> Configuration</a></li>
            </ul>
        </div>

        <!-- Contenu principal -->
        <div class="col-md-10 main-content">
            <h2>BIENVENUE TRES CHER, <%= user.getNom() %> !</h2>
            <p>Role : <%= user.getRole() %></p>

            <!-- Cartes statistiques globales -->
            <div class="row">
                <div class="col-md-3">
                    <div class="card stat-card">
                        <div class="card-body">
                            <h6>Total Utilisateurs</h6>
                            <h3><%= totalUtilisateurs %></h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card stat-card success">
                        <div class="card-body">
                            <h6>Total Immeubles</h6>
                            <h3><%= totalImmeubles %></h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card stat-card warning">
                        <div class="card-body">
                            <h6>Paiements en retard</h6>
                            <h3>5</h3>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card stat-card success">
                        <div class="card-body">
                            <h6>Revenu total</h6>
                            <h3>48 750 €</h3>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sections pour la gestion globale -->
            <div class="row mt-4">
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <i class="fas fa-users fa-2x mb-2"></i>
                            <h5 class="card-title">Gestion Utilisateurs</h5>
                            <p class="card-text">Ajouter, modifier, supprimer proprietaires et locataires.</p>
                            <a href="<%= request.getContextPath() %>/utilisateurs" class="btn btn-primary btn-sm">Gerer</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <i class="fas fa-building fa-2x mb-2"></i>
                            <h5 class="card-title">Gestion Immeubles</h5>
                            <p class="card-text">Ajouter, modifier, supprimer tout immeuble et unité.</p>
                            <a href="<%= request.getContextPath() %>/immeuble/liste.jsp" class="btn btn-primary btn-sm">Gerer</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <i class="fas fa-file-contract fa-2x mb-2"></i>
                            <h5 class="card-title">Contrats & Paiements</h5>
                            <p class="card-text">Suivi global des contrats et paiements.</p>
                            <a href="<%= request.getContextPath() %>/contrats" class="btn btn-primary btn-sm">Voir</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center">
                        <div class="card-body">
                            <i class="fas fa-chart-line fa-2x mb-2"></i>
                            <h5 class="card-title">Rapports</h5>
                            <p class="card-text">Générer rapports globaux sur l’activité.</p>
                            <a href="rapports.jsp" class="btn btn-primary btn-sm">Générer</a>
                        </div>
                    </div>
                </div>
            </div>

            <a href="AuthServlet?logout=true" class="btn btn-danger mt-4">
                <i class="fas fa-sign-out-alt me-1"></i> Se déconnecter
            </a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
