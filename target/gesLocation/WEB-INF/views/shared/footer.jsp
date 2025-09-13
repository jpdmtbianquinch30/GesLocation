<style>
    /* Styles pour l'intégration du sidebar et footer */
    body {
        display: flex;
        flex-direction: column;
        min-height: 100vh;
    }

    .main-wrapper {
        display: flex;
        flex: 1;
    }

    .main-content {
        flex: 1;
        padding: 20px;
        margin-left: 250px; /* Largeur du sidebar */
        transition: margin-left 0.3s;
    }

    .sidebar-collapsed .main-content {
        margin-left: 70px; /* Largeur du sidebar réduit */
    }

    /* Header avec bouton hamburger */
    .main-header {
        background: white;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        height: 70px;
        position: sticky;
        top: 0;
        z-index: 900;
        display: flex;
        align-items: center;
        padding: 0 20px;
    }

    .hamburger-btn {
        background: none;
        border: none;
        font-size: 1.5rem;
        color: #2e3a59;
        cursor: pointer;
        margin-right: 15px;
        padding: 5px 10px;
        border-radius: 5px;
        transition: background-color 0.3s;
    }

    .hamburger-btn:hover {
        background-color: #f0f0f0;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .main-sidebar {
            transform: translateX(-100%);
            width: 250px;
        }

        .sidebar-show .main-sidebar {
            transform: translateX(0);
        }

        .main-content {
            margin-left: 0 !important;
        }
    }
</style>