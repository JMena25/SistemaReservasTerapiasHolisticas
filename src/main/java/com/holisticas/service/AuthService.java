/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.service;

import com.holisticas.model.Usuario;
import com.holisticas.repository.UsuarioDAO;

import javax.servlet.ServletContext;

public class AuthService {

    private final UsuarioDAO usuarioDAO;

    public AuthService(ServletContext context) {
        this.usuarioDAO = new UsuarioDAO(context);
    }

    public Usuario authenticate(String username, String password) {
        if (username == null || password == null) return null;

        username = username.trim();
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()) return null;

        return usuarioDAO.findByUsernameAndPassword(username, password);
    }
}