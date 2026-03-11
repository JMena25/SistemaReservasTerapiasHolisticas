/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import com.holisticas.model.Rol;
import com.holisticas.model.Usuario;
import com.holisticas.repository.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/admin/usuarios")
public class AdminUsuarioServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO(getServletContext());
    }

   @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    String action = req.getParameter("action");

    if ("delete".equals(action)) {
        String idParam = req.getParameter("id");
        if (idParam != null && idParam.matches("\\d+")) {
            int id = Integer.parseInt(idParam);
            usuarioDAO.deleteAdmin(id);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/usuarios");
        return;
    }

    req.setAttribute("listaAdmins", usuarioDAO.findAllAdmins());
    req.getRequestDispatcher("/views/admin/usuarios.jsp").forward(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    String action = req.getParameter("action");
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    switch (action) {
        case "create":
            usuarioDAO.insertAdmin(new Usuario(0, username, password, Rol.ADMIN));
            break;
        case "update":
            String idParam = req.getParameter("id");
            if (idParam != null && idParam.matches("\\d+")) {
                int id = Integer.parseInt(idParam);
                usuarioDAO.updateAdmin(new Usuario(id, username, password, Rol.ADMIN));
            }
            break;
        default:
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no soportada");
            return;
    }

    resp.sendRedirect(req.getContextPath() + "/admin/usuarios");
}
}