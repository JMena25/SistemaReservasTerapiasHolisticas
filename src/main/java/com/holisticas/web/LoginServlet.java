package com.holisticas.web;

import com.holisticas.model.Rol;
import com.holisticas.model.Usuario;
import com.holisticas.repository.UsuarioDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("views/cliente/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        // ✅ 1. Verificar si la sesión ya está bloqueada
        Boolean bloqueado = (Boolean) session.getAttribute("bloqueado");
        if (bloqueado != null && bloqueado) {
            req.setAttribute("error", "Has excedido el número de intentos. La sesión está bloqueada.");
            req.getRequestDispatcher("views/cliente/login.jsp").forward(req, resp);
            return;
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Usuario user = usuarioDAO.findByUsernameAndPassword(username, password);

        // ✅ 2. Si el usuario NO existe → aumentar intentos
        if (user == null) {

            Integer intentos = (Integer) session.getAttribute("intentosFallidos");
            if (intentos == null) intentos = 0;

            intentos++;
            session.setAttribute("intentosFallidos", intentos);

            // ✅ 3. Bloquear después de 2 intentos
            if (intentos >= 2) {
                session.setAttribute("bloqueado", true);
                req.setAttribute("error", "Has fallado 2 veces. La sesión está bloqueada.");
            } else {
                req.setAttribute("error", "Credenciales incorrectas. Intento " + intentos + " de 2.");
            }

            req.getRequestDispatcher("views/cliente/login.jsp").forward(req, resp);
            return;
        }

        // ✅ 4. Si el login es correcto → limpiar intentos y bloqueo
        session.removeAttribute("intentosFallidos");
        session.removeAttribute("bloqueado");

        session.setAttribute("user", user);

        // ✅ 5. Redirección según rol
        if (user.getRol() == Rol.CLIENTE) {
            resp.sendRedirect(req.getContextPath() + "/reserva");
        } else if (user.getRol() == Rol.ADMIN) {
            resp.sendRedirect(req.getContextPath() + "/admin");
        }
    }
}