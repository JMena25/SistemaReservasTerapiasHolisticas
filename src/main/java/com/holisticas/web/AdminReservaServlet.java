/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.holisticas.model.Reserva;
import com.holisticas.repository.ReservaAdminDAO;
import com.holisticas.repository.TerapiaDAO;
import com.holisticas.repository.TerapistaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/reservas")
public class AdminReservaServlet extends HttpServlet {

    private ReservaAdminDAO reservaAdminDAO;
    private TerapiaDAO terapiaDAO;
    private TerapistaDAO terapistaDAO;

    @Override
    public void init() {
        reservaAdminDAO = new ReservaAdminDAO(getServletContext());
        terapiaDAO = new TerapiaDAO(getServletContext());
        terapistaDAO = new TerapistaDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        String view = req.getParameter("view");

        // DELETE
        if ("delete".equals(action)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                reservaAdminDAO.delete(id);
                req.getSession().setAttribute("success", "Reserva eliminada correctamente.");
            } catch (Exception e) {
                req.getSession().setAttribute("error", "No se pudo eliminar la reserva.");
            }
            resp.sendRedirect(req.getContextPath() + "/admin/reservas");
            return;
        }

        // Cargar reservas según vista
        List<Reserva> reservas;
        try {
            if ("today".equals(view)) {
                reservas = reservaAdminDAO.findTodayAdmin();
            } else {
                reservas = reservaAdminDAO.findAllAdmin();
            }
            req.setAttribute("reservas", reservas);
        } catch (Exception e) {
            req.setAttribute("error", "Error al cargar las reservas.");
            req.setAttribute("reservas", null);
        }

        // Listas para selects
        req.setAttribute("listaTerapias", terapiaDAO.findAll());
        req.setAttribute("listaTerapistas", terapistaDAO.findAll());

        // Mensajes desde sesión
        req.setAttribute("success", req.getSession().getAttribute("success"));
        req.setAttribute("error", req.getSession().getAttribute("error"));
        req.getSession().removeAttribute("success");
        req.getSession().removeAttribute("error");

        req.getRequestDispatcher("/views/admin/reservas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {
            int clienteId = Integer.parseInt(req.getParameter("clienteId"));
            int terapistaId = Integer.parseInt(req.getParameter("terapistaId"));
            int terapiaId = Integer.parseInt(req.getParameter("terapiaId"));
            String fecha = req.getParameter("fecha");
            String hora = req.getParameter("hora");

            if ("create".equals(action)) {
                reservaAdminDAO.insert(new Reserva(clienteId, terapistaId, terapiaId, fecha, hora));
                req.getSession().setAttribute("success", "Reserva creada correctamente.");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(req.getParameter("id"));
                reservaAdminDAO.update(new Reserva(id, clienteId, terapistaId, terapiaId, fecha, hora));
                req.getSession().setAttribute("success", "Reserva actualizada correctamente.");
            }

        } catch (Exception e) {
            req.getSession().setAttribute("error", "Datos inválidos en el formulario.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/reservas");
    }
}