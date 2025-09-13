<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Sidebar Navigation -->
<aside class="main-sidebar">
    <div class="sidebar-header">
        <div class="sidebar-logo">
            <i class="fas fa-building"></i>
            <span>GestionLocation</span>
        </div>
        <button class="sidebar-toggle" id="sidebarToggle">
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
                        <li class="menu-item ${param.currentPage == 'paiements' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/paiements">
                                <i class="fas fa-money-bill-wave"></i>
                                <span class="menu-text">Paiements</span>
                            </a>
                        </li>
                        <li class="menu-item ${param.currentPage == 'revenus' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/proprietaire/revenus">
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
                        <li class="menu-item ${param.currentPage == 'mes-contrats' ? 'active' : ''}">
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
                        <li class="menu-item ${param.currentPage == 'historique' ? 'active' : ''}">
                            <a href="${pageContext.request.contextPath}/locataire/historique">
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
                <li class="menu-item ${param.currentPage == 'profile' ? 'active' : ''}">
                    <a href="${pageContext.request.contextPath}/profile">
                        <i class="fas fa-user"></i>
                        <span class="menu-text">Mon Profil</span>
                    </a>
                </li>
                <li class="menu-item ${param.currentPage == 'settings' ? 'active' : ''}">
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

<!-- JavaScript pour le toggle du sidebar -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const sidebar = document.querySelector('.main-sidebar');
        const sidebarToggle = document.getElementById('sidebarToggle');
        const hamburgerBtn = document.getElementById('hamburgerBtn');

        // Fonction pour basculer l'état du sidebar
        function toggleSidebar() {
            document.body.classList.toggle('sidebar-collapsed');
            // Sauvegarder l'état dans le localStorage
            const isCollapsed = document.body.classList.contains('sidebar-collapsed');
            localStorage.setItem('sidebarCollapsed', isCollapsed);
        }

        // Événement pour le bouton de toggle dans le sidebar
        if (sidebarToggle) {
            sidebarToggle.addEventListener('click', toggleSidebar);
        }

        // Événement pour le bouton hamburger dans le header
        if (hamburgerBtn) {
            hamburgerBtn.addEventListener('click', toggleSidebar);
        }

        // Restaurer l'état du sidebar depuis le localStorage
        const sidebarCollapsed = localStorage.getItem('sidebarCollapsed') === 'true';
        if (sidebarCollapsed) {
            document.body.classList.add('sidebar-collapsed');
        }
    });
</script>

<style>
    :root {
        --sidebar-width: 250px;
        --sidebar-collapsed-width: 70px;
        --primary-color: #4e73df;
        --secondary-color: #1cc88a;
        --dark-color: #2e3a59;
        --light-color: #f8f9fc;
        --transition-speed: 0.3s;
    }

    .main-sidebar {
        width: var(--sidebar-width);
        background: var(--dark-color);
        color: white;
        height: 100vh;
        position: fixed;
        top: 0;
        left: 0;
        overflow-y: auto;
        transition: width var(--transition-speed);
        z-index: 1000;
        box-shadow: 3px 0 10px rgba(0, 0, 0, 0.1);
    }

    .sidebar-collapsed .main-sidebar {
        width: var(--sidebar-collapsed-width);
    }

    .sidebar-header {
        padding: 20px 15px;
        border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .sidebar-logo {
        display: flex;
        align-items: center;
        white-space: nowrap;
        overflow: hidden;
    }

    .sidebar-logo i {
        font-size: 1.5rem;
        margin-right: 10px;
        color: var(--secondary-color);
    }

    .sidebar-logo span {
        font-weight: 700;
        font-size: 1.2rem;
    }

    .sidebar-collapsed .sidebar-logo span {
        display: none;
    }

    .sidebar-toggle {
        background: none;
        border: none;
        color: white;
        cursor: pointer;
        font-size: 1rem;
    }

    .sidebar-collapsed .sidebar-toggle i {
        transform: rotate(180deg);
    }

    .sidebar-nav {
        padding: 15px 0;
    }

    .sidebar-section {
        margin-bottom: 20px;
    }

    .sidebar-title {
        font-size: 0.8rem;
        text-transform: uppercase;
        padding: 0 15px;
        margin-bottom: 10px;
        color: rgba(255, 255, 255, 0.6);
        white-space: nowrap;
        overflow: hidden;
    }

    .sidebar-collapsed .sidebar-title {
        display: none;
    }

    .sidebar-menu {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .menu-item {
        margin-bottom: 5px;
    }

    .menu-item a, .logout-btn {
        display: flex;
        align-items: center;
        padding: 12px 15px;
        color: rgba(255, 255, 255, 0.8);
        text-decoration: none;
        transition: all 0.3s;
        border-left: 3px solid transparent;
        white-space: nowrap;
        overflow: hidden;
    }

    .menu-item a:hover, .logout-btn:hover {
        background: rgba(255, 255, 255, 0.1);
        color: white;
        border-left-color: var(--secondary-color);
    }

    .menu-item.active a {
        background: rgba(255, 255, 255, 0.15);
        color: white;
        border-left-color: var(--primary-color);
    }

    .menu-item i {
        width: 20px;
        margin-right: 15px;
        font-size: 1rem;
        text-align: center;
    }

    .sidebar-collapsed .menu-text {
        display: none;
    }

    .sidebar-logout-form {
        width: 100%;
    }

    .logout-btn {
        background: none;
        border: none;
        width: 100%;
        text-align: left;
        cursor: pointer;
    }

    .sidebar-footer {
        padding: 15px;
        border-top: 1px solid rgba(255, 255, 255, 0.1);
        margin-top: auto;
    }

    .user-info {
        display: flex;
        align-items: center;
        white-space: nowrap;
        overflow: hidden;
    }

    .user-avatar {
        margin-right: 10px;
        font-size: 1.8rem;
        color: var(--secondary-color);
    }

    .user-details {
        overflow: hidden;
    }

    .user-details strong {
        display: block;
        font-size: 0.9rem;
    }

    .user-details small {
        font-size: 0.75rem;
        color: rgba(255, 255, 255, 0.6);
    }

    .sidebar-collapsed .user-details {
        display: none;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .main-sidebar {
            transform: translateX(-100%);
            width: var(--sidebar-width);
        }

        .sidebar-show .main-sidebar {
            transform: translateX(0);
        }
    }
</style>