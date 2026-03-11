<%-- 
    Document   : reservas
    Created on : 14/12/2025, 9:02:07
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Reservas - Administrador</title>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminreserva.css">

</head>

<body>

<div class="container py-4">

    <!-- Cerrar sesión -->
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

    <h2 class="mb-4 text-center titulo">Gestión de Reservas</h2>

    <!-- Mensajes -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- BOTONES SUPERIORES -->
    <div class="d-flex justify-content-end mb-3">

        <!-- Nueva reserva -->
        <button class="btn btn-success me-2" data-bs-toggle="modal" data-bs-target="#modalCrear">
            <i class="bi bi-plus-circle"></i> Nueva Reserva
        </button>

        <!-- Reservas de hoy -->
        <a href="${pageContext.request.contextPath}/admin/reservas?view=today"
           class="btn btn-warning me-2">
            <i class="bi bi-calendar-day"></i> Reservas de Hoy
        </a>

        <!-- Todas las reservas -->
        <a href="${pageContext.request.contextPath}/admin/reservas?view=all"
           class="btn btn-primary">
            <i class="bi bi-list-ul"></i> Todas las Reservas
        </a>

    </div>

    <!-- TABLA -->
    <div class="card card-holisticas mb-4">
        <div class="card-body">

            <c:if test="${empty reservas}">
                <p class="text-center text-muted">No hay reservas registradas.</p>
            </c:if>

            <c:if test="${not empty reservas}">
                <table class="table table-hover table-holisticas align-middle">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Cliente</th>
                            <th>Email</th>
                            <th>Terapia</th>
                            <th>Terapista</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th class="text-center">Acciones</th>
                        </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="r" items="${reservas}">
                        <tr>
                            <td>${r.id}</td>
                            <td>${r.clienteNombre}</td>
                            <td>${r.clienteEmail}</td>
                            <td>${r.terapiaNombre}</td>
                            <td>${r.terapeutaNombre}</td>
                            <td>${r.fecha}</td>
                            <td>${r.hora}</td>

                            <td class="text-center">

                                <!-- Editar -->
                                <button class="btn btn-primary btn-sm"
                                        data-bs-toggle="modal"
                                        data-bs-target="#modalEditar${r.id}">
                                    <i class="bi bi-pencil-square"></i>
                                </button>

                                <!-- Eliminar -->
                                <a href="${pageContext.request.contextPath}/admin/reservas?action=delete&id=${r.id}"
                                   class="btn btn-danger btn-sm"
                                   onclick="return confirm('¿Eliminar esta reserva?');">
                                    <i class="bi bi-trash"></i>
                                </a>

                            </td>
                        </tr>

                        <!-- MODAL EDITAR -->
                        <div class="modal fade" id="modalEditar${r.id}">
                            <div class="modal-dialog">
                                <form method="post" action="${pageContext.request.contextPath}/admin/reservas">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${r.id}">

                                    <div class="modal-content">
                                        <div class="modal-header bg-warning text-white">
                                            <h5 class="modal-title">Editar Reserva</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>

                                        <div class="modal-body">

                                            <label>Cliente ID</label>
                                            <input type="number" name="clienteId" class="form-control mb-3" value="${r.clienteId}" required>

                                            <label>Terapia</label>
                                            <select name="terapiaId" class="form-control mb-3" required>
                                                <c:forEach var="t" items="${listaTerapias}">
                                                    <option value="${t.id}" ${t.id == r.terapiaId ? "selected" : ""}>${t.nombre}</option>
                                                </c:forEach>
                                            </select>

                                            <label>Terapista</label>
                                            <select name="terapistaId" class="form-control mb-3" required>
                                                <c:forEach var="p" items="${listaTerapistas}">
                                                    <option value="${p.id}" ${p.id == r.terapistaId ? "selected" : ""}>${p.nombre}</option>
                                                </c:forEach>
                                            </select>

                                            <label>Fecha</label>
                                            <input type="date" name="fecha" class="form-control mb-3" value="${r.fecha}" required>

                                            <label>Hora</label>
                                            <input type="time" name="hora" class="form-control mb-3" value="${r.hora}" required>

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
                    </tbody>
                </table>
            </c:if>

        </div>
    </div>

</div>

<!-- MODAL CREAR -->
<div class="modal fade" id="modalCrear">
    <div class="modal-dialog">
        <form method="post" action="${pageContext.request.contextPath}/admin/reservas">
            <input type="hidden" name="action" value="create">

            <div class="modal-content">

                <div class="modal-header bg-success text-white">
                    <h5 class="modal-title">Crear Nueva Reserva</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>

                <div class="modal-body">

                    <label>Cliente ID</label>
                    <input type="number" name="clienteId" class="form-control mb-3" required>

                    <label>Terapia</label>
                    <select name="terapiaId" class="form-control mb-3" required>
                        <c:forEach var="t" items="${listaTerapias}">
                            <option value="${t.id}">${t.nombre}</option>
                        </c:forEach>
                    </select>

                    <label>Terapista</label>
                    <select name="terapistaId" class="form-control mb-3" required>
                        <c:forEach var="p" items="${listaTerapistas}">
                            <option value="${p.id}">${p.nombre}</option>
                        </c:forEach>
                    </select>

                    <label>Fecha</label>
                    <input type="date" name="fecha" class="form-control mb-3" required>

                    <label>Hora</label>
                    <input type="time" name="hora" class="form-control mb-3" required>

                </div>

                <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button class="btn btn-success">Guardar Reserva</button>
                </div>

            </div>
        </form>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>