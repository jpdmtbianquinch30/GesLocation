<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription - Gestion Location</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Arial, sans-serif;
            background: #f4f6f9;
            height: 100vh;
            display: flex;
        }

        .container {
            display: flex;
            width: 100%;
            height: 100vh;
        }

        /* Partie gauche avec un fond dégradé + illustration */
        .left-panel {
            flex: 1;
            background: linear-gradient(135deg, #36b9cc, #1cc88a);
            color: white;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-direction: column;
            padding: 40px;
        }

        .left-panel h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
        }

        .left-panel p {
            font-size: 1.1rem;
            max-width: 400px;
            text-align: center;
            line-height: 1.5;
        }

        /* Partie droite avec le formulaire */
        .auth-container {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            background: #fff;
        }

        .auth-form {
            width: 100%;
            max-width: 450px;
            padding: 40px;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
            animation: fadeIn 0.6s ease-in-out;
        }

        .auth-form h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #333;
        }

        .form-group {
            margin-bottom: 18px;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
            color: #444;
        }

        input[type="text"], input[type="email"], input[type="tel"],
        input[type="password"], select {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            outline: none;
            transition: 0.3s;
            font-size: 14px;
        }

        input:focus, select:focus {
            border-color: #36b9cc;
            box-shadow: 0 0 6px rgba(54,185,204,0.3);
        }

        .btn-primary {
            width: 100%;
            padding: 12px;
            background: #1cc88a;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn-primary:hover {
            background: #17a673;
        }

        .alert {
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }

        .alert-danger {
            background: #f8d7da;
            color: #721c24;
        }

        p {
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
        }

        p a {
            color: #36b9cc;
            font-weight: bold;
            text-decoration: none;
        }

        p a:hover {
            text-decoration: underline;
        }

        @keyframes fadeIn {
            from {opacity: 0; transform: translateY(-20px);}
            to {opacity: 1; transform: translateY(0);}
        }

        /* Responsive mobile */
        @media(max-width: 900px) {
            .container {
                flex-direction: column;
            }
            .left-panel {
                display: none;
            }
            .auth-container {
                flex: none;
                width: 100%;
                height: 100vh;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Partie gauche -->
    <div class="left-panel">
        <h1>Bienvenue 👋</h1>
        <p>Inscrivez-vous pour accéder à la plateforme de gestion de location et profitez d’une expérience simple et intuitive.</p>
    </div>

    <!-- Partie droite -->
    <div class="auth-container">
        <div class="auth-form">
            <h2>Créer un compte</h2>

            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">${errorMessage}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="form-group">
                    <label for="nom">Nom</label>
                    <input type="text" id="nom" name="nom" placeholder="Votre nom" required>
                </div>

                <div class="form-group">
                    <label for="prenom">Prénom</label>
                    <input type="text" id="prenom" name="prenom" placeholder="Votre prénom" required>
                </div>

                <div class="form-group">
                    <label for="email">Adresse email</label>
                    <input type="email" id="email" name="email" placeholder="exemple@email.com" required>
                </div>

                <div class="form-group">
                    <label for="telephone">Téléphone</label>
                    <input type="tel" id="telephone" name="telephone" placeholder="77 123 45 67" required>
                </div>

                <div class="form-group">
                    <label for="role">Je suis</label>
                    <select id="role" name="role" required>
                        <option value="">-- Sélectionnez --</option>
                        <option value="LOCATAIRE">Locataire</option>
                        <option value="PROPRIETAIRE">Propriétaire</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input type="password" id="password" name="password" placeholder="********" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirmer le mot de passe</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="********" required>
                </div>

                <button type="submit" class="btn btn-primary">S'inscrire</button>
            </form>

            <p>Déjà inscrit ? <a href="${pageContext.request.contextPath}/login">Se connecter</a></p>
        </div>
    </div>
</div>
</body>
</html>
