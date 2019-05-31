package com.test.marianarosas.app3kmrj;


public class Cursos {
    private long id;
    private String nombre;
    private String sede;
    private String fechainic;
    private String fechafin;

    public Cursos(long id, String nombre, String sede,String fechainic, String fechafin  ) {
        this.id = id;
        this.nombre = nombre;
        this.sede = sede;
        this.fechainic = fechainic;
        this.fechafin = fechafin;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setFechainic(String fechainic) {
        this.fechainic = fechainic;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }


    public String getSede() {
        return sede;
    }

    public String getFechainic() {
        return fechainic;
    }

    public String getFechafin() {
        return fechafin;
    }

    public String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }
}
