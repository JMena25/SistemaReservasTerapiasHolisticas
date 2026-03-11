/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.holisticas.model.Terapista;
import com.holisticas.repository.TerapistaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/terapistas")
public class AdminTerapistaServlet extends HttpServlet {

    private TerapistaDAO dao;

    @Override
    public void init() {
        dao = new TerapistaDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        // ELIMINAR
        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean eliminado = dao.delete(id);

            if (eliminado) {
                req.getSession().setAttribute("success", "Terapista eliminado correctamente.");
            } else {
                req.getSession().setAttribute("error", "No se puede eliminar el terapista porque tiene reservas activas.");
            }

            resp.sendRedirect(req.getContextPath() + "/admin/terapistas");
            return;
        }

        // LISTAR
        List<Terapista> lista = dao.findAll();
        req.setAttribute("listaTerapistas", lista);

        // Mensajes desde sesión
        req.setAttribute("success", req.getSession().getAttribute("success"));
        req.setAttribute("error", req.getSession().getAttribute("error"));
        req.getSession().removeAttribute("success");
        req.getSession().removeAttribute("error");

        req.getRequestDispatcher("/views/admin/terapistas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/terapistas");
            return;
        }

        try {
            switch (action) {
                case "create":
                    crear(req);
                    req.getSession().setAttribute("success", "Terapista creado correctamente.");
                    break;
                case "update":
                    actualizar(req);
                    req.getSession().setAttribute("success", "Terapista actualizado correctamente.");
                    break;
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Datos inválidos o incompletos.");
        }

        resp.sendRedirect(req.getContextPath() + "/admin/terapistas");
    }

    private void crear(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String especialidad = req.getParameter("especialidad");

        if (nombre == null || especialidad == null ||
            nombre.isEmpty() || especialidad.isEmpty()) {
            throw new RuntimeException("Campos vacíos");
        }

        dao.insert(new Terapista(nombre, especialidad));
    }

    private void actualizar(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String especialidad = req.getParameter("especialidad");

        if (nombre == null || especialidad == null ||
            nombre.isEmpty() || especialidad.isEmpty()) {
            throw new RuntimeException("Campos vacíos");
        }

        dao.update(new Terapista(id, nombre, especialidad));
    }
}