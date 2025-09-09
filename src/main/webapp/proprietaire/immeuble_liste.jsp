<a href="immeuble_form.jsp">Ajouter un nouvel immeuble</a>
<table>
    <tr><th>Nom</th><th>Adresse</th><th>Description</th><th>Équipements</th><th>Actions</th></tr>
    <c:forEach var="i" items="${immeubles}">
        <tr>
            <td>${i.nom}</td>
            <td>${i.adresse}</td>
            <td>${i.description}</td>
            <td>${i.equipements}</td>
            <td>
                <a href="immeubles?id=${i.id}">Modifier</a>
                <a href="immeubles?action=supprimer&id=${i.id}">Supprimer</a>
                <a href="unites?immeubleId=${i.id}">Gérer les unités</a>
            </td>
        </tr>
    </c:forEach>
</table>
