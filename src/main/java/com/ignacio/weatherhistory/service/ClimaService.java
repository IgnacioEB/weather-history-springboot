package com.ignacio.weatherhistory.service;

import com.ignacio.weatherhistory.dto.ForecastResponse;
import com.ignacio.weatherhistory.dto.GeocodingResponse;
import com.ignacio.weatherhistory.exception.CiudadNoEncontradaException;
import com.ignacio.weatherhistory.model.Clima;
import com.ignacio.weatherhistory.repository.ClimaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClimaService {

    private final RestTemplate restTemplate;
    private final ClimaRepository climaRepository;

    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search?name={ciudad}&count=1";
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,wind_speed_10m,relative_humidity_2m,wind_direction_10m,apparent_temperature,cloud_cover";

    public ClimaService(RestTemplate restTemplate, ClimaRepository climaRepository) {
        this.restTemplate = restTemplate;
        this.climaRepository = climaRepository;
    }

    public Clima consultarClima(String nombreCiudad) {
        // Paso 1: geocoding — nombre de ciudad → coordenadas
        GeocodingResponse geo = restTemplate.getForObject(GEOCODING_URL, GeocodingResponse.class, nombreCiudad);

        if (geo == null || geo.getResults() == null || geo.getResults().isEmpty()) {
            throw new CiudadNoEncontradaException("No se encontró la ciudad: " + nombreCiudad);
        }

        GeocodingResponse.Resultado resultado = geo.getResults().get(0);
        // Paso 2: forecast — coordenadas → clima actual
        ForecastResponse forecast = restTemplate.getForObject(FORECAST_URL, ForecastResponse.class, resultado.getLatitude(), resultado.getLongitude());

        if (forecast == null || forecast.getCurrent() == null) {
            throw new RuntimeException("No se pudo obtener el clima para: " + nombreCiudad);
        }

        // Paso 3: armar y guardar
        Clima clima = new Clima(
                resultado.getName(),
                resultado.getLatitude(),
                resultado.getLongitude(),
                forecast.getCurrent().getTemperatura(),
                forecast.getCurrent().getVelocidadViento(),
                forecast.getCurrent().getHumedad(),
                forecast.getCurrent().getDireccionViento(),
                forecast.getCurrent().getSensacionTermica(),
                forecast.getCurrent().getCoberturaNubes()
        );


        return climaRepository.save(clima);
    }

    public List<Clima> obtenerHistorial(String ciudad) {
        return climaRepository.findByCiudadOrderByFechaConsultaDesc(ciudad);
    }

    public Double obtenerTemperaturaPromedio(String ciudad, int dias){
        LocalDateTime desde= LocalDateTime.now().minusDays(dias);
        Double promedio= climaRepository.ObtenerTemperaturaPromedio(ciudad, desde);
        if(promedio==null){
            throw new CiudadNoEncontradaException("No hay datos de "+ciudad+" en los ultimos "+ dias+ "dias");
        }
        return promedio;
    }

}