<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page d'authentification</title>
    </head>
    <body>
        <h1>Formulaire de connexion</h1>
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

                <input type="submit" name="action" value="Connection">
                <p>Pas de compte? <a href="Inscription.jsp">Créer un compte</a></p>


            </div>
        </form>
        <%
            String erreur = (String) request.getAttribute("Erreur");
            if (erreur != null) {
        %>
        <p><%= erreur%></p>
        <%
            }
        %>

    </body>
</html>
