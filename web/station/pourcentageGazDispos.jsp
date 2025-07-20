<%@page import="model.StationModel"%>
<%
    StationModel stmodel = (StationModel) request.getAttribute("station");
    String type = (String) request.getAttribute("type");
%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pourcentage d'essence disponible</title>
    </head>
    <body>
        <h1> Formulaire de choix pour le pourcentage </h1>
        <form action="StationServlet" method="get">
            <input type="hidden" name="id" value="<%= stmodel.getIdStation()%>"/>

            <label for="type">Choix du type Essence  :</label>
            <select name="type" id="type" required>
                <option value="">-- Sélectionner --</option>
                <option value="gasoline">Gasoline</option>
                <option value="diesel">Diesel</option>
                <option value="all">Les deux</option>
            </select><br/><br>
            <div>
                <input type="submit" name="action" value="Pourcentage_Gaz">
            </div>
        </form>
            <!--traitement du type d'essence  -->

        <%
            if (stmodel == null) {
        %>
        <p>Station introuvable.</p>
        <%
        } else if (type != null && !type.trim().isEmpty()) {
            double pourcentageGasoline = 0.0;
            double pourcentageDiesel = 0.0;

            if (stmodel.getCapaciteStockGasoline() > 0) {
                pourcentageGasoline = ((double) stmodel.getQuantiteGasoline() / stmodel.getCapaciteStockGasoline()) * 100;
            }
            if (stmodel.getCapaciteStockDiesel() > 0) {
                pourcentageDiesel = ((double) stmodel.getQuantiteDiesel() / stmodel.getCapaciteStockDiesel()) * 100;
            }

            if ("gasoline".equals(type)) {
        %>
        <h3>Pourcentage de Gasoline disponible :</h3>
        <p>Gasoline <%= String.format("%.2f", pourcentageGasoline)%>%</p>
        <%
        } else if ("diesel".equals(type)) {
        %>
        <h3>Pourcentage de Diesel disponible :</h3>
        <p>Diesel : <%= String.format("%.2f", pourcentageDiesel)%>%</p>
        <%
        } else if ("all".equals(type)) {
        %>
        <h3>Pourcentage d'essence disponible :</h3>
        <p>Gasoline : <%= String.format("%.2f", pourcentageGasoline)%>%</p>
        <p>Diesel : <%= String.format("%.2f", pourcentageDiesel)%>%</p>
        <%
        } else {
        %>
        <p>Type de carburant inconnu.</p>
        <%
                }
            }
        %>

    </body>
</html>
