package com.ignacio.weatherhistory.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name= "clima")
public class Clima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ciudad;
    private Double latitud;
    private Double longitud;
    private Double temperatura;
    private Double velocidadViento;
    private LocalDateTime fechaConsulta;

    public Clima(String ciudad, Double latitud, Double longitud, Double temperatura, Double velocidadViento){
        this.ciudad= ciudad;
        this.latitud= latitud;
        this.longitud=longitud;
        this.temperatura=temperatura;
        this.velocidadViento=velocidadViento;
    }

    public Long getId() {
        return id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public Double getLatitud() {
        return latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public Double getTemperatura() {
        return temperatura;
    }

    public Double getVelocidadViento() {
        return velocidadViento;
    }

    public LocalDateTime getFechaConsulta() {
        return fechaConsulta;
    }
}
