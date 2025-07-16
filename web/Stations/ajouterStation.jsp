<%@include file="../layout/header.jsp" %>

<div>
    <h2>Ajouter des stations</h2>
    <hr>
    <form action="${pageContext.request.contextPath}/StationServlet" method="post" > 
        <div>
             <label for="adresse">Adresse geographique: </label>
            <input type="text" name="adresse" placeholder="entrer l'adresse dans ce format(numero, rue, commune)" required="">
        </div>
        <div>
            <label for="capaciteGazoline">Capacite de stockage de gasoline</label>
            <input type="number" name="capaciteGazoline" id="capaciteGazoline" required="">
        </div>
        <div>
            <label for="capacitediesel">Capacite de stockage de diesel</label>
            <input type="number" name="capacitediesel" id="capacitediesel" required="">
        </div>
        <div>
            <label for="qtegazo">quantite de gasoline disponible</label>
            <input type="number" name="qtegazo" id="qtegazo" required="">
        </div>
        <div>
            <label for="qteDiesel">Quantite de diesel disponible</label>
            <input type="number" name="qteDiesel" id="qteDiesel" required="">
        </div>
        
        <div>
            <input type="submit" value="Ajouter station">
        </div>
        
    </form>
</div>

<%@include file="../layout/footer.jsp" %>