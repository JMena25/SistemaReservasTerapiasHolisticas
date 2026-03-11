/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.holisticas.model.Terapia;
import com.holisticas.repository.TerapiaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/terapias")
public class AdminTerapiaServlet extends HttpServlet {

    private TerapiaDAO dao;

    @Override
    public void init() {
        dao = new TerapiaDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean eliminado = dao.delete(id);

            if (!eliminado) {
                // ⚠️ No se pudo borrar porque hay reservas activas
                req.setAttribute("errorReserva", true);

                List<Terapia> lista = dao.findAll();
                req.setAttribute("listaTerapias", lista);

                req.getRequestDispatcher("/views/admin/terapias.jsp").forward(req, resp);
                return;
            }

            // ✅ Se borró correctamente
            resp.sendRedirect(req.getContextPath() + "/admin/terapias");
            return;
        }

        // ✅ Listar terapias
        List<Terapia> lista = dao.findAll();
        req.setAttribute("listaTerapias", lista);

        req.getRequestDispatcher("/views/admin/terapias.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/admin/terapias");
            return;
        }

        switch (action) {
            case "create":
                crear(req);
                break;
            case "update":
                actualizar(req);
                break;
        }

        resp.sendRedirect(req.getContextPath() + "/admin/terapias");
    }

    private void crear(HttpServletRequest req) {
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        dao.insert(new Terapia(nombre, descripcion));
    }

    private void actualizar(HttpServletRequest req) {
        int id = Integer.parseInt(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        dao.update(new Terapia(id, nombre, descripcion));
    }
}