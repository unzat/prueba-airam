package com.airam.test.controller;

import com.airam.test.model.Cuenta;
import com.airam.test.service.CuentaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cuentas")
@Tag(name = "Cuentas", description = "Operaciones relacionadas con las cuentas de los usuarios")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody Cuenta cuenta) {
        Cuenta nuevaCuenta = cuentaService.crearCuenta(cuenta);
        return ResponseEntity.ok(nuevaCuenta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCuentaPorId(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.obtenerCuentaPorId(id);
        return cuenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerCuentasPorUsuarioId(@PathVariable Long usuarioId) {
        List<Cuenta> cuentas = cuentaService.obtenerCuentasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(cuentas);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        try {
            Cuenta cuentaActualizada = cuentaService.actualizarCuenta(id, cuenta);
            return ResponseEntity.ok(cuentaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long id) {
        try {
            cuentaService.eliminarCuenta(id);
            return ResponseEntity.ok("Cuenta eliminada correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/actualizar-saldo")
    public ResponseEntity<?> actualizarSaldo(@RequestParam Long cuentaId, @RequestParam BigDecimal monto) {
        boolean actualizado = cuentaService.actualizarSaldo(cuentaId, monto);
        if (actualizado) {
            return ResponseEntity.ok("Saldo actualizado correctamente");
        } else {
            return ResponseEntity.badRequest().body("Error al actualizar el saldo");
        }
    }

    @GetMapping("/verificar-saldo")
    public ResponseEntity<?> verificarSaldo(@RequestParam Long cuentaId, @RequestParam BigDecimal monto) {
        boolean saldoSuficiente = cuentaService.verificarSaldo(cuentaId, monto);
        if (saldoSuficiente) {
            return ResponseEntity.ok("Saldo suficiente");
        } else {
            return ResponseEntity.badRequest().body("Saldo insuficiente");
        }
    }
}
