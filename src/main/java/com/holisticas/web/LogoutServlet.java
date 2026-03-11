/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ✅ Cerrar sesión
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // ✅ Redirigir al login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}