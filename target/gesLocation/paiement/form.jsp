<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter Paiement</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Ajouter Paiement</h2>

    <form action="<%= request.getContextPath() %>/paiement" method="post">
        <div class="mb-3">
            <label for="montant" class="form-label">Montant</label>
            <input type="number" step="0.01" class="form-control" id="montant" name="montant" required>
        </div>

        <div class="mb-3">
            <label for="mode" class="form-label">Mode de paiement</label>
            <select class="form-select" id="mode" name="mode" required>
                <option value="">-- Sélectionner un mode --</option>
                <option value="Carte">Carte</option>
                <option value="Cash">Cash</option>
                <option value="Mobile Money">Mobile Money</option>
            </select>
        </div>

        <button type="submit" class="btn btn-success">Enregistrer</button>
        <a href="<%= request.getContextPath() %>/paiement?action=dashboard" class="btn btn-secondary">Annuler</a>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
