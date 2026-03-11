/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;

@WebFilter("/login")
public class LoginAttemptFilter implements Filter {

    private static final int MAX_ATTEMPTS = 2;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(true);

        Boolean blocked = (Boolean) session.getAttribute("blocked");
        Integer attempts = (Integer) session.getAttribute("attempts");

        if (attempts == null) attempts = 0;
        if (blocked == null) blocked = false;

        // ✅ Si está bloqueado → no permitir login
        if (blocked) {
            req.setAttribute("error", "Tu sesión está bloqueada por múltiples intentos fallidos.");
            req.getRequestDispatcher("views/cliente/login.jsp").forward(req, resp);
            return;
        }

        // ✅ Continuar con el LoginServlet
        chain.doFilter(request, response);

        // ✅ Después del servlet: revisar si falló
        Boolean loginFailed = (Boolean) req.getAttribute("loginFailed");

        if (loginFailed != null && loginFailed) {
            attempts++;
            session.setAttribute("attempts", attempts);

            if (attempts >= MAX_ATTEMPTS) {
                session.setAttribute("blocked", true);
            }
        }
    }
}