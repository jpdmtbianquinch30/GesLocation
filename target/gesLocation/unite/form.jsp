<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gestion.location.entities.Unite" %>

<%
    Unite unite = (Unite) request.getAttribute("unite");
    boolean isEdit = unite != null;
%>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title><c:out value="${isEdit ? 'Modifier' : 'Ajouter'}"/> Unite</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card shadow-sm border-0 rounded-4">
                <div class="card-header bg-primary text-white text-center rounded-top-4">
                    <h3 class="mb-0"><c:out value="${isEdit ? 'Modifier' : 'Ajouter'}"/> Unite</h3>
                </div>
                <div class="card-body p-4">
                    <form action="<%= request.getContextPath() %>/unites" method="post">

                    <c:if test="${isEdit}">
                            <input type="hidden" name="id" value="${unite.id}">
                        </c:if>

                        <div class="mb-3">
                            <label class="form-label">Numero</label>
                            <input type="text" name="numero" class="form-control" required
                                   value="${isEdit ? unite.numero : ''}">
                            <div class="invalid-feedback">Veuillez entrer le numero.</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Pieces</label>
                            <input type="number" name="pieces" class="form-control" required
                                   value="${isEdit ? unite.pieces : ''}">
                            <div class="invalid-feedback">Veuillez entrer le nombre de pieces.</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Superficie</label>
                            <input type="text" name="superficie" class="form-control" required
                                   value="${isEdit ? unite.superficie : ''}">
                            <div class="invalid-feedback">Veuillez entrer la superficie.</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Loyer</label>
                            <input type="text" name="loyer" class="form-control" required
                                   value="${isEdit ? unite.loyer : ''}">
                            <div class="invalid-feedback">Veuillez entrer le loyer.</div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Immeuble</label>
                            <select name="immeubleId" class="form-select" required>
                                <option value="">-- Selectionner un immeuble --</option>
                                <c:forEach var="im" items="${immeubles}">
                                    <option value="${im.id}"
                                            <c:if test="${isEdit && unite.immeuble.id == im.id}">selected</c:if>>
                                            ${im.nom}
                                    </option>
                                </c:forEach>
                            </select>
                            <div class="invalid-feedback">Veuillez selectionner un immeuble.</div>
                        </div>

                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/contrats?action=list" class="btn btn-secondary">
                                Retour Contrats
                            </a>
                            <button type="submit" class="btn btn-success">
                                <c:out value="${isEdit ? 'Modifier' : 'Ajouter'}"/>
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
    // Bootstrap validation
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
