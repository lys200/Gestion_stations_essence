<%@include file="../layout/header.jsp" %>
<% String erreur = (String) request.getAttribute("Erreur"); %>
<% if (erreur != null) { %>
    <div>
        <%= erreur %>
    </div>
<% } %>


<div>
    <h2>Ajouter des stations</h2>
    <hr>
    <form action="${pageContext.request.contextPath}/StationServlet" method="post" > 
        <div>
             <label for="numero">Numero: </label>
            <input type="number" name="numero" placeholder="entrer le numero" required="">
        </div><br>
        <div>
             <label for="rue">Rue de la commune: </label>
            <input type="text" name="rue" placeholder="entrer la rue" required="">
        </div><br>
        <div>
            
             <label for="adresse"> choix de la Commune: </label>
             <select name="adresse" id="adresse">
                 <option value="cap-haitien">Cap-Haitien</option>
                 <option value="limonade">Limonade</option>
                 <option value="terrier-rouge">Terrier-Rouge</option>
                 <option value="fort-liberte">Fort-Liberte</option>
             </select>
            
        </div><br>
        <div>
            <label for="capaciteGasoline">Capacite de stockage de gasoline</label>
            <input type="number" name="capaciteGasoline" id="capaciteGasoline" required="">
        </div><br>
        <div>
            <label for="capacitediesel">Capacite de stockage de diesel</label>
            <input type="number" name="capacitediesel" id="capacitediesel" required="">
        </div><br>
        <div>
            <label for="qtegazo">quantite de gasoline disponible</label>
            <input type="number" name="qtegazo" id="qtegazo" required="">
        </div><br>
        <div>
            <label for="qteDiesel">Quantite de diesel disponible</label>
            <input type="number" name="qteDiesel" id="qteDiesel" required="">
        </div><br>
        
        <div>
            <input type="submit" name="action" value="Ajouter_station">
        </div><br>
        
    </form>
</div>

<%@include file="../layout/footer.jsp" %>