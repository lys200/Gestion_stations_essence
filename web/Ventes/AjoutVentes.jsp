<%@include file="../layout/header.jsp" %>
<% String mesaj = (String) request.getAttribute("msg"); %>
<% if (mesaj != null) {%>
<div>
    <%= mesaj%>
</div>
<% }%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ajouter ventes </title>
    </head>
    <body>
        <div>
            <h2>Ajout des ventes</h2>
            <hr>
            <form action="${pageContext.request.contextPath}/VenteServlet" method="post" > 
                <div>
                    <label for="qtev">Quantite voulue: </label>
                    <input type="number" name="qtev" step="0.01" min="0.01" placeholder="entrer la quantite voulue: " required="">
                </div><br>
                <div>
                    <label for="typecarburant"> Type de carburant: </label>
                    <select name="typecarburant" id="typecarburant">
                        <option value="diesel">Diesel</option>
                        <option value="gasoline">Gasoline</option>
                    </select>
                </div><br>
                <div>
                    <label for="prix">Prix: </label>
                    <input type="number" name="prix" id="prix" step="0.01" required="">
                </div><br>
                <div>
                    <label for="datevente"> Date:  </label>
                    <%
                        String dateAujourdhui = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
                    %>
                    <input type="date" name="datevente" max="<%= dateAujourdhui%>" value="<%= dateAujourdhui%>">


                </div><br> 
                <div>
                    <label for="idStation"> IdStation:  </label>
                    <input type="number" name="idStation" id="idStation" required="">
                </div><br> 
                <div>
                    <input type="submit" name="action" value="Ajouter_ventes">
                </div><br>

            </form>
        </div>

    </body>
</html>

<%@include file="../layout/footer.jsp"%>
