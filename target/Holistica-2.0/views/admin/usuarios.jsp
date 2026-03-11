<%-- 
    Document   : usuarios
    Created on : 14/12/2025, 9:18:33
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Administradores - Holísticas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="../../css/adminusuario.css" rel="stylesheet" type="text/css"/>
</head>

<body>
<div class="container py-4">

    <!-- Botón cerrar sesión -->
    <div class="d-flex justify-content-end mb-4">
        <a href="${pageContext.request.contextPath}/logout"
           class="btn btn-sm"
           style="background: var(--rosado); color: #fff; font-weight: bold; border-radius: 8px;">
            <i class="bi bi-box-arrow-right me-1"></i> Cerrar sesión
        </a>
    </div>

    <!-- Menú superior -->
     <div class="top-menu mb-5">
        <div class="row g-3 align-items-center">

            <div class="col-md-2 d-flex justify-content-center justify-content-md-start">
                <img src="${pageContext.request.contextPath}/img/logo.png"
                     alt="Logo"
                     style="height:70px;">
            </div>

            <div class="col-md-10">
                <div class="row g-3">

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/views/admin/terapistas.jsp"
                           class="btn btn-outline-success w-100">
                            <i class="bi bi-person-hearts me-2"></i> Terapistas
                        </a>
                    </div>

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/views/admin/terapias.jsp"
                           class="btn btn-outline-primary w-100">
                            <i class="bi bi-flower3 me-2"></i> Terapias
                        </a>
                    </div>

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/admin/reservas"
                           class="btn btn-warning w-100">
                            <i class="bi bi-calendar-check-fill me-2"></i> Reservas
                        </a>
                    </div>

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/views/admin/usuarios.jsp"
                           class="btn btn-outline-danger w-100">
                            <i class="bi bi-shield-lock-fill me-2"></i> Usuarios Admin
                        </a>
                    </div>

                </div>
            </div>

        </div>
    </div>

    <h2 class="mb-4 text-center titulo">Administradores del Sistema</h2>

    <!-- Botón crear admin -->
    <div class="mb-4 text-end">
        <button class="btn btn-holisticas" data-bs-toggle="modal" data-bs-target="#modalCrear">
            <i class="bi bi-plus-circle me-1"></i> Nuevo Administrador
        </button>
    </div>

    <!-- Tabla -->
    <div class="card card-holisticas">
        <div class="card-body">
            <table class="table table-hover table-holisticas align-middle">
                <thead>
                    <tr>
                        <th>Usuario</th>
                        <th>Rol</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="a" items="${listaAdmins}">
                    <tr>
                        <td>${a.username}</td>
                        <td>${a.rol}</td>
                        <td class="text-center">
                            <button class="btn btn-primary btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modalEditar${a.id}"
                                    title="Editar">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                            <a href="${pageContext.request.contextPath}/admin/usuarios?action=delete&id=${a.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Eliminar este administrador?');"
                               title="Eliminar">
                                <i class="bi bi-trash"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Crear -->
<div class="modal fade" id="modalCrear">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/usuarios">
            <input type="hidden" name="action" value="create">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Nuevo Administrador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <label>Usuario</label>
                    <input type="text" name="username" class="form-control mb-3" required>
                    <label>Contraseña</label>
                    <input type="password" name="password" class="form-control mb-3" required>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button class="btn btn-success">Crear Administrador</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Modales Editar fuera de la tabla -->
<c:forEach var="a" items="${listaAdmins}">
<div class="modal fade" id="modalEditar${a.id}">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/usuarios">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="${a.id}">
            <div class="modal-content">
                <div class="modal-header bg-warning text-white">
                    <h5 class="modal-title">Editar Administrador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <label>Usuario</label>
                    <input type="text" name="username" class="form-control mb-3" value="${a.username}" required>
                    <label>Contraseña</label>
                    <!-- No mostrar la contraseña actual -->
                    <input type="password" name="password" class="form-control mb-3" placeholder="Nueva contraseña">
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button class="btn btn-warning">Guardar Cambios</button>
                </div>
            </div>
        </form>
    </div>
</div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
