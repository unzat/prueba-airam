package com.airam.test.service;

import com.airam.test.model.Cuenta;
import com.airam.test.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Optional<Cuenta> obtenerCuentaPorId(Long id) {
        return cuentaRepository.findById(id);
    }

    public List<Cuenta> obtenerCuentasPorUsuarioId(Long usuarioId) {
        return cuentaRepository.findByUsuarioId(usuarioId);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        return cuentaRepository.findById(id)
                .map(cuenta -> {
                    cuenta.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
                    cuenta.setSaldo(cuentaActualizada.getSaldo());
                    return cuentaRepository.save(cuenta);
                })
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));
    }

    public void eliminarCuenta(Long id) {
        if (cuentaRepository.existsById(id)) {
            cuentaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Cuenta no encontrada");
        }
    }

    public boolean actualizarSaldo(Long cuentaId, BigDecimal monto) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(cuentaId);
        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();
            cuenta.setSaldo(cuenta.getSaldo().add(monto));
            cuentaRepository.save(cuenta);
            return true;
        }
        return false;
    }

    public boolean verificarSaldo(Long cuentaId, BigDecimal monto) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(cuentaId);
        return cuentaOpt.map(cuenta -> cuenta.getSaldo().compareTo(monto) >= 0).orElse(false);
    }
}
