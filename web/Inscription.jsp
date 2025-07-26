<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page d'authentification</title>
    </head>
    <body>
        <h1>Formulaire d'inscription</h1>
        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
        <div>
            <label for="nomutilisateur">Nom Utilisateur</label>
            <input type="text" name="nomutilisateur" required="" placeholder="entrez le nom d'utilisateur"><br><br>
        </div>
        <div>
            <label for="password">Nom Utilisateur</label>
            <input type="password" name="password" required="" placeholder="entrez le mot de passe"><br><br>
        </div>
        <div>
            
            <input type="submit" name="action" value="S'inscrire">
           <p>Déjà inscrit ? <a href="connection.jsp">Connectez-vous</a></p>
            
        </div>
            </form>
            <%-- Traitement des messages d'erreur --%>
    <%
        String msg = (String) request.getAttribute("msg");
        String err = (String) request.getAttribute("Erreur");
        if (msg != null) {
    %>
        <p><%= msg %></p>
    <%
        } else if (err != null) {
    %>
        <p><%= err %></p>
    <%
        }
    %>
    </body>
</html>
