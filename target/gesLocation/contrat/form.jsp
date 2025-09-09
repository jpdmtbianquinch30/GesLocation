<%@ page import="com.gestion.location.entities.Contrat" %>
<%@ page import="com.gestion.location.entities.Utilisateur" %>
<%@ page import="com.gestion.location.entities.Unite" %>
<%@ page import="java.util.List" %>

<%
    Contrat contrat = (Contrat) request.getAttribute("contrat");
    List<Utilisateur> locataires = (List<Utilisateur>) request.getAttribute("locataires");
    List<Unite> unites = (List<Unite>) request.getAttribute("unites");
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><%= contrat != null ? "✏ Modifier Contrat" : "➕ Ajouter Contrat" %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card shadow-lg border-0 rounded-4">
                <div class="card-header bg-primary text-white text-center rounded-top-4">
                    <h3><%= contrat != null ? "✏ Modifier Contrat" : "➕ Ajouter un Nouveau Contrat" %></h3>
                </div>
                <div class="card-body p-4">
                    <form action="<%= request.getContextPath() %>/contrats" method="post" class="needs-validation" novalidate>
                        <% if (contrat != null) { %>
                        <input type="hidden" name="id" value="<%= contrat.getId() %>"/>
                        <% } %>

                        <!-- Locataire -->
                        <div class="mb-3 d-flex justify-content-between align-items-center">
                            <label for="locataire" class="form-label fw-bold">👤 Locataire</label>
                            <a href="<%= request.getContextPath() %>/locataires?action=add&fromContrat=true"
                               class="btn btn-sm btn-primary">➕ Nouveau locataire</a>
                        </div>

                        <select class="form-select mb-3" id="locataire" name="locataireId" required>
                            <option value="">-- Sélectionner un locataire --</option>
                            <% if (locataires != null) {
                                for (Utilisateur l : locataires) {
                                    boolean selected = contrat != null && contrat.getLocataire() != null
                                            && contrat.getLocataire().getId().equals(l.getId());
                            %>
                            <option value="<%= l.getId() %>" <%= selected ? "selected" : "" %>>
                                <%= l.getNom() + " " + l.getPrenom() %>
                            </option>
                            <%  }
                            } %>
                        </select>
                        <div class="invalid-feedback">Veuillez sélectionner un locataire.</div>
                        <!-- Unité -->
                        <div class="mb-3">
                            <label for="unite" class="form-label fw-bold">🏠 Unité</label>
                            <select class="form-select" id="unite" name="uniteId" required>
                                <option value="">-- Sélectionner une unité --</option>
                                <% for (Unite u : unites) { %>
                                <option value="<%= u.getId() %>"
                                        <%= contrat != null && contrat.getUnite() != null
                                                && contrat.getUnite().getId().equals(u.getId()) ? "selected" : "" %>>
                                    <%= u.getNumero() %>
                                </option>
                                <% } %>
                            </select>
                            <div class="invalid-feedback">Veuillez sélectionner une unité.</div>
                        </div>

                        <!-- Dates -->
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label for="dateDebut" class="form-label fw-bold">📅 Date début</label>
                                <input type="date" class="form-control" id="dateDebut" name="dateDebut"
                                       value="<%= contrat != null && contrat.getDateDebut() != null ? contrat.getDateDebut().toString() : "" %>" required>
                                <div class="invalid-feedback">Veuillez choisir une date de début.</div>
                            </div>
                            <div class="col-md-6 mb-3">
                                <label for="dateFin" class="form-label fw-bold">📅 Date fin</label>
                                <input type="date" class="form-control" id="dateFin" name="dateFin"
                                       value="<%= contrat != null && contrat.getDateFin() != null ? contrat.getDateFin().toString() : "" %>" required>
                                <div class="invalid-feedback">Veuillez choisir une date de fin.</div>
                            </div>
                        </div>

                        <!-- Boutons -->
                        <div class="d-flex justify-content-between mt-4">
                            <a href="<%= request.getContextPath() %>/contrats" class="btn btn-secondary px-4">⬅ Annuler</a>
                            <button type="submit" class="btn btn-success px-4">
                                <%= contrat != null ? "💾 Enregistrer Modifications" : "➕ Ajouter Contrat" %>
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    (() => {
        'use strict'
        const forms = document.querySelectorAll('.needs-validation')
        Array.from(forms).forEach(form => {
            form.addEventListener('submit', event => {
                if (!form.checkValidity()) {
                    event.preventDefault()
                    event.stopPropagation()
                }
                form.classList.add('was-validated')
            }, false)
        })
    })()
</script>
</body>
</html>
