/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.filters;

import com.holisticas.model.Rol;
import com.holisticas.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter({"/admin", "/admin/*"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        // ✅ Si no hay sesión → enviar al login
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Usuario user = (Usuario) session.getAttribute("user");

        // ✅ Si NO es admin → enviarlo a su panel de cliente
        if (user.getRol() != Rol.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/reserva");
            return;
        }

        chain.doFilter(request, response);
    }
}