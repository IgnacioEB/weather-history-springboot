package com.ignacio.weatherhistory.repository;

import com.ignacio.weatherhistory.model.Clima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
//java persistence api repository
public interface ClimaRepository extends JpaRepository<Clima, Long> {

    List<Clima> findByCiudadOrderByFechaConsultaDesc(String ciudad);


    @Query("SELECT AVG(c.temperatura) FROM Clima c WHERE c.ciudad= :ciudad AND c.fechaConsulta>=:desde")
    Double ObtenerTemperaturaPromedio(@Param("ciudad")String ciudad,@Param("desde") LocalDateTime desde);

    Clima findTopByOrderByTemperaturaDesc();
    Clima findTopByCiudadOrderByTemperaturaDesc(String ciudad);
    Clima findTopByOrderByTemperaturaAsc();
    Clima findTopByCiudadOrderByTemperaturaAsc(String ciudad);
    @Query("select c.ciudad FROM Clima c")
    List<String> obtenerCiudades();
}
