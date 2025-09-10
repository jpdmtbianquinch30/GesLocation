<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mes Paiements</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Mes Paiements</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Résumé financier -->
    <div class="financial-summary">
        <div class="summary-card">
            <h3>Total payé</h3>
            <p class="summary-amount"><fmt:formatNumber value="${totalPaye}" type="currency"/></p>
        </div>
    </div>

    <!-- Filtre par statut -->
    <div class="filter-section">
        <form method="get" class="filter-form">
            <label for="statut">Filtrer par statut:</label>
            <select id="statut" name="statut" onchange="this.form.submit()">
                <option value="">Tous les statuts</option>
                <option value="EN_ATTENTE" ${selectedStatut == 'EN_ATTENTE' ? 'selected' : ''}>En attente</option>
                <option value="VALIDE" ${selectedStatut == 'VALIDE' ? 'selected' : ''}>Validés</option>
                <option value="REFUSE" ${selectedStatut == 'REFUSE' ? 'selected' : ''}>Refusés</option>
            </select>
        </form>
    </div>

    <!-- Liste des paiements -->
    <div class="paiements-list">
        <c:forEach var="paiement" items="${paiements}">
            <div class="paiement-card ${paiement.statutPaiement.toLowerCase()}">
                <div class="paiement-header">
                    <h4>Paiement #${paiement.id}</h4>
                    <span class="statut-badge ${paiement.statutPaiement.toLowerCase()}">
                            ${paiement.statutPaiement}
                    </span>
                </div>

                <div class="paiement-details">
                    <div class="detail-row">
                        <span class="detail-label">Montant:</span>
                        <span class="detail-value"><fmt:formatNumber value="${paiement.montant}" type="currency"/></span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Mois couvert:</span>
                        <span class="detail-value">${paiement.moisCouvert}</span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Date de paiement:</span>
                        <span class="detail-value">
                                <fmt:formatDate value="${paiement.datePaiement}" pattern="dd/MM/yyyy"/>
                            </span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Méthode:</span>
                        <span class="detail-value">${paiement.methodePaiement}</span>
                    </div>

                    <div class="detail-row">
                        <span class="detail-label">Contrat:</span>
                        <span class="detail-value">#${paiement.contrat.id} - ${paiement.contrat.unite.numeroUnite}</span>
                    </div>
                </div>

                <c:if test="${paiement.statutPaiement == 'EN_ATTENTE'}">
                    <div class="paiement-actions">
                        <button class="btn btn-primary" onclick="effectuerPaiement(${paiement.id})">
                            Effectuer le paiement
                        </button>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty paiements}">
        <div class="no-paiements">
            <h3>Aucun paiement trouvé</h3>
            <p>Vous n'avez aucun paiement correspondant à vos critères.</p>
        </div>
    </c:if>

    <!-- Prochain paiement -->
    <c:if test="${not empty prochainPaiement}">
        <div class="next-payment-section">
            <h2>Prochain Paiement à Effectuer</h2>
            <div class="next-payment-card">
                <div class="payment-info">
                    <h4>Paiement pour ${prochainPaiement.moisCouvert}</h4>
                    <p><strong>Montant:</strong> <fmt:formatNumber value="${prochainPaiement.montant}" type="currency"/></p>
                    <p><strong>Date limite:</strong> <fmt:formatDate value="${prochainPaiement.datePaiement}" pattern="dd/MM/yyyy"/></p>
                    <p><strong>Contrat:</strong> #${prochainPaiement.contrat.id} - ${prochainPaiement.contrat.unite.numeroUnite}</p>
                </div>
                <div class="payment-actions">
                    <button class="btn btn-primary" onclick="effectuerPaiement(${prochainPaiement.id})">
                        Payer maintenant
                    </button>
                </div>
            </div>
        </div>
    </c:if>
</div>

<!-- Modal de paiement -->
<div class="modal-overlay" id="paiementModal" style="display: none;">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Effectuer un Paiement</h2>
            <button class="modal-close" onclick="closePaiementModal()">&times;</button>
        </div>
        <div class="modal-body">
            <form id="paiementForm">
                <input type="hidden" id="paiementId">

                <div class="form-group">
                    <label for="methodePaiement">Méthode de paiement:</label>
                    <select id="methodePaiement" required>
                        <option value="">Sélectionnez une méthode</option>
                        <option value="CARTE">Carte bancaire</option>
                        <option value="VIREMENT">Virement</option>
                        <option value="CHEQUE">Chèque</option>
                    </select>
                </div>

                <div id="carteFields" style="display: none;">
                    <div class="form-group">
                        <label for="numeroCarte">Numéro de carte:</label>
                        <input type="text" id="numeroCarte" placeholder="1234 5678 9012 3456">
                    </div>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="dateExpiration">Date d'expiration:</label>
                            <input type="text" id="dateExpiration" placeholder="MM/AA">
                        </div>
                        <div class="form-group">
                            <label for="cvv">CVV:</label>
                            <input type="text" id="cvv" placeholder="123">
                        </div>
                    </div>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Confirmer le paiement</button>
                    <button type="button" class="btn btn-secondary" onclick="closePaiementModal()">Annuler</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    let currentPaiementId = null;

    function effectuerPaiement(paiementId) {
        currentPaiementId = paiementId;
        document.getElementById('paiementId').value = paiementId;
        document.getElementById('paiementModal').style.display = 'flex';
    }

    function closePaiementModal() {
        document.getElementById('paiementModal').style.display = 'none';
        document.getElementById('paiementForm').reset();
        currentPaiementId = null;
    }

    // Afficher/masquer les champs carte
    document.getElementById('methodePaiement').addEventListener('change', function() {
        const carteFields = document.getElementById('carteFields');
        carteFields.style.display = this.value === 'CARTE' ? 'block' : 'none';
    });

    // Soumission du formulaire
    document.getElementById('paiementForm').addEventListener('submit', function(e) {
        e.preventDefault();

        // Simulation de paiement
        alert('Paiement effectué avec succès ! (Cette fonctionnalité serait connectée à un vrai système de paiement)');

        // Rediriger vers la page de confirmation
        window.location.href = '${pageContext.request.contextPath}/locataire/paiements?statut=VALIDE';
    });

    // Fermer la modal en cliquant à l'extérieur
    document.addEventListener('click', function(event) {
        const modal = document.getElementById('paiementModal');
        if (event.target === modal) {
            closePaiementModal();
        }
    });
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>