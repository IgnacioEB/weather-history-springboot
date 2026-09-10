package com.ignacio.weatherhistory.controller;

import com.ignacio.weatherhistory.model.Clima;
import com.ignacio.weatherhistory.service.ClimaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashSet;

@RestController
@RequestMapping("/clima")
public class ClimaController {

    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @PostMapping("/consultar")
    public ResponseEntity<Clima> consultar(@RequestParam String ciudad) {
        Clima clima = climaService.consultarClima(ciudad);
        return ResponseEntity.status(HttpStatus.CREATED).body(clima);
    }

    @GetMapping("/historial")
    public ResponseEntity<List<Clima>> historial(@RequestParam String ciudad) {
        return ResponseEntity.ok(climaService.obtenerHistorial(ciudad));
    }


    @GetMapping("/promedio")
    public ResponseEntity<Double> promedio(@RequestParam String ciudad, @RequestParam(defaultValue="7") int dias){
        return ResponseEntity.ok(climaService.obtenerTemperaturaPromedio(ciudad, dias));
    }

    @GetMapping("/maxima")
    public ResponseEntity<Clima> maxima(@RequestParam(required = false)String ciudad){
        return ResponseEntity.ok(climaService.climaMayorTemperatura(ciudad));
    }

    @GetMapping("/minima")
    public ResponseEntity<Clima> minima(@RequestParam(required = false)String ciudad){
        return ResponseEntity.ok(climaService.climaMenorTemperatura(ciudad));
    }

    @GetMapping("ciudades_registradas")
    public ResponseEntity<HashSet<String>> ciudades(){
        return ResponseEntity.ok(climaService.obtenerCiudades());
    }


}
