package com.ignacio.weatherhistory.service;

import com.ignacio.weatherhistory.dto.ForecastResponse;
import com.ignacio.weatherhistory.dto.GeocodingResponse;
import com.ignacio.weatherhistory.exception.CiudadNoEncontradaException;
import com.ignacio.weatherhistory.model.Clima;
import com.ignacio.weatherhistory.repository.ClimaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Service
public class ClimaService {

    private final RestTemplate restTemplate;
    private final ClimaRepository climaRepository;
    //GEOCODING URLS CON DIFERENTES CON DIFERENTE CANTIDAD DE PLACEHOLDERS
    private static final String URL_SOLO_CIUDAD =
            "https://geocoding-api.open-meteo.com/v1/search?name={ciudad}&count=1";
    private static final String URL_CIUDAD_PROVINCIA =
            "https://geocoding-api.open-meteo.com/v1/search?name={ciudad},{provincia}&count=1";
    private static final String URL_CIUDAD_PAIS =
            "https://geocoding-api.open-meteo.com/v1/search?name={ciudad}&countryCode={pais}&count=1";
    private static final String URL_CIUDAD_PROVINCIA_PAIS =
            "https://geocoding-api.open-meteo.com/v1/search?name={ciudad},{provincia}&countryCode={pais}&count=1";
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,wind_speed_10m,relative_humidity_2m,wind_direction_10m,apparent_temperature,cloud_cover";

    public ClimaService(RestTemplate restTemplate, ClimaRepository climaRepository) {
        this.restTemplate = restTemplate;
        this.climaRepository = climaRepository;
    }

    private GeocodingResponse.Resultado consultarCoordenadas(String url, Object... params){
        GeocodingResponse geo= restTemplate.getForObject(url, GeocodingResponse.class, params);
        if(geo==null || geo.getResults()==null||geo.getResults().isEmpty()){
            throw new CiudadNoEncontradaException("No se encontro la ciudad "+ params[0]+ ".");
        }
        return geo.getResults().get(0);
    }

    public String obtenerNombrePais(String codigoPais){
        Locale locale= new Locale("",codigoPais);
        return locale.getDisplayCountry(Locale.forLanguageTag("es"));
    }


    public Clima consultarClima(String ciudad, String provincia, String codigoPais) {
        //SEGUIR CONDICIONAL PARA VER QUÉ METODO EJECUTAR DEPENDIENDO DEL INPUT DE PARAMETROS
        GeocodingResponse.Resultado resultado;
        if(provincia==null && codigoPais!=null ){
            resultado= consultarCoordenadas(URL_CIUDAD_PAIS,ciudad, codigoPais);
        } else if (provincia !=null && codigoPais==null) {
            resultado= consultarCoordenadas(URL_CIUDAD_PROVINCIA,ciudad, provincia);
        } else if (provincia==null && codigoPais==null) {
            resultado= consultarCoordenadas(URL_SOLO_CIUDAD,ciudad);
        } else{
            resultado= consultarCoordenadas(URL_CIUDAD_PROVINCIA_PAIS,ciudad, provincia, codigoPais);
        }

        ForecastResponse forecast = restTemplate.getForObject(FORECAST_URL, ForecastResponse.class, resultado.getLatitude(), resultado.getLongitude());

        if (forecast == null || forecast.getCurrent() == null) {
            throw new RuntimeException("No se pudo obtener el clima para: " + ciudad);
        }

        // Paso 3: armar y guardar
        Clima clima = new Clima(
                resultado.getName(),
                resultado.getAdmin1(),
                resultado.getCountry(),
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

    public Clima climaMayorTemperatura(String ciudad) {
        Clima resultado;
        if(ciudad==null){
            resultado= climaRepository.findTopByOrderByTemperaturaDesc();
        }
        else{
            resultado= climaRepository.findTopByCiudadOrderByTemperaturaDesc(ciudad);
        }
        if(resultado==null){
            if(ciudad==null){
                throw new CiudadNoEncontradaException("Todavia no hay datos de ninguna ciudad.");
            }
            else{
                throw new CiudadNoEncontradaException("No hay datos de la ciudad "+ciudad+ ".");
            }
        }
        return resultado;
    }
    //metodo hecho de una forma alternativa, usando el operador ternario.
    public Clima climaMenorTemperatura(String ciudad){
        Clima resultado=(ciudad == null ? climaRepository.findTopByOrderByTemperaturaAsc() : climaRepository.findTopByCiudadOrderByTemperaturaAsc(ciudad));
        if(resultado==null){
            String message=(ciudad == null ? "Todavia no hay datos de ninguna ciudad." : "No hay datos de la ciudad"+ ciudad );
            throw new CiudadNoEncontradaException(message);
        }
        return resultado;
    }

    public HashSet<String> obtenerCiudades(){
        return climaRepository.obtenerCiudades();
    }


}