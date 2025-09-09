<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    com.gestion.location.entities.Utilisateur user =
            (com.gestion.location.entities.Utilisateur) session.getAttribute("user");
    if (user == null || !user.getRole().equals("PROPRIETAIRE")) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Propriétaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { min-height:100vh; display:flex; flex-direction:column; }
        .sidebar { min-width:220px; max-width:220px; background-color:#343a40; color:#fff; }
        .sidebar a { color:#fff; text-decoration:none; }
        .sidebar a:hover { background-color:#495057; }
        .content { flex:1; padding:20px; }
        .card { margin-bottom:20px; }
        @media(max-width:768px){ .sidebar{ max-width:100%; min-width:100%; } }
    </style>
</head>
<body>
<div class="d-flex">
    <!-- Sidebar -->
    <div class="sidebar d-flex flex-column p-3">
        <h4 class="text-center">Propriétaire</h4>
        <hr>
        <ul class="nav nav-pills flex-column mb-auto">
            <li><a href="#immeubles" class="nav-link">Mes Immeubles</a></li>
            <li><a href="#unites" class="nav-link">Unités</a></li>
            <li><a href="#locataires" class="nav-link">Locataires</a></li>
            <li><a href="#paiements" class="nav-link">Paiements</a></li>
        </ul>
        <hr>
        <div>
            <a href="AuthServlet?logout=true" class="btn btn-danger w-100">Déconnexion</a>
        </div>
    </div>

    <!-- Contenu principal -->
    <div class="content flex-grow-1">
        <h2>Bienvenue, <c:out value="${user.nom}"/></h2>

        <!-- Statistiques rapides -->
        <div class="row">
            <div class="col-md-3">
                <div class="card text-white bg-primary">
                    <div class="card-body">
                        <h5 class="card-title">Mes Immeubles</h5>
                        <p class="card-text"><c:out value="${immeubles.size()}"/></p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-success">
                    <div class="card-body">
                        <h5 class="card-title">Unités</h5>
                        <p class="card-text"><c:out value="${unites.size()}"/></p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-warning">
                    <div class="card-body">
                        <h5 class="card-title">Locataires</h5>
                        <p class="card-text"><c:out value="${locataires.size()}"/></p>
                    </div>
                </div>
            </div>
            <div class="col-md-3">
                <div class="card text-white bg-danger">
                    <div class="card-body">
                        <h5 class="card-title">Paiements</h5>
                        <p class="card-text"><c:out value="${paiements.size()}"/></p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Liste des Immeubles -->
        <section id="immeubles" class="mt-5">
            <h3>Mes Immeubles</h3>
            <a href="<%= request.getContextPath() %>/immeubles?action=add" class="btn btn-success mb-3">Ajouter un Immeuble</a>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Adresse</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="immeuble" items="${immeubles}">
                    <tr>
                        <td><c:out value="${immeuble.id}"/></td>
                        <td><c:out value="${immeuble.nom}"/></td>
                        <td><c:out value="${immeuble.adresse}"/></td>
                        <td>
                            <a href="<%= request.getContextPath() %>/immeubles?action=edit&id=${immeuble.id}" class="btn btn-sm btn-warning">Modifier</a>
                            <a href="<%= request.getContextPath() %>/immeubles?action=delete&id=${immeuble.id}" class="btn btn-sm btn-danger">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>

        <!-- Unités liées aux immeubles -->
        <section id="unites" class="mt-5">
            <h3>Unités</h3>
            <a href="<%= request.getContextPath() %>/unites?action=add" class="btn btn-success mb-3">Ajouter une Unité</a>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Numéro</th>
                    <th>Immeuble</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="unite" items="${unites}">
                    <tr>
                        <td><c:out value="${unite.id}"/></td>
                        <td><c:out value="${unite.numero}"/></td>
                        <td><c:out value="${unite.immeuble.nom}"/></td>
                        <td>
                            <a href="<%= request.getContextPath() %>/unites?action=edit&id=${unite.id}" class="btn btn-sm btn-warning">Modifier</a>
                            <a href="<%= request.getContextPath() %>/unites?action=delete&id=${unite.id}" class="btn btn-sm btn-danger">Supprimer</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>

        <!-- Locataires pour ses unités -->
        <section id="locataires" class="mt-5">
            <h3>Locataires</h3>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="locataire" items="${locataires}">
                    <tr>
                        <td><c:out value="${locataire.id}"/></td>
                        <td><c:out value="${locataire.nom}"/></td>
                        <td><c:out value="${locataire.email}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>

        <!-- Paiements reçus -->
        <section id="paiements" class="mt-5">
            <h3>Paiements</h3>
            <table class="table table-striped table-bordered mt-3">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Locataire</th>
                    <th>Montant</th>
                    <th>Date</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="paiement" items="${paiements}">
                    <tr>
                        <td><c:out value="${paiement.id}"/></td>
                        <td><c:out value="${paiement.locataire.nom}"/></td>
                        <td><c:out value="${paiement.montant}"/></td>
                        <td><c:out value="${paiement.date}"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
