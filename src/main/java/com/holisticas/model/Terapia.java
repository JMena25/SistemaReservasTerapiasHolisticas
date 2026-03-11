/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.holisticas.model;

public class Terapia {
    private int id;
    private String nombre;
    private String descripcion;
    private int terapeutaId; // ✅ agregado para que el JSP pueda usarlo

    public Terapia() {}

    // ✅ Constructor para UPDATE
    public Terapia(int id, String nombre, String descripcion, int terapeutaId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.terapeutaId = terapeutaId;
    }

    // ✅ Constructor para INSERT
    public Terapia(String nombre, String descripcion, int terapeutaId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.terapeutaId = terapeutaId;
    }

    // ✅ Constructor original (manteniendo compatibilidad)
    public Terapia(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Terapia(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getTerapeutaId() { return terapeutaId; }
    public void setTerapeutaId(int terapeutaId) { this.terapeutaId = terapeutaId; }
}