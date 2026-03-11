<%-- 
    Document   : terapistas
    Created on : 14/12/2025, 9:18:56
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Gestión de Terapistas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminterapeuta.css">
</head>

<body class="bg-light fade-in">

<div class="container py-4">

      <!-- Barra superior -->
    <div class="top-menu mb-5">
        <div class="row g-3 align-items-center">

            <!-- LOGO -->
            <div class="col-md-2 d-flex justify-content-center justify-content-md-start">
                <img src="${pageContext.request.contextPath}/img/logo.png"
                     alt="Logo"
                     style="height:70px; width:auto; object-fit:contain;">
            </div>

            <!-- BOTONES -->
            <div class="col-md-10">
                <div class="row g-3">

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/admin/terapistas"
                           class="btn btn-success w-100">
                            <i class="bi bi-person-hearts me-2"></i> Terapistas
                        </a>
                    </div>

                    <div class="col-md-3">
                        <a href="${pageContext.request.contextPath}/admin/terapias"
                           class="btn btn-primary w-100">
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
                        <a href="${pageContext.request.contextPath}/admin/usuarios"
                           class="btn btn-danger w-100">
                            <i class="bi bi-shield-lock-fill me-2"></i> Usuarios Admin
                        </a>
                    </div>

                </div>
            </div>

        </div>
    </div>

    <!-- Título -->
    <h2 class="mb-4 text-center">Gestión de Terapistas</h2>
    
    <!-- ALERTAS DE ÉXITO Y ERROR -->
<c:if test="${not empty success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i>
        ${success}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
    
     <!-- Botón Mostrar Terapistas -->
    <div class="text-end mb-3">
        <a href="${pageContext.request.contextPath}/admin/terapistas" class="btn btn-outline-success">
            <i class="bi bi-eye-fill me-2"></i> Mostrar Terapistas
        </a>
    </div>

    <!-- Botón Crear -->
    <div class="text-end mb-3">
        <button class="btn btn-success" data-bs-toggle="modal" data-bs-target="#modalCrear">
            <i class="bi bi-plus-circle me-2"></i> Nuevo Terapista
        </button>
    </div>

    <!-- Tabla -->
    <div class="card shadow-sm border-0 card-animated">
        <div class="card-body">
            <table class="table table-hover align-middle">
                <thead class="table-success">
                    <tr>
                        <th>Nombre</th>
                        <th>Especialidad</th>
                        <th class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="t" items="${listaTerapistas}">
                    <tr>
                        <td>${t.nombre}</td>
                        <td>${t.especialidad}</td>
                        <td class="text-center">
                            <!-- Editar -->
                            <button class="btn btn-primary btn-sm"
                                    data-bs-toggle="modal"
                                    data-bs-target="#modalEditar"
                                    data-id="${t.id}"
                                    data-nombre="${t.nombre}"
                                    data-especialidad="${t.especialidad}">
                                <i class="bi bi-pencil-square"></i>
                            </button>

                            <!-- Eliminar -->
                            <a href="${pageContext.request.contextPath}/admin/terapistas?action=delete&id=${t.id}"
                               class="btn btn-danger btn-sm"
                               onclick="return confirm('¿Eliminar este terapista?');">
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
<div class="modal fade" id="modalCrear" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/terapistas">
            <input type="hidden" name="action" value="create">
            <div class="modal-content">
                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Nuevo Terapista</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <label>Nombre</label>
                    <input type="text" name="nombre" class="form-control mb-3" required>
                    <label>Especialidad</label>
                    <input type="text" name="especialidad" class="form-control mb-3" required>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button class="btn btn-success">Crear</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Modal Editar (único) -->
<div class="modal fade" id="modalEditar" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/terapistas">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" id="edit-id">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">Editar Terapista</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <label>Nombre</label>
                    <input type="text" name="nombre" id="edit-nombre" class="form-control mb-3" required>
                    <label>Especialidad</label>
                    <input type="text" name="especialidad" id="edit-especialidad" class="form-control mb-3" required>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button class="btn btn-primary">Guardar Cambios</button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Script para cargar datos en el modal de edición -->
<script>
document.addEventListener('DOMContentLoaded', function () {
    var modalEditar = document.getElementById('modalEditar');
    modalEditar.addEventListener('show.bs.modal', function (event) {
        var button = event.relatedTarget;
        var id = button.getAttribute('data-id');
        var nombre = button.getAttribute('data-nombre');
        var especialidad = button.getAttribute('data-especialidad');

        document.getElementById('edit-id').value = id;
        document.getElementById('edit-nombre').value = nombre;
        document.getElementById('edit-especialidad').value = especialidad;
    });
});
</script>

</body>
</html>