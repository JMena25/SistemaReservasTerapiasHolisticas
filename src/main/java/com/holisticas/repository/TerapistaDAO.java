/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.repository;

import com.holisticas.model.Terapista;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerapistaDAO {

    private final String jdbcURL;
    private final String jdbcUser;
    private final String jdbcPass;

    public TerapistaDAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUser = context.getInitParameter("jdbcUserName");
        this.jdbcPass = context.getInitParameter("jdbcPassword");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    // ============================
    // LISTAR TODOS
    // ============================
    public List<Terapista> findAll() {
        List<Terapista> lista = new ArrayList<>();
        String sql = "SELECT * FROM terapistas";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Terapista(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especialidad")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en findAll(): " + e.getMessage());
        }

        return lista;
    }

    // ============================
    // INSERTAR
    // ============================
    public boolean insert(Terapista t) {
        String sql = "INSERT INTO terapistas (nombre, especialidad) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getEspecialidad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en insert(): " + e.getMessage());
        }

        return false;
    }

    // ============================
    // BUSCAR POR ID
    // ============================
    public Terapista findById(int id) {
        String sql = "SELECT * FROM terapistas WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Terapista(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("especialidad")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en findById(): " + e.getMessage());
        }

        return null;
    }

    // ============================
    // ACTUALIZAR
    // ============================
    public boolean update(Terapista t) {
        String sql = "UPDATE terapistas SET nombre=?, especialidad=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getEspecialidad());
            ps.setInt(3, t.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en update(): " + e.getMessage());
        }

        return false;
    }

    // ============================
    // ELIMINAR
    // ============================
    public boolean delete(int id) {
    String sql = "DELETE FROM terapistas WHERE id=?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        if (e.getErrorCode() == 1451) {
            System.err.println("No se puede eliminar el terapista porque tiene reservas activas.");
            return false; // devolvemos false para que el servlet muestre la alerta
        }
        System.err.println("Error en delete(): " + e.getMessage());
        return false;
    }
}

    // ============================
    // CONTAR
    // ============================
    public int count() {
        String sql = "SELECT COUNT(*) FROM terapistas";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            System.err.println("Error en count(): " + e.getMessage());
        }

        return 0;
    }
}