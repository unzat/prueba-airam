package com.airam.test.service;

import com.airam.test.model.Pago;
import com.airam.test.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {
    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private ServicioService servicioService;

    public Pago realizarPago(Pago pago) {
        // Validar si la cuenta existe
        if (!cuentaService.obtenerCuentaPorId(pago.getCuenta().getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada.");
        }

        // Validar si el servicio existe
        if (!servicioService.obtenerServicioPorId(pago.getServicio().getId()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado.");
        }

        // Validar que el usuario tenga saldo suficiente
        if (!cuentaService.verificarSaldo(pago.getCuenta().getId(), pago.getMontoAbonado())) {
            throw new IllegalArgumentException("Saldo insuficiente en la cuenta.");
        }

        // Actualizar saldo de la cuenta
        boolean saldoActualizado = cuentaService.actualizarSaldo(pago.getCuenta().getId(), pago.getMontoAbonado().negate());
        if (!saldoActualizado) {
            throw new IllegalStateException("Error al actualizar el saldo de la cuenta.");
        }

        // Guardar la transacción de pago
        pago.setFechaPago(new Date(System.currentTimeMillis()));  // Solo la fecha actual
        return pagoRepository.save(pago);
    }

    public List<Pago> obtenerPagosPorFecha(Long usuarioId, Date fechaInicio, Date fechaFin) {
        return pagoRepository.findAllByCuentaUsuarioIdAndFechaPagoBetween(usuarioId, fechaInicio, fechaFin);
    }

    public List<Pago> obtenerPagosPorServicio(Long usuarioId, Long servicioId) {
        return pagoRepository.findAllByCuentaUsuarioIdAndServicioId(usuarioId, servicioId);
    }

    public Optional<Pago> obtenerPagoPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public Pago actualizarPago(Long id, Pago pagoActualizado) {
        return pagoRepository.findById(id)
                .map(pago -> {
                    pago.setNumeroReferenciaComprobante(pagoActualizado.getNumeroReferenciaComprobante());
                    pago.setMontoDeudaTotal(pagoActualizado.getMontoDeudaTotal());
                    pago.setMontoAbonado(pagoActualizado.getMontoAbonado());
                    return pagoRepository.save(pago);
                })
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
    }

    public void eliminarPago(Long id) {
        if (pagoRepository.existsById(id)) {
            pagoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Pago no encontrado");
        }
    }
}
