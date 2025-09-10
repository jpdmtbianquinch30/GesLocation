<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Demande de Location</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Demande de Location</h1>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <div class="demande-content">
        <c:if test="${not empty uniteSelectionnee}">
            <!-- Afficher les détails de l'unité sélectionnée -->
            <div class="unite-selectionnee">
                <h3>Unité sélectionnée</h3>
                <div class="unite-card">
                    <div class="unite-header">
                        <h4>Appartement ${uniteSelectionnee.numeroUnite}</h4>
                        <span class="unite-price">${uniteSelectionnee.loyerMensuel} €/mois</span>
                    </div>
                    <div class="unite-details">
                        <p><strong>Immeuble:</strong> ${uniteSelectionnee.immeuble.nom}</p>
                        <p><strong>Adresse:</strong> ${uniteSelectionnee.immeuble.adresse}</p>
                        <p><strong>Pièces:</strong> ${uniteSelectionnee.nombrePieces}</p>
                        <p><strong>Superficie:</strong> ${uniteSelectionnee.superficie} m²</p>
                        <p><strong>Description:</strong> ${uniteSelectionnee.description}</p>
                    </div>
                </div>
            </div>

            <!-- Formulaire de demande -->
            <div class="demande-form-container">
                <h3>Formulaire de demande</h3>
                <form action="${pageContext.request.contextPath}/locataire/demande-location" method="post">
                    <input type="hidden" name="uniteId" value="${uniteSelectionnee.id}">

                    <div class="form-group">
                        <label for="dateDebut">Date de début de location:</label>
                        <input type="date" id="dateDebut" name="dateDebut"
                               min="<fmt:formatDate value='<%= new java.util.Date() %>' pattern='yyyy-MM-dd'/>"
                               required>
                    </div>

                    <div class="form-group">
                        <label for="dateFin">Date de fin de location:</label>
                        <input type="date" id="dateFin" name="dateFin" required>
                    </div>

                    <div class="form-group">
                        <label for="message">Message au propriétaire (optionnel):</label>
                        <textarea id="message" name="message" rows="4"
                                  placeholder="Présentez-vous et expliquez pourquoi vous seriez un bon locataire..."></textarea>
                    </div>

                    <div class="form-summary">
                        <h4>Récapitulatif</h4>
                        <div class="summary-item">
                            <span>Loyer mensuel:</span>
                            <span>${uniteSelectionnee.loyerMensuel} €</span>
                        </div>
                        <div class="summary-item">
                            <span>Caution (2 mois):</span>
                            <span>${uniteSelectionnee.loyerMensuel * 2} €</span>
                        </div>
                        <div class="summary-item total">
                            <span>Total à prévoir:</span>
                            <span>${uniteSelectionnee.loyerMensuel * 3} €</span>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">Envoyer la demande</button>
                        <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-secondary">
                            Retour aux offres
                        </a>
                    </div>
                </form>
            </div>
        </c:if>

        <c:if test="${empty uniteSelectionnee}">
            <div class="no-unite-selected">
                <h3>Aucune unité sélectionnée</h3>
                <p>Veuillez sélectionner une unité depuis la page des offres pour faire une demande de location.</p>
                <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-primary">
                    Consulter les offres
                </a>
            </div>
        </c:if>
    </div>
</div>

<script>
    // Calcul automatique de la date de fin (1 an par défaut)
    document.getElementById('dateDebut').addEventListener('change', function() {
        const dateDebut = new Date(this.value);
        if (!isNaN(dateDebut.getTime())) {
            const dateFin = new Date(dateDebut);
            dateFin.setFullYear(dateFin.getFullYear() + 1);
            document.getElementById('dateFin').value = dateFin.toISOString().split('T')[0];
        }
    });
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>