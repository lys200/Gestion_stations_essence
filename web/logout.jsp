<%@page import="model.Utilisateurs"%>
<%
    Utilisateurs ut = (Utilisateurs) session.getAttribute("user");
    if (ut != null) {
        session.invalidate();
        response.sendRedirect("connection.jsp");
    }
%>

