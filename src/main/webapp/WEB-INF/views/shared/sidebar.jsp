<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar Navigation -->
<aside class="main-sidebar">
    <div class="sidebar-header">
        <div class="sidebar-logo">
            <i class="fas fa-building"></i>
            <span>GestionLocation</span>
        </div>
        <button class="sidebar-toggle">
            <i class="fas fa-chevron-left"></i>
        </button>
    </div>

    <nav class="sidebar-nav">
        <c:choose>
            <c:when test="${sessionScope.userRole == 'ADMIN'}">
                <div class="sidebar-section">
                    <h3 class="sidebar-title">Administration</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'dashboard' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/admin/dashboard">
                                <i class="fas fa-tachometer-alt"></i>
                                <span class="menu-text">Tableau de Bord</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-section">
                    <h3 class="sidebar-title">Gestion</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'utilisateurs' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/admin/utilisateurs">
                                <i class="fas fa-users"></i>
                                <span class="menu-text">Utilisateurs</span>
                            </a>
                        </li>
                        <li class="menu-item ${param.currentPage == 'rapports' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/admin/rapports">
                                <i class="fas fa-chart-bar"></i>
                                <span class="menu-text">Rapports</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </c:when>

            <c:when test="${sessionScope.userRole == 'PROPRIETAIRE'}">
                <div class="sidebar-section">
                    <h3 class="sidebar-title">Tableau de Bord</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'dashboard' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/dashboard">
                                <i class="fas fa-tachometer-alt"></i>
                                <span class="menu-text">Accueil</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-section">
                    <h3 class="sidebar-title">Gestion Immobilière</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'immeubles' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/immeubles">
                                <i class="fas fa-building"></i>
                                <span class="menu-text">Mes Immeubles</span>
                            </a>
                        </li>
                        <li class="menu-item ${param.currentPage == 'unites' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/unites">
                                <i class="fas fa-home"></i>
                                <span class="menu-text">Unités</span>
                            </a>
                        </li>
                        <li class="menu-item ${param.currentPage == 'contrats' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/contrats">
                                <i class="fas fa-file-contract"></i>
                                <span class="menu-text">Contrats</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-section">
                    <h3 class="sidebar-title">Finances</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item">
                            <a href="#">
                                <i class="fas fa-money-bill-wave"></i>
                                <span class="menu-text">Paiements</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="#">
                                <i class="fas fa-chart-line"></i>
                                <span class="menu-text">Revenus</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </c:when>

            <c:when test="${sessionScope.userRole == 'LOCATAIRE'}">
                <div class="sidebar-section">
                    <h3 class="sidebar-title">Tableau de Bord</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'dashboard' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/locataire/dashboard">
                                <i class="fas fa-tachometer-alt"></i>
                                <span class="menu-text">Accueil</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-section">
                    <h3 class="sidebar-title">Location</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'offres' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/locataire/offres">
                                <i class="fas fa-search"></i>
                                <span class="menu-text">Consulter les Offres</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="${pageContext.request.contextPath}/locataire/mes-contrats">
                                <i class="fas fa-file-contract"></i>
                                <span class="menu-text">Mes Contrats</span>
                            </a>
                        </li>
                    </ul>
                </div>

                <div class="sidebar-section">
                    <h3 class="sidebar-title">Paiements</h3>
                    <ul class="sidebar-menu">
                        <li class="menu-item ${param.currentPage == 'paiements' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/locataire/paiements">
                                <i class="fas fa-credit-card"></i>
                                <span class="menu-text">Mes Paiements</span>
                            </a>
                        </li>
                        <li class="menu-item">
                            <a href="#">
                                <i class="fas fa-history"></i>
                                <span class="menu-text">Historique</span>
                            </a>
                        </li>
                    </ul>
                </div>
            </c:when>
        </c:choose>

        <div class="sidebar-section">
            <h3 class="sidebar-title">Compte</h3>
            <ul class="sidebar-menu">
                <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/profile">
                        <i class="fas fa-user"></i>
                        <span class="menu-text">Mon Profil</span>
                    </a>
                </li>
                <li class="menu-item">
                    <a href="${pageContext.request.contextPath}/settings">
                        <i class="fas fa-cog"></i>
                        <span class="menu-text">Paramètres</span>
                    </a>
                </li>
                <li class="menu-item">
                    <form action="${pageContext.request.contextPath}/logout" method="post" class="sidebar-logout-form">
                        <button type="submit" class="logout-btn">
                            <i class="fas fa-sign-out-alt"></i>
                            <span class="menu-text">Déconnexion</span>
                        </button>
                    </form>
                </li>
            </ul>
        </div>
    </nav>

    <div class="sidebar-footer">
        <div class="user-info">
            <div class="user-avatar">
                <i class="fas fa-user-circle"></i>
            </div>
            <div class="user-details">
                <strong>${sessionScope.userPrenom} ${sessionScope.userNom}</strong>
                <small>${sessionScope.userRole}</small>
            </div>
        </div>
    </div>
</aside>

<!-- Main Content Area -->
<main class="main-content"/>