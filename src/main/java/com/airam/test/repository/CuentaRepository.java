package com.airam.test.repository;

import com.airam.test.model.Cuenta;
import com.airam.test.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {
    List<Cuenta> findByUsuarioId(Long usuarioId);
}
