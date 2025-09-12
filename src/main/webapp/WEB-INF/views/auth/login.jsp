<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion - Gestion Location</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <style>
        body {
            margin: 0;
            font-family: Arial, Helvetica, sans-serif;
            background: linear-gradient(135deg, #4e73df, #1cc88a);
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .auth-container {
            width: 100%;
            max-width: 400px;
            padding: 20px;
        }

        .auth-form {
            background: #fff;
            border-radius: 12px;
            padding: 30px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.15);
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

        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 8px;
            outline: none;
            transition: 0.3s;
        }

        input:focus {
            border-color: #4e73df;
            box-shadow: 0 0 6px rgba(78,115,223,0.3);
        }

        .btn-primary {
            width: 100%;
            padding: 12px;
            background: #4e73df;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.3s;
        }

        .btn-primary:hover {
            background: #2e59d9;
        }

        .alert {
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
            font-size: 14px;
        }

        .alert-success {
            background: #d4edda;
            color: #155724;
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
            color: #1cc88a;
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
    </style>
</head>
<body>
<div class="auth-container">
    <div class="auth-form">
        <h2>Connexion</h2>

        <c:if test="${not empty param.logout}">
            <div class="alert alert-success">Vous avez été déconnecté avec succès.</div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="form-group">
                <label for="email">Adresse email</label>
                <input type="email" id="email" name="email" placeholder="Entrez votre email" required>
            </div>

            <div class="form-group">
                <label for="password">Mot de passe</label>
                <input type="password" id="password" name="password" placeholder="Entrez votre mot de passe" required>
            </div>

            <button type="submit" class="btn btn-primary">Se connecter</button>
        </form>

        <p>Pas encore de compte ? <a href="${pageContext.request.contextPath}/register">Créer un compte</a></p>
    </div>
</div>
</body>
</html>
