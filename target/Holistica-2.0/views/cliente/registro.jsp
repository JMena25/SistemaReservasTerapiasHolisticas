<%-- 
    Document   : registro
    Created on : 13/12/2025, 16:48:52
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Crear Cuenta - Holísticas</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registro.css">

    </head>

    <body>

        <div class="card-login">

            <h3 class="titulo mb-4">Crear Cuenta</h3>

            <!-- Mensaje de error -->
            <c:if test="${not empty error}">
                <div class="alert-error mb-3">${error}</div>
            </c:if>

            <!-- Mensaje de éxito -->
            <c:if test="${not empty success}">
                <div class="alert-success mb-3">${success}</div>
            </c:if>

            <form method="post" action="${pageContext.request.contextPath}/registro">

                <!-- Nombre -->
                <label class="form-label">Nombre completo</label>
                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="bi bi-person"></i></span>
                    <input type="text" name="nombre" class="form-control" required>
                </div>

                <!-- Email -->
                <label class="form-label">Email</label>
                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="bi bi-envelope"></i></span>
                    <input type="email" name="email" class="form-control" required>
                </div>

                <!-- Contraseña -->
                <label class="form-label">Contraseña</label>
                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="bi bi-lock"></i></span>
                    <input type="password" name="password" class="form-control" required>
                </div>

                <!-- Confirmar contraseña -->
                <label class="form-label">Confirmar contraseña</label>
                <div class="input-group mb-4">
                    <span class="input-group-text"><i class="bi bi-lock-fill"></i></span>
                    <input type="password" name="confirm" class="form-control" required>
                </div>

                <!-- Botón -->
                <button type="submit" class="btn-holisticas w-100">
                    <i class="bi bi-person-plus"></i>
                    Registrarse
                </button>
            </form>

            <div class="link-login">
                ¿Ya tienes una cuenta?
                <a href="${pageContext.request.contextPath}/login">Inicia sesión</a>
            </div>

        </div>

    </body>
</html>