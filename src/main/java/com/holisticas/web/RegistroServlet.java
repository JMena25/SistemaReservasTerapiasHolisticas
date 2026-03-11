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

@WebServlet("/registro")
public class RegistroServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Ruta corregida
        req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");

        if (email == null || password == null || confirm == null ||
            email.trim().isEmpty() || password.trim().isEmpty() || confirm.trim().isEmpty()) {

            req.setAttribute("error", "Todos los campos son obligatorios.");
            req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
            return;
        }

        email = email.trim();
        password = password.trim();
        confirm = confirm.trim();

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            req.setAttribute("error", "El email no es válido.");
            req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirm)) {
            req.setAttribute("error", "Las contraseñas no coinciden.");
            req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
            return;
        }

        if (usuarioDAO.exists(email)) {
            req.setAttribute("error", "El usuario ya existe.");
            req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
            return;
        }

        Usuario nuevo = new Usuario(0, email, password, Rol.CLIENTE);
        boolean registrado = usuarioDAO.insert(nuevo);

        if (!registrado) {
            req.setAttribute("error", "No se pudo registrar. Intenta nuevamente.");
            req.getRequestDispatcher("views/cliente/registro.jsp").forward(req, resp);
            return;
        }

        req.setAttribute("success", "Usuario creado exitosamente. Ahora puedes iniciar sesión.");
        req.getRequestDispatcher("views/cliente/login.jsp").forward(req, resp);
    }
}