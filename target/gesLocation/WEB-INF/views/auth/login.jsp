<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Connexion - Gestion Location</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <style>
        :root {
            --primary-gradient: linear-gradient(135deg, #4e73df 0%, #1cc88a 100%);
            --primary-color: #4e73df;
            --success-color: #1cc88a;
        }

        body {
            background: var(--primary-gradient);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
        }

        .auth-container {
            width: 100%;
            max-width: 420px;
            padding: 15px;
        }

        .auth-card {
            border-radius: 16px;
            border: none;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            overflow: hidden;
            background: #fff;
        }

        .auth-header {
            background: var(--primary-gradient);
            color: white;
            padding: 25px 30px;
            text-align: center;
        }

        .auth-body {
            padding: 30px;
        }

        .form-control {
            border-radius: 8px;
            padding: 12px 16px;
            border: 1px solid #e1e5eb;
            transition: all 0.3s;
        }

        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(78, 115, 223, 0.25);
        }

        .input-group-text {
            background: transparent;
            border-radius: 8px 0 0 8px;
            border: 1px solid #e1e5eb;
            border-right: none;
        }

        .form-floating>.form-control:focus~label,
        .form-floating>.form-control:not(:placeholder-shown)~label {
            color: var(--primary-color);
        }

        .btn-primary {
            background: var(--primary-color);
            border: none;
            padding: 12px;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s;
        }

        .btn-primary:hover {
            background: #3a5fca;
            transform: translateY(-2px);
        }

        .divider {
            display: flex;
            align-items: center;
            margin: 20px 0;
        }

        .divider::before,
        .divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #e1e5eb;
        }

        .divider span {
            padding: 0 10px;
            color: #8a93a2;
            font-size: 14px;
        }

        .alert {
            border-radius: 8px;
            padding: 12px 16px;
        }

        .auth-footer {
            text-align: center;
            padding: 20px 0 0;
            color: #8a93a2;
            font-size: 14px;
        }

        .auth-footer a {
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 600;
            transition: all 0.3s;
        }

        .auth-footer a:hover {
            color: var(--success-color);
        }

        .password-toggle {
            cursor: pointer;
            background: transparent;
            border: 1px solid #e1e5eb;
            border-left: none;
            border-radius: 0 8px 8px 0;
        }

        .animate-fade-in {
            animation: fadeIn 0.6s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>
</head>
<body>
<div class="auth-container animate-fade-in">
    <div class="auth-card">
        <div class="auth-header">
            <h2 class="mb-0"><i class="bi bi-box-arrow-in-right me-2"></i>Connexion</h2>
            <p class="mb-0 mt-2 opacity-75">Accédez à votre espace personnel</p>
        </div>

        <div class="auth-body">
            <c:if test="${not empty param.logout}">
                <div class="alert alert-success d-flex align-items-center" role="alert">
                    <i class="bi bi-check-circle-fill me-2"></i>
                    <div>Vous avez été déconnecté avec succès.</div>
                </div>
            </c:if>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger d-flex align-items-center" role="alert">
                    <i class="bi bi-exclamation-triangle-fill me-2"></i>
                    <div>${errorMessage}</div>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="form-floating mb-3">
                    <input type="email" class="form-control" id="email" name="email" placeholder="name@example.com" required>
                    <label for="email"><i class="bi bi-envelope me-2"></i>Adresse email</label>
                </div>

                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input type="password" class="form-control" id="password" name="password" placeholder="Mot de passe" required>
                        <label for="password"><i class="bi bi-key me-2"></i>Mot de passe</label>
                    </div>
                    <span class="input-group-text password-toggle" id="passwordToggle">
                        <i class="bi bi-eye"></i>
                    </span>
                </div>

                <div class="d-grid gap-2 mb-3">
                    <button type="submit" class="btn btn-primary btn-lg">
                        <i class="bi bi-box-arrow-in-right me-2"></i>Se connecter
                    </button>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="rememberMe" name="remember-me">
                    <label class="form-check-label" for="rememberMe">Se souvenir de moi</label>
                    <a href="#" class="float-end text-decoration-none">Mot de passe oublié?</a>
                </div>
            </form>

            <div class="divider">
                <span>Ou</span>
            </div>

            <div class="d-grid gap-2">
                <a href="${pageContext.request.contextPath}/register" class="btn btn-outline-primary">
                    <i class="bi bi-person-plus me-2"></i>Créer un nouveau compte
                </a>
            </div>
        </div>

        <div class="auth-footer">
            <p>© 2023 Gestion Location. Tous droits réservés.</p>
        </div>
    </div>
</div>

<!-- Bootstrap & JavaScript -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Fonction pour basculer la visibilité du mot de passe
    document.getElementById('passwordToggle').addEventListener('click', function() {
        const passwordInput = document.getElementById('password');
        const icon = this.querySelector('i');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            icon.classList.remove('bi-eye');
            icon.classList.add('bi-eye-slash');
        } else {
            passwordInput.type = 'password';
            icon.classList.remove('bi-eye-slash');
            icon.classList.add('bi-eye');
        }
    });
</script>
</body>
</html>