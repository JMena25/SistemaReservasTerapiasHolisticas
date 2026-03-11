/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.google.gson.Gson;
import com.holisticas.model.Reserva;
import com.holisticas.model.Usuario;
import com.holisticas.repository.ReservaDAO;
import com.holisticas.repository.TerapiaDAO;
import com.holisticas.repository.TerapistaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/reserva")
public class ReservaServlet extends HttpServlet {

    private ReservaDAO reservaDAO;
    private TerapiaDAO terapiaDAO;
    private TerapistaDAO terapistaDAO;

    @Override
    public void init() {
        reservaDAO = new ReservaDAO(getServletContext());
        terapiaDAO = new TerapiaDAO(getServletContext());
        terapistaDAO = new TerapistaDAO(getServletContext());
    }

    // ============================================================
    // GET → Mostrar vista de reservas o devolver horas disponibles (AJAX)
    // ============================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // AJAX: devolver horas disponibles en JSON
        if ("1".equals(req.getParameter("ajax"))) {
            int terapeutaId = Integer.parseInt(req.getParameter("terapeutaId"));
            String fecha = req.getParameter("fecha");

            List<String> libres = reservaDAO.horasDisponibles(terapeutaId, fecha);

            resp.setContentType("application/json");
            resp.getWriter().write(new Gson().toJson(libres));
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario user = (Usuario) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int clienteId = user.getId();

        // Reservas del cliente
        req.setAttribute("misReservas", reservaDAO.findByClienteFull(clienteId));

        // Reservas para el calendario (con nombres)
        req.setAttribute("reservas", reservaDAO.findAllForCalendar());

        // Listas para selects
        req.setAttribute("listaTerapias", terapiaDAO.findAll());
        req.setAttribute("listaTerapistas", terapistaDAO.findAll());

        req.getRequestDispatcher("views/cliente/reserva.jsp").forward(req, resp);
    }

    // ============================================================
    // POST → Crear reserva
    // ============================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario user = (Usuario) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int clienteId = user.getId();

        try {
            int terapeutaId = Integer.parseInt(req.getParameter("terapeuta"));
            int terapiaId = Integer.parseInt(req.getParameter("terapia"));
            String fecha = req.getParameter("fecha");
            String hora = req.getParameter("hora");

            if (fecha == null || fecha.isEmpty() || hora == null || hora.isEmpty()) {
                req.setAttribute("error", "Debe seleccionar fecha y hora.");
                doGet(req, resp);
                return;
            }

            // Validar que la fecha no sea pasada
            LocalDate hoy = LocalDate.now();
            LocalDate fechaReserva = LocalDate.parse(fecha);
            if (fechaReserva.isBefore(hoy)) {
                req.setAttribute("error", "No puede reservar en fechas pasadas.");
                doGet(req, resp);
                return;
            }

            // Validar choque de horario
            if (reservaDAO.existeChoque(terapeutaId, fecha, hora)) {
                req.setAttribute("error", "Ese horario ya está reservado.");
                doGet(req, resp);
                return;
            }

            // Crear reserva
            Reserva nueva = new Reserva(clienteId, terapeutaId, terapiaId, fecha, hora);
            boolean creada = reservaDAO.insert(nueva);

            if (!creada) {
                req.setAttribute("error", "No se pudo crear la reserva.");
            } else {
                req.setAttribute("success", "Reserva creada exitosamente.");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("error", "Datos inválidos en el formulario.");
        }

        doGet(req, resp);
    }
}