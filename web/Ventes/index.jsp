<%@page import="model.VentesModel"%>
<%@page import="java.util.List"%>
<%@page import="model.StationModel"%>
<%@include file="../layout/header.jsp" %>

<div>
    <h2>Liste des Ventes</h2> 
    <a href="Ventes/AjoutVentes.jsp">Nouvelle Vente</a>
    <hr>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>IDVENTE</th>
                <th>QUANTITÉ</th>
                <th>TYPE DE CARBURANT</th>
                <th>DATE DE VENTE</th>
                <th>PRIX DE VENTE</th>
                <th>IDSTATION</th>
<!--                <th>ACTION</th>-->
            </tr>
        </thead>
        <tbody>
            <%
                List<VentesModel> lvm = (List<VentesModel>) request.getAttribute("ventes");
                if (lvm != null && !lvm.isEmpty()) {
                    for (VentesModel vtm : lvm) {
            %>
            <tr>
                <td><%= vtm.getIdVente() %></td>
                <td><%= vtm.getQuantiteVendu() %></td>
                <td><%= vtm.getTypeCarburantVente() %></td>
                <td><%= vtm.getDateVente() %></td>
                <td><%= vtm.getPrixVente() %></td>
                <td><%= vtm.getIdStation() %></td>
             
            </tr>
             <%
            }
        } else {
    %>
    <tr>
        <td colspan="7">Aucune vente enregistrée</td>
    </tr>
    <%
        }
    %>
        </tbody>
    </table>
</div>

<%@include file="../layout/footer.jsp" %>
