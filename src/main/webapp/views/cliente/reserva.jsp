<%-- 
    Document   : reserva
    Created on : 13/12/2025, 17:55:47
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Reservar Cita</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link href="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/reserva.css">
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

    <h2 class="text-center mb-4 titulo">Reservar una Cita</h2>

    <!-- Mensajes -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!-- CALENDARIO -->
    <div id="calendario" class="mb-4"></div>

    <!-- FORMULARIO -->
    <div class="card card-holisticas mb-4">
        <div class="card-body">

            <form method="post" action="${pageContext.request.contextPath}/reserva">

                <div class="mb-3">
                    <label class="form-label">Terapista</label>
                    <select name="terapeuta" id="terapeutaSelectForm" class="form-control" required>
                        <option value="">Seleccione un terapista</option>
                        <c:forEach var="p" items="${listaTerapistas}">
                            <option value="${p.id}">${p.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Terapia</label>
                    <select name="terapia" id="terapiaSelectForm" class="form-control" required>
                        <option value="">Seleccione una terapia</option>
                        <c:forEach var="t" items="${listaTerapias}">
                            <option value="${t.id}" data-terapeuta="${t.terapeutaId}">${t.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Fecha</label>
                    <input type="date" id="fechaInput" name="fecha" class="form-control"
                           min="${hoy}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Hora</label>
                    <input type="time" id="horaInput" name="hora" class="form-control" required>
                </div>

                <button class="btn btn-holisticas w-100">Reservar</button>
            </form>

        </div>
    </div>

    <!-- Mis reservas -->
    <h4 class="mb-3 titulo">Mis Reservas</h4>
    <div class="card card-holisticas mb-4">
        <div class="card-body">
            <c:if test="${empty misReservas}">
                <p class="text-center text-muted">Aún no tienes reservas registradas.</p>
            </c:if>
            <c:if test="${not empty misReservas}">
                <table class="table table-hover table-holisticas align-middle">
                    <thead>
                        <tr>
                            <th>Terapia</th>
                            <th>Terapista</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="r" items="${misReservas}">
                        <tr>
                            <td>${r.terapiaNombre}</td>
                            <td>${r.terapeutaNombre}</td>
                            <td>${r.fecha}</td>
                            <td>${r.hora}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

<!-- MODAL -->
<div class="modal fade" id="modalReserva" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header bg-success text-white">
                <h5 class="modal-title">Reservar Cita</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>

            <form method="post" action="${pageContext.request.contextPath}/reserva">
                <div class="modal-body">

                    <input type="hidden" name="fecha" id="fechaModal">
                    <input type="hidden" name="terapeuta" id="terapeutaHidden">

                    <label class="mt-2">Hora disponible</label>
                    <select name="hora" id="horaSelect" class="form-control" required></select>

                    <label class="mt-3">Terapia</label>
                    <select name="terapia" id="terapiaSelectModal" class="form-control" required>
                        <c:forEach var="t" items="${listaTerapias}">
                            <option value="${t.id}" data-terapeuta="${t.terapeutaId}">${t.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="modal-footer">
                    <button class="btn btn-success">Confirmar Reserva</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- FullCalendar JS -->
<script src="https://cdn.jsdelivr.net/npm/fullcalendar@5.11.3/main.min.js"></script>
<script>
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendario');

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        locale: 'es',

        events: [
            <c:forEach var="r" items="${reservas}" varStatus="loop">
            {
                title: 'Ocupado - ${r.terapiaNombre}',
                start: '${r.fecha}T${r.hora}',
                color: '#E57373',
                textColor: 'white'
            }<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
        ]
    });

    calendar.render();
});
</script>
</body>
</html>