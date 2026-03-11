<%-- 
    Document   : admin
    Created on : 13/12/2025, 16:31:38
    Author     : HP
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Dashboard Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admindashboard.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>

<body class="bg-light fade-in">

<div class="container py-4">

<!--ÍNDICE SUPERIOR CON LOGO Y BOTONES DIRECTOS -->
<div class="top-menu mb-5">
    <div class="row g-3 align-items-center">

        <!-- LOGO A LA IZQUIERDA -->
        <div class="col-md-2 d-flex justify-content-center justify-content-md-start">
            <img src="${pageContext.request.contextPath}/img/logo.png"
                 alt="Logo"
                 style="height:70px; width:auto; object-fit:contain;">
        </div>

        <!-- BOTONES DIRECTOS A JSP -->
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
                    <a href="${pageContext.request.contextPath}/views/admin/reservas.jsp"
                       class="btn btn-outline-warning w-100">
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
                        
    <h1 class="text-center mb-4">Dashboard Administrativo</h1>
    <p class="text-center text-muted mb-5">Resumen general del sistema</p>

    <div class="row g-4 mb-5">

    <!-- CLIENTES -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#A3C9A8; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-people-fill text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Clientes Registrados</h6>
                    <h2 class="text-white fw-bold">${totalClientes}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- ADMINISTRADORES -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#E8AEB7; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-shield-lock-fill text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Administradores</h6>
                    <h2 class="text-white fw-bold">${totalAdmins}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- TERAPISTAS -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#89CFF0; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-person-hearts text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Terapistas</h6>
                    <h2 class="text-white fw-bold">${totalTerapistas}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- TERAPIAS -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#F7D488; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-flower3 text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Terapias</h6>
                    <h2 class="text-white fw-bold">${totalTerapias}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- TOTAL RESERVAS -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#FFB6C1; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-calendar-check-fill text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Reservas Totales</h6>
                    <h2 class="text-white fw-bold">${totalReservas}</h2>
                </div>
            </div>
        </div>
    </div>

    <!-- RESERVAS HOY -->
    <div class="col-md-4 fade-in">
        <div class="card card-animated p-3 border-0" style="background:#C3B1E1; border-radius:14px;">
            <div class="d-flex align-items-center">
                <i class="bi bi-stars text-white me-3" style="font-size:3rem;"></i>
                <div>
                    <h6 class="text-white mb-1">Reservas Hoy</h6>
                    <h2 class="text-white fw-bold">${reservasHoy}</h2>
                </div>
            </div>
        </div>
    </div>

</div>

</div>

</body>
</html>