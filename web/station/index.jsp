  
<%@page import="java.util.List"%>
<%@page import="model.StationModel"%>
<%@include file="../layout/header.jsp" %>

<div>
    <h2> Liste des stations</h2> <a href="station/ajouterStation.jsp"> Nouvelle Station </a>
    <hr>
    <table>
        <thead>

            <tr>
                <th>ID</th>
                <th>ADRESSE GEOGRAPHIQUE</th>
                <th>CAPACITE DE STOCK GAZOLINE</th>
                <th>CAPACITE DE STOCK DE DIESEL</th>
                <th>NOMBRES DE GALLONS DE GAZOLINE DISPOS</th>
                <th>NOMBRES DE GALLONS DE DIESEL DISPOS</th>
                <th>ACTION</th>
            </tr>
        </thead>

        <tbody>
            <%
                List<StationModel> lst = (List<StationModel>) request.getAttribute("stations");
                if (lst != null && !lst.isEmpty()) {
                    for (StationModel stm : lst) {

            %>
            <tr>
                <td><%= stm.getIdStation()%></td>
                <td><%= stm.getAdresseGeog()%></td>
                <td><%= stm.getCapaciteStockGasoline()%></td>
                <td><%= stm.getCapaciteStockDiesel()%></td>
                <td><%= stm.getQuantiteGasoline()%></td>
                <td><%= stm.getQuantiteDiesel()%></td>
                <td>
                    <a href="${pageContext.request.contextPath}/StationServlet?id=<%=stm.getIdStation() %>&action=Modifier">MODIFIER</a>
                </td>
            </tr>

            <%}
            } else{%>
            <tr>
                <td colspan="6">Aucune station enregistrée.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</div>
<%@include file="../layout/footer.jsp" %>