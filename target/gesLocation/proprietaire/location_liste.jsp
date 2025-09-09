<h2>Demandes de Location</h2>
<table border="1">
    <tr>
        <th>Locataire</th>
        <th>Immeuble</th>
        <th>Unité</th>
        <th>Date de début</th>
        <th>Date de fin</th>
        <th>Statut</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="c" items="${contrats}">
        <tr>
            <td>${c.locataire.nom}</td>
            <td>${c.immeuble.nom}</td>
            <td>${c.unite.numero}</td>
            <td>${c.dateDebut}</td>
            <td>${c.dateFin}</td>
            <td>${c.statut}</td>
            <td>
                <c:if test="${c.statut == 'EN_ATTENTE'}">
                    <a href="locations?action=approuver&id=${c.id}">Approuver</a>
                    <a href="locations?action=refuser&id=${c.id}">Refuser</a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Paiements reçus</h2>
<table border="1">
    <tr>
        <th>Locataire</th>
        <th>Immeuble</th>
        <th>Unité</th>
        <th>Date de paiement</th>
        <th>Montant</th>
        <th>Statut</th>
    </tr>
    <c:forEach var="p" items="${paiements}">
        <tr>
            <td>${p.contrat.locataire.nom}</td>
            <td>${p.contrat.immeuble.nom}</td>
            <td>${p.contrat.unite.numero}</td>
            <td>${p.datePaiement}</td>
            <td>${p.montant}</td>
            <td>${p.statut}</td>
        </tr>
    </c:forEach>
</table>
