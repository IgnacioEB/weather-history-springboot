package com.ignacio.weatherhistory.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name= "climas")
public class Clima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ciudad;
    private String provincia;
    private String pais;
    private Double latitud;
    private Double longitud;
    private Double temperatura;
    private Double velocidadViento;
    private LocalDateTime fechaConsulta;
    private Integer humedad;
    private String direccionViento;
    private Double sensacionTermica;
    private int coberturaNubes;

    public Clima(String ciudad, String provincia, String pais, Double latitud, Double longitud, Double temperatura, Double velocidadViento, Integer humedad, String direccionViento, Double sensacionTermica, Integer coberturaNubes){
        this.ciudad= ciudad;
        this.provincia=provincia;
        this.pais=pais; 
        this.latitud= latitud;
        this.longitud=longitud;
        this.temperatura=temperatura;
        this.velocidadViento=velocidadViento;
        this.fechaConsulta= LocalDateTime.now();
        this.humedad=humedad;
        this.direccionViento= direccionViento;
        this.sensacionTermica= sensacionTermica;
        this.coberturaNubes=coberturaNubes;

    }

    public Clima() {

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

    public int getHumedad() {
        return humedad;
    }


    public String getDireccionViento() {
        return direccionViento;
    }

    public Double getSensacionTermica() {
        return sensacionTermica;
    }

    public int getCoberturaNubes() {
        return coberturaNubes;
    }


    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }
}
