package com.ignacio.weatherhistory.repository;

import com.ignacio.weatherhistory.model.Clima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClimaRepository extends JpaRepository<Clima, Long> {

    List<Clima> findByCiudadOrderByFechaConsultaDesc(String ciudad);


}
