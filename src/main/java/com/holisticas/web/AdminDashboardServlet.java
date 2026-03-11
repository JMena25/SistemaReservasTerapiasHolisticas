/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.holisticas.model.Usuario;
import com.holisticas.model.Rol;
import com.holisticas.repository.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin")
public class AdminDashboardServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private TerapistaDAO terapistaDAO;
    private TerapiaDAO terapiaDAO;
    private ReservaDAO reservaDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO(getServletContext());
        terapistaDAO = new TerapistaDAO(getServletContext());
        terapiaDAO = new TerapiaDAO(getServletContext());
        reservaDAO = new ReservaDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 🔒 Validar sesión existente
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 🔒 Validar usuario en sesión
        Usuario user = (Usuario) session.getAttribute("user");
        if (user == null) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 🔒 Validar rol ADMIN
        if (user.getRol() != Rol.ADMIN) {
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // 📊 Cargar métricas del dashboard
        req.setAttribute("totalClientes", usuarioDAO.countClientes());
        req.setAttribute("totalAdmins", usuarioDAO.countAdmins());
        req.setAttribute("totalTerapistas", terapistaDAO.count());
        req.setAttribute("totalTerapias", terapiaDAO.count());


        // Vista del dashboard
        req.getRequestDispatcher("/views/admin/admin.jsp").forward(req, resp);
    }
}