<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un approvisionnement</title>
</head>
<body>

<h2>Ajouter un approvisionnement</h2>

<%-- Afficher message d'erreur --%>
<%
    String erreur = (String) request.getAttribute("Erreur");
    if (erreur != null && !erreur.isEmpty()) {
%>
    <div style="color:red"><%= erreur %></div>
<%
    }
%>


<%
    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null && !successMessage.isEmpty()) {
%>
    <div style="color:green"><%= successMessage %></div>
<%
    }
%>

<form action="ApprovServlet" method="post">
    <input type="hidden" name="action" value="ajouterApprov" />

    <label for="idStation">Station :</label>
    <select name="idStation" id="idStation" required>
        <option value="">-- Sélectionner une station --</option>
        <%
            java.util.List stations = (java.util.List) request.getAttribute("stations");
            if (stations != null) {
                for (Object obj : stations) {
                    model.StationModel station = (model.StationModel) obj;
        %>
            <option value="<%= station.getIdStation() %>"><%= station.getIdStation() %> - <%= station.getAdresseGeog() %></option>
        <%
                }
            }
        %>
    </select>
    <br><br>

    <label for="typeCarburant">Type de carburant :</label>
    <select name="typeCarburant" id="typeCarburant" required>
        <option value="">-- Sélectionner le type --</option>
        <option value="gazoline">Gazoline</option>
        <option value="diesel">Diesel</option>
    </select>
    <br><br>

    <label for="quantite">Quantité :</label>
    <input type="number" name="quantite" id="quantite" min="1" required />
    <br><br>

    <label for="fournisseur">Fournisseur :</label>
    <select name="fournisseur" id="fournisseur" required>
        <option value="">-- Choisir un fournisseur --</option>
        <option value="TOTAL">TOTAL</option>
        <option value="PETROHAITI">PETROHAITI</option>
        <option value="ESSO">ESSO</option>
    </select>
    <br><br>

    <label for="dateApprov">Date d'approvisionnement :</label>
    <input type="date" name="dateApprov" id="dateApprov" required />
    <br><br>

    <button type="submit">Enregistrer</button>
</form>

</body>
</html>
