<%@ page import="java.util.List" %>
<%@ page import="model.StationModel" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Approvisionnement</title>
</head>
<body>
<h2>Ajouter un Approvisionnement</h2>

<%
    String erreur = (String) request.getAttribute("Erreur");
    if (erreur != null && !erreur.isEmpty()) {
%>
    <p style="color:red;"><%= erreur %></p>
<%
    }

    String successMessage = (String) request.getAttribute("successMessage");
    if (successMessage != null && !successMessage.isEmpty()) {
%>
    <p style="color:green;"><%= successMessage %></p>
<%
    }

    List<StationModel> stations = (List<StationModel>) request.getAttribute("stations");
%>

<form action="<%= request.getContextPath() %>/ApprovServlet" method="post">
    <input type="hidden" name="action" value="ajouterApprov"/>

    <label for="idStation">Station :</label>
    <select name="idStation" id="idStation" required>
        <option value="">-- Sélectionner une station --</option>
        <%
            if (stations != null) {
                for (StationModel station : stations) {
        %>
                    <option value="<%= station.getIdStation() %>"><%= station.getAdresseGeog() %></option>
        <%
                }
            }
        %>
    </select><br><br>

    <label for="typeCarburant">Type de carburant :</label>
    <select name="typeCarburant" id="typeCarburant" required>
        <option value="">-- Sélectionner le type --</option>
        <option value="Gazoline">Gasoline</option>
        <option value="Diesel">Diesel</option>
    </select><br><br>

    <label for="quantite">Quantité :</label>
    <input type="number" name="quantite" id="quantite" min="1" required><br><br>

    <label for="fournisseur">Fournisseur :</label>
    <input type="text" name="fournisseur" id="fournisseur" required><br><br>

    <button type="submit">Enregistrer</button>
</form>

<p><a href="<%= request.getContextPath() %>/Approvisionnement/index.jsp">Retour au menu</a></p>
</body>
</html>
