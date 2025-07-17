<%@page import="model.StationModel"%>
<%
    StationModel stmod = (StationModel)request.getAttribute("stations");

%>
<%@include file="../layout/header.jsp" %>


<form action="${pageContext.request.contextPath}/StationServlet" method="post">
    <h2>Modifier les infos de la station</h2>

    <div>
        <label for="capaciteGasoline">Capacite de stockage gasoline</label>
        <input type="number" name="capaciteGasoline" id="capaciteGasoline" value="<%= stmod.getCapaciteStockGasoline()%>"  placeholder="entrer la capacite de stock gasoline">
        <input type="hidden" name="id" id="id" value="<%=stmod.getIdStation()%>">
    </div>
    <div>
        <label for="capaciteDiesel">capacite de stockage de Diesel</label>
        <input type="number" name="capaciteDiesel" id="capaciteDiesel"  value="<%= stmod.getCapaciteStockDiesel()%>" placeholder="entrer la capacite de stockage de diesel">
    </div>
    <div>
    <input type="submit" name="action" value="Modifier">
    </div>
</form>
<%@include file="../layout/footer.jsp" %>
