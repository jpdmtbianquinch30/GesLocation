<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Enregistrer un Paiement</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>
<body>
<h2>Formulaire Paiement</h2>
<form action="paiement" method="post">
    <label>Montant :</label>
    <input type="number" step="0.01" name="montant" required><br>

    <label>Mode de Paiement :</label>
    <select name="mode" required>
        <option value="carte">Carte Bancaire</option>
        <option value="cash">Cash</option>
        <option value="mobile">Mobile Money</option>
    </select><br>

    <button type="submit">Enregistrer</button>
</form>

<br>
<a href="listePaiements.jsp">Voir la liste des paiements</a>
</body>
</html>
