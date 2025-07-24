<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion des stations d'essence</title>
    </head>
    <body>
        <h1>Gestion des stations d'essence</h1>
        <header>
            <nav>
                <ul>
                    <li><a href="">Accueil</a></li>
                    <li><a href="${pageContext.request.contextPath}/StationServlet">Station</a></li>
                    <li><a href="${pageContext.request.contextPath}/ApprovServlet">Approvisionnement</a></li>
                    <li><a href="">Ventes</a></li>
                    <li><a href="">Deconnexion</a></li>
                </ul>
            </nav>
        </header>

    </body>
</html>
