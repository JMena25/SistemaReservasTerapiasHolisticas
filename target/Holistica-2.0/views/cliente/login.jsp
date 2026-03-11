<%-- 
    Document   : login
    Created on : 13/12/2025, 16:23:35
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/styles.css">

</head>
<body>

<div class="card-login">
    <img src="img/logo.png" alt="Logo" class="logo-login"/>
    <h2>Iniciar sesión</h2>

    <c:if test="${not empty success}">
        <div class="alert-success">${success}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert-error">${error}</div>
    </c:if>

<form method="post" action="login">

    <div class="mb-3">
        <label for="username" class="form-label">Usuario</label>
        <input type="text" id="username" name="username" class="form-control" placeholder="Ingresa tu correo" autocomplete="off">
    </div>

    <div class="mb-3">
        <label for="password" class="form-label">Contraseña</label>
        <input type="password" id="password" name="password" class="form-control" placeholder="Ingresa tu contraseña" autocomplete="off">
    </div>

    <button type="submit" class="btn btn-login w-100">Entrar</button>
</form>

    <a href="registro" class="link-custom">Crear cuenta nueva</a>

</div>

<script>
    // Animación de entrada suave para inputs
    document.querySelectorAll('input').forEach(input => {
        input.addEventListener('focus', () => {
            input.style.borderColor = '#A3C9A8';
        });
        input.addEventListener('blur', () => {
            input.style.borderColor = '#E8AEB7';
        });
    });
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>