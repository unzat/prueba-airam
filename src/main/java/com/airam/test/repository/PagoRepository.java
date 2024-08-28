package com.airam.test.repository;

import com.airam.test.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findPagosByCuentaUsuarioId(Long usuarioId);
    List<Pago> findAllByCuentaUsuarioIdAndFechaPagoBetween(Long usuarioId, Date fechaInicio, Date fechaFin);
    List<Pago> findAllByCuentaUsuarioIdAndServicioId(Long usuarioId, Long servicioId);
}
