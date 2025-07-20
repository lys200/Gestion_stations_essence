<%@ page import="model.StationModel" %>
<%
    StationModel stmod = (StationModel) request.getAttribute("station");
    String erreur = (String) request.getAttribute("Erreur");
    String msg = (String) request.getAttribute("msg");
%>

<%@ include file="../layout/header.jsp" %>

<h2>Modifier les infos de la station</h2>

<% if (erreur != null) {%>
<div>
    <%= erreur%>
</div>
<% } %>

<% if (msg != null) {%>
<div>
    <%= msg%>
</div>
<% } %>

<% if (stmod == null) { %>
<p>Erreur : données de la station manquantes.</p>
<% } else {%>
<form action="<%= request.getContextPath()%>/StationServlet" method="post">
    <input type="hidden" name="id" value="<%= stmod.getIdStation()%>">

    <div>
        <label for="capaciteGasoline">Capacité de stockage gasoline</label>
        <input type="number"  name="capaciteGasoline" id="capaciteGasoline" 
               value="<%= stmod.getCapaciteStockGasoline()%>" placeholder="Entrer la capacité de stockage gasoline" required>
    </div>

    <div>
        <label for="capaciteDiesel">Capacité de stockage diesel</label>
        <input type="number"  name="capaciteDiesel" id="capaciteDiesel" 
               value="<%= stmod.getCapaciteStockDiesel()%>" placeholder="Entrer la capacité de stockage diesel" required>
    </div>

    <div>
        <input type="submit" name="action" value="Modifier">
    </div>
</form>
<% }%>

<%@ include file="../layout/footer.jsp" %>
