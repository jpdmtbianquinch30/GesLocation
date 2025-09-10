<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulter les Offres</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
</head>
<body>
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="main-content">
    <div class="page-header">
        <h1>Consulter les Offres de Location</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Filtres de recherche -->
    <div class="filters-section">
        <h3>Filtrer les résultats</h3>
        <form method="get" class="filters-form">
            <div class="filter-row">
                <div class="filter-group">
                    <label for="prixMin">Prix min (€):</label>
                    <input type="number" id="prixMin" name="prixMin" value="${filtres.prixMin}" min="0" step="50">
                </div>

                <div class="filter-group">
                    <label for="prixMax">Prix max (€):</label>
                    <input type="number" id="prixMax" name="prixMax" value="${filtres.prixMax}" min="0" step="50">
                </div>

                <div class="filter-group">
                    <label for="pieces">Nombre de pièces:</label>
                    <input type="number" id="pieces" name="pieces" value="${filtres.pieces}" min="1" max="10">
                </div>

                <div class="filter-group">
                    <label for="superficie">Superficie min (m²):</label>
                    <input type="number" id="superficie" name="superficie" value="${filtres.superficie}" min="0" step="5">
                </div>
            </div>

            <div class="filter-actions">
                <button type="submit" class="btn btn-primary">Appliquer les filtres</button>
                <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-secondary">Réinitialiser</a>
            </div>
        </form>
    </div>

    <!-- Liste des offres -->
    <div class="offres-grid">
        <c:forEach var="offre" items="${offres}">
            <div class="offre-card">
                <div class="offre-image">
                    <img src="${pageContext.request.contextPath}/assets/img/appartment-placeholder.jpg"
                         alt="Appartement ${offre.numeroUnite}">
                </div>

                <div class="offre-content">
                    <div class="offre-header">
                        <h3>Appartement ${offre.numeroUnite}</h3>
                        <span class="offre-price">${offre.loyerMensuel} €/mois</span>
                    </div>

                    <div class="offre-details">
                        <div class="detail-item">
                            <span class="detail-icon">🏢</span>
                            <span>${offre.immeuble.nom}</span>
                        </div>

                        <div class="detail-item">
                            <span class="detail-icon">📍</span>
                            <span>${offre.immeuble.adresse}</span>
                        </div>

                        <div class="detail-item">
                            <span class="detail-icon">🚪</span>
                            <span>${offre.nombrePieces} pièce(s)</span>
                        </div>

                        <div class="detail-item">
                            <span class="detail-icon">📐</span>
                            <span>${offre.superficie} m²</span>
                        </div>
                    </div>

                    <div class="offre-description">
                        <p>${offre.description}</p>
                    </div>

                    <div class="offre-actions">
                        <a href="${pageContext.request.contextPath}/locataire/demande-location?uniteId=${offre.id}"
                           class="btn btn-primary">Faire une demande</a>
                        <button class="btn btn-secondary" onclick="showDetails(${offre.id})">Voir détails</button>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty offres}">
        <div class="no-results">
            <h3>Aucune offre ne correspond à vos critères</h3>
            <p>Essayez de modifier vos filtres de recherche.</p>
        </div>
    </c:if>
</div>

<!-- Modal de détails -->
<div class="modal-overlay" id="detailsModal" style="display: none;">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Détails de l'offre</h2>
            <button class="modal-close" onclick="closeModal()">&times;</button>
        </div>
        <div class="modal-body" id="modalBody">
            <!-- Contenu chargé dynamiquement -->
        </div>
    </div>
</div>

<script>
    function showDetails(uniteId) {
        // Charger les détails de l'unité via AJAX
        fetch('${pageContext.request.contextPath}/api/unites/' + uniteId)
            .then(response => response.json())
            .then(data => {
                const modalBody = document.getElementById('modalBody');
                modalBody.innerHTML = `
                        <div class="offre-details-modal">
                            <h3>Appartement \${data.numeroUnite}</h3>
                            <p><strong>Immeuble:</strong> \${data.immeuble.nom}</p>
                            <p><strong>Adresse:</strong> \${data.immeuble.adresse}</p>
                            <p><strong>Loyer:</strong> \${data.loyerMensuel} €/mois</p>
                            <p><strong>Pièces:</strong> \${data.nombrePieces}</p>
                            <p><strong>Superficie:</strong> \${data.superficie} m²</p>
                            <p><strong>Description:</strong> \${data.description}</p>
                            <p><strong>Équipements:</strong> \${data.immeuble.equipements}</p>
                        </div>
                    `;
                document.getElementById('detailsModal').style.display = 'flex';
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Impossible de charger les détails');
            });
    }

    function closeModal() {
        document.getElementById('detailsModal').style.display = 'none';
    }

    // Fermer la modal en cliquant à l'extérieur
    document.addEventListener('click', function(event) {
        const modal = document.getElementById('detailsModal');
        if (event.target === modal) {
            closeModal();
        }
    });
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>