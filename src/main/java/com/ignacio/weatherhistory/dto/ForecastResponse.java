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
    public static class Current{
        @JsonProperty("temperature_2m")
        private Double temperatura;

        @JsonProperty("wind_speed_10m")
        private Double velocidadViento;

        @JsonProperty("relative_humidity_2m")
        private int humedad;


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
    }
}
