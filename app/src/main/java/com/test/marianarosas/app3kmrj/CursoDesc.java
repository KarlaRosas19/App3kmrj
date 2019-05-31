package com.test.marianarosas.app3kmrj;

public class CursoDesc {


    private long id;
    private String nombre;
    private String descripcion;


    public CursoDesc(long id, String nombre, String descripcion ) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;

    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
