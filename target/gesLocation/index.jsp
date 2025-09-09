<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%
    // Vérification de session
    Utilisateur user = (Utilisateur) session.getAttribute("user");
    if(user == null || !user.getRole().equals("UTILISATEUR")){
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil - Locataire</title>
    <!-- Bootstrap & FontAwesome -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 0;
            background: linear-gradient(135deg, #1c1c1c, #2c3e50);
            color: #fff;
        }
        .navbar-brand { font-weight: bold; color: #ff4b2b !important; }
        .btn-logout {
            background: linear-gradient(90deg, #ff416c, #ff4b2b);
            border: none;
            border-radius: 50px;
            font-weight: bold;
            padding: 8px 20px;
            transition: 0.3s;
        }
        .btn-logout:hover { background: linear-gradient(90deg, #ff4b2b, #ff416c); }

        .hero {
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            text-align: center;
            height: 90vh;
            background: url('https://images.unsplash.com/photo-1581090700227-bf3f3ff0d0e1?auto=format&fit=crop&w=1470&q=80') no-repeat center center/cover;
            position: relative;
        }
        .hero::after {
            content: "";
            position: absolute;
            top:0; left:0; width:100%; height:100%;
            background-color: rgba(0,0,0,0.6);
        }
        .hero-content { position: relative; z-index:2; }
        .hero h1 {
            font-size: 3rem;
            font-weight: bold;
            margin-bottom: 20px;
            background: linear-gradient(90deg, #ff416c, #ff4b2b);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
        }
        .hero p { font-size: 1.2rem; margin-bottom: 30px; }
        .hero a { font-size: 1.1rem; }

        .features {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 30px;
            padding: 60px 20px;
            background: #121212;
        }
        .feature-card {
            background: rgba(255,255,255,0.05);
            padding: 30px;
            border-radius: 20px;
            width: 250px;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .feature-card:hover { transform: translateY(-10px); box-shadow: 0 10px 25px rgba(0,0,0,0.5); }
        .feature-card i { font-size: 3rem; color: #ff4b2b; margin-bottom: 15px; }
        .feature-card h5 { font-weight: bold; margin-bottom: 10px; }
        .feature-card p { color: #ccc; font-size: 0.95rem; }

        footer { text-align: center; padding: 20px; background-color: #1a1a1a; color: #aaa; }
    </style>
</head>
<body>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">GesLocation</a>
        <div class="d-flex align-items-center gap-3">
            <span>Bonjour tres cher (e), <%= user.getNom() %> (<%= user.getRole() %>)</span>
            <a class="btn btn-logout" href="AuthServlet?logout=true">Se deconnecter</a>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<section class="hero">
    <div class="hero-content">
        <h1>Bienvenue <%= user.getNom() %> !</h1>
        <p>Consultez les offres de location, envoyez vos demandes et suivez vos contrats et paiements.</p>
        <a href="immeuble/liste.jsp" class="btn btn-primary btn-lg">
            <i class="fa-solid fa-arrow-right"></i> Voir les Immeubles
        </a>
    </div>
</section>

<!-- Features Section -->
<section class="features">
    <div class="feature-card">
        <i class="fa-solid fa-building"></i>
        <h5>Consulter les Immeubles</h5>
        <p>Voir la liste des immeubles et unités disponibles avec details sur prix, localisation et nombre de pieces.</p>
        <a href="immeuble/liste.jsp" class="btn btn-primary mt-2"><i class="fa-solid fa-arrow-right"></i> Consulter</a>
    </div>
    <div class="feature-card">
        <i class="fa-solid fa-file-lines"></i>
        <h5>Envoyer une Demande</h5>
        <p>Faites une demande de location pour une unite specifique et suivez son statut.</p>
        <a href="demande/form.jsp" class="btn btn-primary mt-2"><i class="fa-solid fa-arrow-right"></i> Demander</a>
    </div>
    <div class="feature-card">
        <i class="fa-solid fa-file-contract"></i>
        <h5>Mes Contrats</h5>
        <p>Consultez vos contrats de location actifs et archives.</p>
        <a href="contrat/liste.jsp" class="btn btn-primary mt-2"><i class="fa-solid fa-arrow-right"></i> Voir Contrats</a>
    </div>
    <div class="feature-card">
        <i class="fa-solid fa-money-bill-wave"></i>
        <h5>Paiements</h5>
        <p>Suivez l’historique de vos paiements et vos reçus.</p>
        <a href="paiement/liste.jsp" class="btn btn-primary mt-2"><i class="fa-solid fa-arrow-right"></i> Voir Paiements</a>
    </div>
</section>

<!-- Footer -->
<footer>
    &copy; Contactez-nous : +221 77 594 58 84 | +221 33 856 88 88
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
