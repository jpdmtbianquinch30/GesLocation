// main.js - JavaScript principal pour l'application

document.addEventListener('DOMContentLoaded', function() {
    // Gestion du menu mobile
    initMobileMenu();

    // Gestion des dropdowns utilisateur
    initUserDropdowns();

    // Gestion des messages toast
    initToastSystem();

    // Gestion des formulaires
    initForms();

    // Gestion des modals
    initModals();
});

// Menu mobile
function initMobileMenu() {
    const menuToggle = document.querySelector('.mobile-menu-toggle');
    const mobileNav = document.querySelector('.mobile-nav');
    const navClose = document.querySelector('.mobile-nav-close');

    if (menuToggle && mobileNav) {
        menuToggle.addEventListener('click', () => {
            mobileNav.classList.add('open');
            document.body.style.overflow = 'hidden';
        });

        if (navClose) {
            navClose.addEventListener('click', () => {
                mobileNav.classList.remove('open');
                document.body.style.overflow = '';
            });
        }

        // Fermer le menu en cliquant à l'extérieur
        document.addEventListener('click', (e) => {
            if (!mobileNav.contains(e.target) && !menuToggle.contains(e.target)) {
                mobileNav.classList.remove('open');
                document.body.style.overflow = '';
            }
        });
    }
}

// Dropdowns utilisateur
function initUserDropdowns() {
    const dropdowns = document.querySelectorAll('.user-dropdown');

    dropdowns.forEach(dropdown => {
        const toggle = dropdown.querySelector('.user-toggle');
        const menu = dropdown.querySelector('.dropdown-menu');

        if (toggle && menu) {
            toggle.addEventListener('click', (e) => {
                e.stopPropagation();
                menu.classList.toggle('show');
            });

            // Fermer en cliquant à l'extérieur
            document.addEventListener('click', () => {
                menu.classList.remove('show');
            });

            // Empêcher la fermeture en cliquant dans le menu
            menu.addEventListener('click', (e) => {
                e.stopPropagation();
            });
        }
    });
}

// Système de messages toast
function initToastSystem() {
    window.showToast = function(message, type = 'info', duration = 5000) {
        const container = document.getElementById('toastContainer') || createToastContainer();
        const toast = createToast(message, type);

        container.appendChild(toast);

        // Animation d'entrée
        setTimeout(() => toast.classList.add('show'), 100);

        // Suppression automatique
        if (duration > 0) {
            setTimeout(() => hideToast(toast), duration);
        }

        return toast;
    };

    function createToastContainer() {
        const container = document.createElement('div');
        container.className = 'toast-container';
        document.body.appendChild(container);
        return container;
    }

    function createToast(message, type) {
        const toast = document.createElement('div');
        toast.className = `toast toast-${type}`;
        toast.innerHTML = `
            <div class="toast-content">
                <i class="toast-icon ${getToastIcon(type)}"></i>
                <span class="toast-message">${message}</span>
            </div>
            <button class="toast-close" onclick="hideToast(this.parentNode)">
                <i class="fas fa-times"></i>
            </button>
        `;
        return toast;
    }

    function getToastIcon(type) {
        const icons = {
            success: 'fas fa-check-circle',
            error: 'fas fa-exclamation-circle',
            warning: 'fas fa-exclamation-triangle',
            info: 'fas fa-info-circle'
        };
        return icons[type] || icons.info;
    }

    window.hideToast = function(toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 300);
    };
}

// Gestion des formulaires
function initForms() {
    // Validation basique
    const forms = document.querySelectorAll('form[needs-validation]');

    forms.forEach(form => {
        form.addEventListener('submit', function(e) {
            if (!this.checkValidity()) {
                e.preventDefault();
                e.stopPropagation();
                showFormErrors(this);
            }
            this.classList.add('was-validated');
        });
    });

    // Masquer les messages de validation natifs
    document.addEventListener('invalid', (function() {
        return function(e) {
            e.preventDefault();
            const input = e.target;
            showInputError(input);
        };
    })(), true);
}

function showFormErrors(form) {
    const invalidInputs = form.querySelectorAll(':invalid');
    invalidInputs.forEach(input => showInputError(input));
}

function showInputError(input) {
    const message = input.validationMessage;
    const container = input.closest('.form-group') || input.parentNode;

    // Supprimer les erreurs existantes
    const existingError = container.querySelector('.error-message');
    if (existingError) existingError.remove();

    // Ajouter le message d'erreur
    if (message) {
        const errorDiv = document.createElement('div');
        errorDiv.className = 'error-message';
        errorDiv.textContent = message;
        errorDiv.style.color = 'var(--danger-color)';
        errorDiv.style.fontSize = 'var(--font-size-sm)';
        errorDiv.style.marginTop = 'var(--spacing-xs)';
        container.appendChild(errorDiv);
    }

    // Style d'erreur sur l'input
    input.style.borderColor = 'var(--danger-color)';
    setTimeout(() => input.style.borderColor = '', 3000);
}

// Gestion des modals
function initModals() {
    window.showModal = function(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.style.display = 'flex';
            document.body.style.overflow = 'hidden';
        }
    };

    window.hideModal = function(modalId) {
        const modal = document.getElementById(modalId);
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = '';
        }
    };

    // Fermer les modals en cliquant à l'extérieur
    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('modal-overlay')) {
            hideModal(e.target.id);
        }
    });

    // Fermer avec la touche Échap
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape') {
            const openModals = document.querySelectorAll('.modal-overlay[style*="display: flex"]');
            openModals.forEach(modal => hideModal(modal.id));
        }
    });
}

// Fonctions utilitaires
function formatCurrency(amount) {
    return new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'EUR'
    }).format(amount);
}

function formatDate(date) {
    return new Intl.DateTimeFormat('fr-FR').format(new Date(date));
}

function debounce(func, wait) {
    let timeout;
    return function executedFunction(...args) {
        const later = () => {
            clearTimeout(timeout);
            func(...args);
        };
        clearTimeout(timeout);
        timeout = setTimeout(later, wait);
    };
}

// Gestion du loading
window.showLoading = function() {
    const overlay = document.getElementById('loadingOverlay');
    if (overlay) overlay.style.display = 'flex';
};

window.hideLoading = function() {
    const overlay = document.getElementById('loadingOverlay');
    if (overlay) overlay.style.display = 'none';
};

// Intercepteur pour les requêtes AJAX
const originalFetch = window.fetch;
window.fetch = function(...args) {
    showLoading();
    return originalFetch.apply(this, args)
        .then(response => {
            hideLoading();
            return response;
        })
        .catch(error => {
            hideLoading();
            throw error;
        });
};

// Export des fonctions pour une utilisation globale
window.utils = {
    formatCurrency,
    formatDate,
    debounce,
    showLoading,
    hideLoading
};