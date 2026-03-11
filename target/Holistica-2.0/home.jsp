<%-- 
    Document   : home
    Created on : 13/12/2025, 16:23:44
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.holisticas.model.Usuario" %>
<%
    Usuario user = (Usuario) session.getAttribute("user");
%>
<html>
<body>
<h2>Bienvenido, <%= user.getUsername() %></h2>
<p>Rol: <%= user.getRol() %></p>
</body>
</html>