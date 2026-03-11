package com.holisticas.repository;

import com.holisticas.model.Terapia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class TerapiaDAO {

    private final String jdbcURL;
    private final String jdbcUser;
    private final String jdbcPass;

    public TerapiaDAO(ServletContext context) {
        this.jdbcURL = context.getInitParameter("jdbcURL");
        this.jdbcUser = context.getInitParameter("jdbcUserName");
        this.jdbcPass = context.getInitParameter("jdbcPassword");
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    public List<Terapia> findAll() {
        List<Terapia> lista = new ArrayList<>();
        String sql = "SELECT * FROM terapias ORDER BY id DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Terapia(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error en TerapiaDAO.findAll(): " + e.getMessage());
        }

        return lista;
    }

    public boolean insert(Terapia t) {
        String sql = "INSERT INTO terapias (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en TerapiaDAO.insert(): " + e.getMessage());
            return false;
        }
    }

    public boolean update(Terapia t) {
        String sql = "UPDATE terapias SET nombre=?, descripcion=? WHERE id=?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getNombre());
            ps.setString(2, t.getDescripcion());
            ps.setInt(3, t.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error en TerapiaDAO.update(): " + e.getMessage());
            return false;
        }
    }

   public boolean delete(int id) {
    String sql = "DELETE FROM terapias WHERE id=?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, id);
        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        // Error 1451 = clave foránea, no se puede borrar porque hay reservas activas
        if (e.getErrorCode() == 1451) {
            System.err.println("No se puede eliminar la terapia porque tiene reservas activas.");
            return false; // devolvemos false para que el servlet muestre la alerta
        }
        System.err.println("Error en TerapiaDAO.delete(): " + e.getMessage());
        return false;
    }
}

    public int count() {
        String sql = "SELECT COUNT(*) FROM terapias";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            System.err.println("Error en TerapiaDAO.count(): " + e.getMessage());
        }

        return 0;
    }
}