/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.repository;

import com.holisticas.model.Rol;
import com.holisticas.model.Usuario;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private final String jdbcURL;
    private final String jdbcUser;
    private final String jdbcPass;

    public UsuarioDAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUser = context.getInitParameter("jdbcUserName");
        this.jdbcPass = context.getInitParameter("jdbcPassword");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    // ✅ LOGIN: Buscar usuario por username y password
    public Usuario findByUsernameAndPassword(String username, String password) {
        String sql = "SELECT * FROM usuarios WHERE username=? AND password=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Rol.valueOf(rs.getString("rol"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Verificar si un usuario ya existe
    public boolean exists(String username) {
        String sql = "SELECT id FROM usuarios WHERE username=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true; // evitar duplicados si hay error
        }
    }

    // ✅ Registrar usuario CLIENTE
    public boolean insert(Usuario u) {
        String sql = "INSERT INTO usuarios (username, password, rol) VALUES (?, ?, 'CLIENTE')";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Registrar usuario ADMIN
    public boolean insertAdmin(Usuario u) {
        String sql = "INSERT INTO usuarios (username, password, rol) VALUES (?, ?, 'ADMIN')";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Listar todos los administradores
    public List<Usuario> findAllAdmins() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE rol='ADMIN'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Rol.ADMIN
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ Eliminar administrador
    public boolean deleteAdmin(int id) {
        String sql = "DELETE FROM usuarios WHERE id=? AND rol='ADMIN'";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ Buscar usuario por ID
    public Usuario findById(int id) {
        String sql = "SELECT * FROM usuarios WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Rol.valueOf(rs.getString("rol"))
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Listar todos los usuarios
    public List<Usuario> findAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Usuario(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Rol.valueOf(rs.getString("rol"))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ✅ Contar clientes
    public int countClientes() {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE rol='CLIENTE'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ✅ Contar administradores
    public int countAdmins() {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE rol='ADMIN'";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    public boolean updateAdmin(Usuario u) {
    String sql = "UPDATE usuarios SET username=?, password=? WHERE id=? AND rol='ADMIN'";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, u.getUsername());
        ps.setString(2, u.getPassword());
        ps.setInt(3, u.getId());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
}