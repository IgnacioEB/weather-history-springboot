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
    }
}
