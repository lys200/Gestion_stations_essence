<%-- 
    Document   : connection
    Created on : Jul 19, 2025, 11:42:19 PM
    Author     : BELCEUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Page d'authentification</title>
    </head>
    <body>
        <h1>Formulaire de connexion</h1>
        <<form action="action" method="post">
        <div>
            <label for="nomutilisateur">Nom Utilisateur</label>
            <input type="text" name="nomutilisateur" required="" placeholder="entre le nom d'utilisateur"><br><br>
        </div>
        <div>
            <label for="password">Nom Utilisateur</label>
            <input type="password" name="password" required="" placeholder="entre le mot de passe"><br><br>
        </div>
        <div>
            
            <input type="submit" name="action" value="Authentifier">
            
        </div>
            </form>
    </body>
</html>
