package com.ignacio.weatherhistory.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponse {
    private List<Resultado> results;

    public List<Resultado> getResults() { return results; }
    public void setResults(List<Resultado> results) { this.results = results; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resultado {
        private String name;
        private Double latitude;
        private Double longitude;
        private String country;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }
        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
    }
}