<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.lang.Boolean" %>
<%
    // Vérifie si on vient du formulaire Contrat
    Boolean fromContrat = request.getAttribute("fromContrat") != null
            ? (Boolean) request.getAttribute("fromContrat")
            : false;
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>➕ Ajouter un Locataire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-lg border-0 rounded-4">
                <div class="card-header bg-primary text-white text-center rounded-top-4">
                    <h3 class="mb-0">➕ Ajouter un Locataire</h3>
                </div>
                <div class="card-body p-4">
                    <form action="<%= request.getContextPath() %>/locataires" method="post"
                          class="needs-validation" novalidate>

                        <!-- Champ caché pour savoir si on vient du formulaire Contrat -->
                        <input type="hidden" name="fromContrat" value="<%= fromContrat %>">

                        <!-- Nom -->
                        <div class="mb-3">
                            <label class="form-label fw-bold">👤 Nom</label>
                            <input type="text" class="form-control" name="nom" placeholder="Entrez le nom" required>
                            <div class="invalid-feedback">Veuillez entrer le nom du locataire.</div>
                        </div>

                        <!-- Prénom -->
                        <div class="mb-3">
                            <label class="form-label fw-bold">👤 Prénom</label>
                            <input type="text" class="form-control" name="prenom" placeholder="Entrez le prénom" required>
                            <div class="invalid-feedback">Veuillez entrer le prénom du locataire.</div>
                        </div>

                        <!-- Email -->
                        <div class="mb-3">
                            <label class="form-label fw-bold">📧 Email</label>
                            <input type="email" class="form-control" name="email" placeholder="exemple@mail.com" required>
                            <div class="invalid-feedback">Veuillez entrer un email valide.</div>
                        </div>

                        <!-- Téléphone -->
                        <div class="mb-3">
                            <label class="form-label fw-bold">📞 Téléphone</label>
                            <input type="text" class="form-control" name="telephone" placeholder="+221 77 000 00 00" required>
                            <div class="invalid-feedback">Veuillez entrer un numéro de téléphone.</div>
                        </div>

                        <!-- Boutons -->
                        <div class="d-flex justify-content-between mt-4">
                            <% if (fromContrat) { %>
                            <a href="<%= request.getContextPath() %>/contrats?action=list" class="btn btn-secondary px-4">⬅ Retour Contrats</a>
                            <% } else { %>
                            <a href="<%= request.getContextPath() %>/locataires" class="btn btn-secondary px-4">⬅ Retour Locataires</a>
                            <% } %>
                            <button type="submit" class="btn btn-success px-4">💾 Enregistrer</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Validation Bootstrap
    (() => {
        'use strict';
        const forms = document.querySelectorAll('.needs-validation');
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();
</script>

</body>
</html>
