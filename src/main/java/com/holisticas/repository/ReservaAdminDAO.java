/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.repository;

import com.holisticas.model.Reserva;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaAdminDAO {

    private final String jdbcURL;
    private final String jdbcUser;
    private final String jdbcPass;

    public ReservaAdminDAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUser = context.getInitParameter("jdbcUserName");
        this.jdbcPass = context.getInitParameter("jdbcPassword");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    // ============================
    //   LISTAR TODAS LAS RESERVAS
    // ============================
    public List<Reserva> findAllAdmin() {
        List<Reserva> lista = new ArrayList<>();

        String sql =
                "SELECT r.*, " +
                "u.username AS clienteNombre, " +
                "u.username AS clienteEmail, " +
                "t.nombre AS terapiaNombre, " +
                "te.nombre AS terapeutaNombre " +
                "FROM reservas r " +
                "JOIN usuarios u ON r.cliente_id = u.id " +
                "JOIN terapias t ON r.terapia_id = t.id " +
                "JOIN terapistas te ON r.terapista_id = te.id " +
                "ORDER BY r.fecha, r.hora";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Reserva(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("terapista_id"),
                        rs.getInt("terapia_id"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("clienteNombre"),
                        rs.getString("clienteEmail"),
                        rs.getString("terapiaNombre"),
                        rs.getString("terapeutaNombre")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================
    //   LISTAR RESERVAS DEL DÍA
    // ============================
    public List<Reserva> findTodayAdmin() {
        List<Reserva> lista = new ArrayList<>();

        String sql =
                "SELECT r.*, " +
                "u.username AS clienteNombre, " +
                "u.username AS clienteEmail, " +
                "t.nombre AS terapiaNombre, " +
                "te.nombre AS terapeutaNombre " +
                "FROM reservas r " +
                "JOIN usuarios u ON r.cliente_id = u.id " +
                "JOIN terapias t ON r.terapia_id = t.id " +
                "JOIN terapistas te ON r.terapista_id = te.id " +
                "WHERE r.fecha = CURDATE() " +
                "ORDER BY r.hora";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Reserva(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("terapista_id"),
                        rs.getInt("terapia_id"),
                        rs.getString("fecha"),
                        rs.getString("hora"),
                        rs.getString("clienteNombre"),
                        rs.getString("clienteEmail"),
                        rs.getString("terapiaNombre"),
                        rs.getString("terapeutaNombre")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // ============================
    //   INSERTAR RESERVA
    // ============================
    public void insert(Reserva r) {
        String sql = "INSERT INTO reservas (cliente_id, terapista_id, terapia_id, fecha, hora) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getClienteId());
            ps.setInt(2, r.getTerapistaId()); // CORREGIDO
            ps.setInt(3, r.getTerapiaId());
            ps.setString(4, r.getFecha());
            ps.setString(5, r.getHora());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    //   ACTUALIZAR RESERVA
    // ============================
    public void update(Reserva r) {
        String sql = "UPDATE reservas SET cliente_id=?, terapista_id=?, terapia_id=?, fecha=?, hora=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getClienteId());
            ps.setInt(2, r.getTerapistaId()); // CORREGIDO
            ps.setInt(3, r.getTerapiaId());
            ps.setString(4, r.getFecha());
            ps.setString(5, r.getHora());
            ps.setInt(6, r.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================
    //   ELIMINAR RESERVA
    // ============================
    public void delete(int id) {
        String sql = "DELETE FROM reservas WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}