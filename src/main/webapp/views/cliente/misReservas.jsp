<%-- 
    Document   : misReservas
    Created on : 14/12/2025, 9:01:22
    Author     : HP
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Reservar Cita</title>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
     <link rel="stylesheet" href="${pageContext.request.contextPath}/css/adminreserva.css">
</head>

<body class="bg-light">

<div class="container py-4">

    <h2 class="text-center mb-4">Reservar una Cita</h2>

    <!-- Mensajes -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">${success}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>

    <!--Formulario de reserva -->
    <div class="card shadow-sm mb-4">
        <div class="card-body">

            <form method="post" action="${pageContext.request.contextPath}/reserva">

                <div class="mb-3">
                    <label class="form-label">Terapia (ID)</label>
                    <input type="number" name="terapia" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Terapista (ID)</label>
                    <input type="number" name="terapeuta" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Fecha</label>
                    <input type="date" name="fecha" class="form-control" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Hora</label>
                    <input type="time" name="hora" class="form-control" required>
                </div>

                <button class="btn btn-primary w-100">Reservar</button>

            </form>

        </div>
    </div>

    <!--Tabla de reservas existentes (para evitar choques de horario) -->
    <h4 class="mb-3">Fechas ya reservadas</h4>

    <div class="card shadow-sm">
        <div class="card-body">

            <table class="table table-bordered table-striped">
                <thead class="table-secondary">
                    <tr>
                        <th>ID</th>
                        <th>Cliente ID</th>
                        <th>Terapia ID</th>
                        <th>Terapista ID</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                    </tr>
                </thead>

                <tbody>
                <c:forEach var="r" items="${reservas}">
                    <tr>
                        <td>${r.id}</td>
                        <td>${r.clienteId}</td>
                        <td>${r.terapiaId}</td>
                        <td>${r.terapeutaId}</td>
                        <td>${r.fecha}</td>
                        <td>${r.hora}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>

</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>