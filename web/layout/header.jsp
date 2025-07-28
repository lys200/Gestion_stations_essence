<%@page import="model.Utilisateurs"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Utilisateurs ut = (Utilisateurs) session.getAttribute("user");
    if (ut == null) {
        response.sendRedirect("connection.jsp");
        return;
    }
%>

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
                    <li><a href="index.jsp">Accueil</a></li>
                    <li><a href="${pageContext.request.contextPath}/StationServlet">Station</a></li>
                    <li><a href="${pageContext.request.contextPath}/ApprovServlet">Approvisionnement</a></li>
                    <li><a href="${pageContext.request.contextPath}/VenteServlet">Ventes</a></li>

                </ul>
                <span>
                    <%= ut != null ? "Bonjour " + ut.getNomUtilisateur():""%> | <a href="logout.jsp">D&eacute;connexion</a>
                </span>
            </nav>
        </header>

    </body>
</html>
