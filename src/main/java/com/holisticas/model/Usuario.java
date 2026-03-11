/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.model;

import java.io.Serializable;

public class Usuario implements Serializable {

    private int id;
    private String username;
    private String password;
    private Rol rol;

    public Usuario(int id, String username, String password, Rol rol) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Rol getRol() { return rol; }

    public boolean isAdmin() { return rol == Rol.ADMIN; }
    public boolean isCliente() { return rol == Rol.CLIENTE; }
}
