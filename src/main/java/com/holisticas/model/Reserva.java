/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.model;

public class Reserva {
    private int id;
    private int clienteId;
    private int terapistaId;
    private int terapiaId;
    private String fecha;
    private String hora;

    // Campos adicionales para mostrar nombres en JSP
    private String clienteNombre;
    private String clienteEmail;
    private String terapiaNombre;
    private String terapeutaNombre;

    // ============================
    // Constructores
    // ============================

    // Constructor básico (para findAll, findByTerapeutaAndFecha)
    public Reserva(int id, int clienteId, int terapistaId, int terapiaId, String fecha, String hora) {
        this.id = id;
        this.clienteId = clienteId;
        this.terapistaId = terapistaId;
        this.terapiaId = terapiaId;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor para crear nueva reserva (POST en servlet)
    public Reserva(int clienteId, int terapistaId, int terapiaId, String fecha, String hora) {
        this.clienteId = clienteId;
        this.terapistaId = terapistaId;
        this.terapiaId = terapiaId;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor con nombres (para findAllForCalendar, findByClienteFull)
    public Reserva(int id, int clienteId, int terapistaId, int terapiaId,
                   String fecha, String hora,
                   String terapiaNombre, String terapeutaNombre) {
        this.id = id;
        this.clienteId = clienteId;
        this.terapistaId = terapistaId;
        this.terapiaId = terapiaId;
        this.fecha = fecha;
        this.hora = hora;
        this.terapiaNombre = terapiaNombre;
        this.terapeutaNombre = terapeutaNombre;
    }

    // Constructor completo para admin (incluye clienteNombre y clienteEmail)
    public Reserva(int id, int clienteId, int terapistaId, int terapiaId,
                   String fecha, String hora,
                   String clienteNombre, String clienteEmail,
                   String terapiaNombre, String terapeutaNombre) {
        this.id = id;
        this.clienteId = clienteId;
        this.terapistaId = terapistaId;
        this.terapiaId = terapiaId;
        this.fecha = fecha;
        this.hora = hora;
        this.clienteNombre = clienteNombre;
        this.clienteEmail = clienteEmail;
        this.terapiaNombre = terapiaNombre;
        this.terapeutaNombre = terapeutaNombre;
    }

    // ============================
    // Getters y Setters
    // ============================

    public int getId() { return id; }
    public int getClienteId() { return clienteId; }
    public int getTerapistaId() { return terapistaId; }
    public int getTerapiaId() { return terapiaId; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }

    public String getClienteNombre() { return clienteNombre; }
    public String getClienteEmail() { return clienteEmail; }
    public String getTerapiaNombre() { return terapiaNombre; }
    public String getTerapeutaNombre() { return terapeutaNombre; }

    public void setId(int id) { this.id = id; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public void setTerapistaId(int terapistaId) { this.terapistaId = terapistaId; }
    public void setTerapiaId(int terapiaId) { this.terapiaId = terapiaId; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public void setHora(String hora) { this.hora = hora; }

    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }
    public void setTerapiaNombre(String terapiaNombre) { this.terapiaNombre = terapiaNombre; }
    public void setTerapeutaNombre(String terapeutaNombre) { this.terapeutaNombre = terapeutaNombre; }
}