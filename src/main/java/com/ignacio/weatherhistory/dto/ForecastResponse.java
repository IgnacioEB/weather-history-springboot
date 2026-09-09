package com.ignacio.weatherhistory.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastResponse {
    private Current current;

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Current {
        @JsonProperty("temperature_2m")
        private Double temperatura;

        @JsonProperty("wind_speed_10m")
        private Double velocidadViento;

        @JsonProperty("relative_humidity_2m")
        private int humedad;

        @JsonProperty("wind_direction_10m")
        private String direccionViento;

        @JsonProperty("apparent_temperature")
        private Double sensacionTermica;

        @JsonProperty("cloud_cover")
        private Integer coberturaNubes;


        public Double getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(Double temperatura) {
            this.temperatura = temperatura;
        }

        public Double getVelocidadViento() {
            return velocidadViento;
        }

        public void setVelocidadViento(Double velocidadViento) {
            this.velocidadViento = velocidadViento;
        }

        public int getHumedad(){
            return this.humedad;
        }
        public void setHumedad(int humedad){
            this.humedad=humedad;
        }


        public String getDireccionViento() {
            return direccionViento;
        }

        public void setDireccionViento(String direccionViento) {
            this.direccionViento = direccionViento;
        }


        public Double getSensacionTermica() {
            return sensacionTermica;
        }

        public void setSensacionTermica(Double sensacionTermica) {
            this.sensacionTermica = sensacionTermica;
        }

        public Integer getCoberturaNubes() {
            return coberturaNubes;
        }

        public void setCoberturaNubes(Integer coberturaNubes) {
            this.coberturaNubes = coberturaNubes;
        }
    }


}
