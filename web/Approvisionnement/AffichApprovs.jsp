<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des Approvisionnements</title>
</head>
<body>
<h2>Liste des Approvisionnements</h2>

<!-- Formulaire de filtre -->
<form action="${pageContext.request.contextPath}/ApprovServlet" method="get">
    <input type="hidden" name="action" value="afficherListe" />
    <label for="filterStation">Station :</label>
    <select name="filterStation" id="filterStation">
        <option value="">-- Toutes --</option>
        <% 
           List<model.StationModel> stations = (List<model.StationModel>) request.getAttribute("stations");
           String filterStation = (String) request.getAttribute("filterStation");
           if (stations != null) {
               for (model.StationModel station : stations) {
                   String selected = (station.getIdStation().equals(filterStation)) ? "selected" : "";
        %>
            <option value="<%= station.getIdStation() %>" <%= selected %>><%= station.getAdresseGeog() %></option>
        <%     }
           }
        %>
    </select>

    <label for="filterFournisseur">Fournisseur :</label>
    <input type="text" name="filterFournisseur" id="filterFournisseur" value="<%= request.getAttribute("filterFournisseur") != null ? request.getAttribute("filterFournisseur") : "" %>" />

    <label for="filterDate">Date (yyyy-MM-dd) :</label>
    <input type="date" name="filterDate" id="filterDate" value="<%= request.getAttribute("filterDate") != null ? request.getAttribute("filterDate") : "" %>" />

    <button type="submit">Filtrer</button>
</form>

<table border="1" cellpadding="5" cellspacing="0">
    <thead>
        <tr>
            <th>ID Approvisionnement</th>
            <th>Station</th>
            <th>Type Carburant</th>
            <th>Quantité</th>
            <th>Date Approvisionnement</th>
            <th>Fournisseur</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<model.ApprovisionnementModel> listeApprovs = (List<model.ApprovisionnementModel>) request.getAttribute("listeApprovs");
            if (listeApprovs != null && !listeApprovs.isEmpty()) {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (model.ApprovisionnementModel app : listeApprovs) {
        %>
            <tr>
                <td><%= app.getIdApp() %></td>
                <td><%= app.getIdStation() %></td>
                <td><%= app.getTypeCarburant() %></td>
                <td><%= app.getQuantite() %></td>
                <td><%= sdf.format(app.getDateApprov()) %></td>
                <td><%= app.getFournisseur() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr><td colspan="6">Aucun approvisionnement trouvé.</td></tr>
        <% } %>
    </tbody>
</table>

<p><a href="index.jsp">Retour au menu</a></p>
</body>
</html>
