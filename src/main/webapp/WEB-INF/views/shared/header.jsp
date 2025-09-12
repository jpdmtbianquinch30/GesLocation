<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>GestionLocation - ${param.title != null ? param.title : 'Application de gestion immobilière'}</title>

    <!-- CSS Principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">

    <!-- CSS spécifique au rôle -->
    <c:choose>
        <c:when test="${sessionScope.userRole == 'ADMIN'}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">
        </c:when>
        <c:when test="${sessionScope.userRole == 'PROPRIETAIRE'}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/proprietaire.css">
        </c:when>
        <c:when test="${sessionScope.userRole == 'LOCATAIRE'}">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
        </c:when>
    </c:choose>

    <!-- Favicon -->
    <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/img/favicon.ico">

    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
<!-- Header Navigation -->
<header class="main-header">
    <div class="header-container">
        <!-- Logo -->
        <div class="logo">
            <a href="${pageContext.request.contextPath}">
                <i class="fas fa-building"></i>
                <span>GestionLocation</span>
            </a>
        </div>

        <!-- Navigation principale -->
        <nav class="main-nav">
            <c:if test="${not empty sessionScope.user}">
                <ul class="nav-links">
                    <c:choose>
                        <c:when test="${sessionScope.userRole == 'ADMIN'}">
                            <li><a href="${pageContext.request.contextPath}/admin/dashboard"
                                   class="${param.currentPage == 'dashboard' ? 'active' : ''}">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/utilisateurs"
                                   class="${param.currentPage == 'utilisateurs' ? 'active' : ''}">
                                <i class="fas fa-users"></i> Utilisateurs
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/admin/rapports"
                                   class="${param.currentPage == 'rapports' ? 'active' : ''}">
                                <i class="fas fa-chart-bar"></i> Rapports
                            </a></li>
                        </c:when>
                        <c:when test="${sessionScope.userRole == 'PROPRIETAIRE'}">
                            <li><a href="${pageContext.request.contextPath}/proprietaire/dashboard"
                                   class="${param.currentPage == 'dashboard' ? 'active' : ''}">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/immeubles"
                                   class="${param.currentPage == 'immeubles' ? 'active' : ''}">
                                <i class="fas fa-building"></i> Mes Immeubles
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/unites"
                                   class="${param.currentPage == 'unites' ? 'active' : ''}">
                                <i class="fas fa-home"></i> Unités
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/proprietaire/contrats"
                                   class="${param.currentPage == 'contrats' ? 'active' : ''}">
                                <i class="fas fa-file-contract"></i> Contrats
                            </a></li>
                        </c:when>
                        <c:when test="${sessionScope.userRole == 'LOCATAIRE'}">
                            <li><a href="${pageContext.request.contextPath}/locataire/dashboard"
                                   class="${param.currentPage == 'dashboard' ? 'active' : ''}">
                                <i class="fas fa-tachometer-alt"></i> Dashboard
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/locataire/offres"
                                   class="${param.currentPage == 'offres' ? 'active' : ''}">
                                <i class="fas fa-search"></i> Offres
                            </a></li>
                            <li><a href="${pageContext.request.contextPath}/locataire/paiements"
                                   class="${param.currentPage == 'paiements' ? 'active' : ''}">
                                <i class="fas fa-credit-card"></i> Paiements
                            </a></li>
                        </c:when>
                    </c:choose>
                </ul>
            </c:if>
        </nav>

        <!-- User Menu -->
        <div class="user-menu">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <div class="user-dropdown">
                        <button class="user-toggle">
                            <div class="user-avatar">
                                <i class="fas fa-user-circle"></i>
                            </div>
                            <span class="user-name">${sessionScope.userPrenom} ${sessionScope.userNom}</span>
                            <i class="fas fa-chevron-down"></i>
                        </button>
                        <div class="dropdown-menu">
                            <div class="user-info">
                                <strong>${sessionScope.userPrenom} ${sessionScope.userNom}</strong>
                                <small>${sessionScope.userRole}</small>
                                <small>${sessionScope.userEmail}</small>
                            </div>
                            <div class="dropdown-divider"></div>
                            <a href="${pageContext.request.contextPath}/profile" class="dropdown-item">
                                <i class="fas fa-user"></i> Mon Profil
                            </a>
                            <a href="${pageContext.request.contextPath}/settings" class="dropdown-item">
                                <i class="fas fa-cog"></i> Paramètres
                            </a>
                            <div class="dropdown-divider"></div>
                            <form action="${pageContext.request.contextPath}/logout" method="post" class="logout-form">
                                <button type="submit" class="dropdown-item logout-btn">
                                    <i class="fas fa-sign-out-alt"></i> Déconnexion
                                </button>
                            </form>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="auth-buttons">
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Connexion</a>
                        <a href="${pageContext.request.contextPath}/register" class="btn btn-primary">Inscription</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Mobile Menu Toggle -->
        <button class="mobile-menu-toggle">
            <i class="fas fa-bars"></i>
        </button>
    </div>
</header>

<!-- Mobile Navigation -->
<div class="mobile-nav" id="mobileNav">
    <div class="mobile-nav-header">
        <span>Menu</span>
        <button class="mobile-nav-close">
            <i class="fas fa-times"></i>
        </button>
    </div>
    <nav class="mobile-nav-content">
        <c:if test="${not empty sessionScope.user}">
            <div class="mobile-user-info">
                <div class="user-avatar">
                    <i class="fas fa-user-circle"></i>
                </div>
                <div>
                    <strong>${sessionScope.userPrenom} ${sessionScope.userNom}</strong>
                    <small>${sessionScope.userRole}</small>
                </div>
            </div>

            <ul class="mobile-nav-links">
                <c:choose>
                    <c:when test="${sessionScope.userRole == 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/admin/dashboard">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/utilisateurs">
                            <i class="fas fa-users"></i> Utilisateurs
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/admin/rapports">
                            <i class="fas fa-chart-bar"></i> Rapports
                        </a></li>
                    </c:when>
                    <c:when test="${sessionScope.userRole == 'PROPRIETAIRE'}">
                        <li><a href="${pageContext.request.contextPath}/proprietaire/dashboard">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/proprietaire/immeubles">
                            <i class="fas fa-building"></i> Mes Immeubles
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/proprietaire/unites">
                            <i class="fas fa-home"></i> Unités
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/proprietaire/contrats">
                            <i class="fas fa-file-contract"></i> Contrats
                        </a></li>
                    </c:when>
                    <c:when test="${sessionScope.userRole == 'LOCATAIRE'}">
                        <li><a href="${pageContext.request.contextPath}/locataire/dashboard">
                            <i class="fas fa-tachometer-alt"></i> Dashboard
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/locataire/offres">
                            <i class="fas fa-search"></i> Offres
                        </a></li>
                        <li><a href="${pageContext.request.contextPath}/locataire/paiements">
                            <i class="fas fa-credit-card"></i> Paiements
                        </a></li>
                    </c:when>
                </c:choose>
            </ul>

            <div class="mobile-nav-footer">
                <a href="${pageContext.request.contextPath}/profile">
                    <i class="fas fa-user"></i> Mon Profil
                </a>
                <a href="${pageContext.request.contextPath}/settings">
                    <i class="fas fa-cog"></i> Paramètres
                </a>
                <form action="${pageContext.request.contextPath}/logout" method="post">
                    <button type="submit" class="logout-btn">
                        <i class="fas fa-sign-out-alt"></i> Déconnexion
                    </button>
                </form>
            </div>
        </c:if>
    </nav>
</div>

<!-- Main Content Wrapper -->
<div class="main-wrapper"/>