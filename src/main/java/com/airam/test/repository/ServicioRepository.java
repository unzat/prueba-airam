package com.airam.test.repository;

import com.airam.test.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    Servicio findByNombreServicio(String nombreServicio);
}
