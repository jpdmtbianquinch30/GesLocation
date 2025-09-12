<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Consulter les Offres</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/locataire.css">
</head>
<body class="bg-light">
<%@ include file="/WEB-INF/views/shared/header.jsp" %>
<%@ include file="/WEB-INF/views/shared/sidebar.jsp" %>

<div class="container-fluid main-content py-4">
    <div class="page-header mb-4">
        <h1 class="fw-bold text-primary">🏠 Consulter les Offres de Location</h1>
    </div>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Filtres de recherche -->
    <div class="card shadow-sm mb-4">
        <div class="card-header bg-primary text-white">
            <h5 class="mb-0">🔍 Filtrer les résultats</h5>
        </div>
        <div class="card-body">
            <form method="get" class="row g-3">
                <div class="col-md-3">
                    <label for="prixMin" class="form-label">Prix min (€)</label>
                    <input type="number" class="form-control" id="prixMin" name="prixMin"
                           value="${filtres.prixMin}" min="0" step="50">
                </div>
                <div class="col-md-3">
                    <label for="prixMax" class="form-label">Prix max (€)</label>
                    <input type="number" class="form-control" id="prixMax" name="prixMax"
                           value="${filtres.prixMax}" min="0" step="50">
                </div>
                <div class="col-md-3">
                    <label for="pieces" class="form-label">Nombre de pièces</label>
                    <input type="number" class="form-control" id="pieces" name="pieces"
                           value="${filtres.pieces}" min="1" max="10">
                </div>
                <div class="col-md-3">
                    <label for="superficie" class="form-label">Superficie min (m²)</label>
                    <input type="number" class="form-control" id="superficie" name="superficie"
                           value="${filtres.superficie}" min="0" step="5">
                </div>
                <div class="col-12 text-end mt-3">
                    <button type="submit" class="btn btn-primary me-2">Appliquer</button>
                    <a href="${pageContext.request.contextPath}/locataire/offres" class="btn btn-outline-secondary">Réinitialiser</a>
                </div>
            </form>
        </div>
    </div>

    <!-- Liste des offres -->
    <div class="row g-4">
        <c:forEach var="offre" items="${offres}">
            <div class="col-md-6 col-lg-4">
                <div class="card h-100 shadow-sm">
                    <img src="${pageContext.request.contextPath}/assets/img/appartment-placeholder.jpg"
                         class="card-img-top" alt="Appartement ${offre.numeroUnite}">
                    <div class="card-body">
                        <div class="d-flex justify-content-between align-items-center mb-2">
                            <h5 class="card-title mb-0">Appartement ${offre.numeroUnite}</h5>
                            <span class="badge bg-success fs-6">${offre.loyerMensuel} €/mois</span>
                        </div>
                        <ul class="list-unstyled small text-muted mb-3">
                            <li>🏢 ${offre.immeuble.nom}</li>
                            <li>📍 ${offre.immeuble.adresse}</li>
                            <li>🚪 ${offre.nombrePieces} pièce(s)</li>
                            <li>📐 ${offre.superficie} m²</li>
                        </ul>
                        <p class="card-text">${offre.description}</p>
                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/locataire/demande-location?uniteId=${offre.id}"
                               class="btn btn-primary btn-sm">Faire une demande</a>
                            <button class="btn btn-outline-secondary btn-sm" onclick="showDetails(${offre.id})">
                                Voir détails
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty offres}">
        <div class="alert alert-warning mt-4 text-center">
            <h5>Aucune offre ne correspond à vos critères</h5>
            <p>Essayez de modifier vos filtres de recherche.</p>
        </div>
    </c:if>
</div>

<!-- Modal de détails (Bootstrap modal au lieu du custom) -->
<div class="modal fade" id="detailsModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title">Détails de l'offre</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body" id="modalBody">
                <!-- Contenu chargé dynamiquement -->
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function showDetails(uniteId) {
        fetch('${pageContext.request.contextPath}/api/unites/' + uniteId)
            .then(response => response.json())
            .then(data => {
                const modalBody = document.getElementById('modalBody');
                modalBody.innerHTML = `
                    <h4>Appartement \${data.numeroUnite}</h4>
                    <ul>
                        <li><strong>Immeuble:</strong> \${data.immeuble.nom}</li>
                        <li><strong>Adresse:</strong> \${data.immeuble.adresse}</li>
                        <li><strong>Loyer:</strong> \${data.loyerMensuel} €/mois</li>
                        <li><strong>Pièces:</strong> \${data.nombrePieces}</li>
                        <li><strong>Superficie:</strong> \${data.superficie} m²</li>
                        <li><strong>Description:</strong> \${data.description}</li>
                        <li><strong>Équipements:</strong> \${data.immeuble.equipements}</li>
                    </ul>
                `;
                const modal = new bootstrap.Modal(document.getElementById('detailsModal'));
                modal.show();
            })
            .catch(error => {
                console.error('Erreur:', error);
                alert('Impossible de charger les détails');
            });
    }
</script>

<%@ include file="/WEB-INF/views/shared/footer.jsp" %>
</body>
</html>
