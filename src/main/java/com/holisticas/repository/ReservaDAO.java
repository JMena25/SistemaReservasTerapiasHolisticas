/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.repository;

import com.holisticas.model.Reserva;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservaDAO {

    private final String jdbcURL;
    private final String jdbcUser;
    private final String jdbcPass;
    private static final Logger LOGGER = Logger.getLogger(ReservaDAO.class.getName());

    public ReservaDAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUser = context.getInitParameter("jdbcUserName");
        this.jdbcPass = context.getInitParameter("jdbcPassword");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    // ============================================================
    // Método auxiliar para mapear ResultSet → Reserva
    // ============================================================
    private Reserva mapReserva(ResultSet rs) throws SQLException {
        return new Reserva(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getInt("terapista_id"),
                rs.getInt("terapia_id"),
                rs.getString("fecha"),
                rs.getString("hora")
        );
    }

    // ============================================================
    // LISTAR TODAS LAS RESERVAS
    // ============================================================
    public List<Reserva> findAll() {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas ORDER BY fecha, hora";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapReserva(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en findAll", e);
        }
        return lista;
    }

    // ============================================================
    // LISTAR RESERVAS PARA EL CALENDARIO (con nombres)
    // ============================================================
    public List<Reserva> findAllForCalendar() {
        List<Reserva> lista = new ArrayList<>();
        String sql =
                "SELECT r.id, r.cliente_id, r.terapista_id, r.terapia_id, r.fecha, r.hora, " +
                "t.nombre AS terapiaNombre, te.nombre AS terapeutaNombre " +
                "FROM reservas r " +
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
                        rs.getString("terapiaNombre"),
                        rs.getString("terapeutaNombre")
                ));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en findAllForCalendar", e);
        }
        return lista;
    }

    // ============================================================
    // INSERTAR (con validación de choque)
    // ============================================================
    public boolean insert(Reserva r) {
        if (existeChoque(r.getTerapistaId(), r.getFecha(), r.getHora())) {
            LOGGER.warning("Intento de reserva duplicada: " + r.getFecha() + " " + r.getHora());
            return false;
        }

        String sql = "INSERT INTO reservas (cliente_id, terapista_id, terapia_id, fecha, hora) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, r.getClienteId());
            ps.setInt(2, r.getTerapistaId());
            ps.setInt(3, r.getTerapiaId());
            ps.setString(4, r.getFecha());
            ps.setString(5, r.getHora());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en insert", e);
            return false;
        }
    }

    // ============================================================
    // HORAS DISPONIBLES (para AJAX en JSP)
    // ============================================================
    public List<String> horasDisponibles(int terapeutaId, String fecha) {
        List<String> horas = Arrays.asList(
                "08:00", "09:00", "10:00", "11:00", "12:00",
                "14:00", "15:00", "16:00", "17:00"
        );

        List<Reserva> ocupadas = findByTerapeutaAndFecha(terapeutaId, fecha);
        List<String> libres = new ArrayList<>();

        for (String h : horas) {
            boolean estaOcupada = ocupadas.stream().anyMatch(r -> r.getHora().equals(h));
            if (!estaOcupada) {
                libres.add(h);
            }
        }

        return libres;
    }

    // ============================================================
    // VALIDAR CHOQUE DE HORARIO
    // ============================================================
    public boolean existeChoque(int terapeutaId, String fecha, String hora) {
        String sql = "SELECT COUNT(*) FROM reservas WHERE terapista_id=? AND fecha=? AND hora=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, terapeutaId);
            ps.setString(2, fecha);
            ps.setString(3, hora);

            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en existeChoque", e);
        }
        return true; // por seguridad
    }
    
    // LISTAR RESERVAS POR CLIENTE (con nombres)
public List<Reserva> findByClienteFull(int clienteId) {
    List<Reserva> lista = new ArrayList<>();

    String sql =
        "SELECT r.id, r.cliente_id, r.terapista_id, r.terapia_id, r.fecha, r.hora, " +
        "t.nombre AS terapiaNombre, " +
        "te.nombre AS terapeutaNombre " +
        "FROM reservas r " +
        "JOIN terapias t ON r.terapia_id = t.id " +
        "JOIN terapistas te ON r.terapista_id = te.id " +
        "WHERE r.cliente_id = ? " +
        "ORDER BY r.fecha, r.hora";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, clienteId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            lista.add(new Reserva(
                rs.getInt("id"),
                rs.getInt("cliente_id"),
                rs.getInt("terapista_id"),
                rs.getInt("terapia_id"),
                rs.getString("fecha"),
                rs.getString("hora"),
                rs.getString("terapiaNombre"),
                rs.getString("terapeutaNombre")
            ));
        }

    } catch (SQLException e) {
        LOGGER.log(Level.SEVERE, "Error en findByClienteFull", e);
    }

    return lista;
}

    // RESERVAS POR TERAPEUTA Y FECHA
    public List<Reserva> findByTerapeutaAndFecha(int terapeutaId, String fecha) {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM reservas WHERE terapista_id=? AND fecha=? ORDER BY hora";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, terapeutaId);
            ps.setString(2, fecha);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(mapReserva(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error en findByTerapeutaAndFecha", e);
        }
        return lista;
    }
}