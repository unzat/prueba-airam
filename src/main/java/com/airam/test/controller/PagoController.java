package com.airam.test.controller;

import com.airam.test.model.Pago;
import com.airam.test.service.PagoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Pagos realizados por los usuarios")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    @PostMapping("/realizar")
    public ResponseEntity<?> realizarPago(@RequestBody Pago pago) {
        try {
            Pago pagoRealizado = pagoService.realizarPago(pago);
            return ResponseEntity.ok(pagoRealizado);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/historial")
    public ResponseEntity<?> obtenerHistorialPagos(@RequestParam Long usuarioId,
                                                   @RequestParam String fechaInicio,
                                                   @RequestParam String fechaFin) {
        // Convertir las cadenas de fecha en objetos java.sql.Date
        Date inicio = Date.valueOf(fechaInicio);
        Date fin = Date.valueOf(fechaFin);

        List<Pago> pagos = pagoService.obtenerPagosPorFecha(usuarioId, inicio, fin);

        return pagos.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron pagos en el rango de fechas especificado") : ResponseEntity.ok(pagos);
    }

    @GetMapping("/servicio")
    public ResponseEntity<?> obtenerPagosPorServicio(@RequestParam Long usuarioId,
                                                     @RequestParam Long servicioId) {
        List<Pago> pagos = pagoService.obtenerPagosPorServicio(usuarioId, servicioId);
        return pagos.isEmpty() ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Datos no encontrados") : ResponseEntity.ok(pagos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPagoPorId(@PathVariable Long id) {
        Optional<Pago> pago = pagoService.obtenerPagoPorId(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarPago(@PathVariable Long id, @RequestBody Pago pago) {
        try {
            Pago pagoActualizado = pagoService.actualizarPago(id, pago);
            return ResponseEntity.ok(pagoActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarPago(@PathVariable Long id) {
        try {
            pagoService.eliminarPago(id);
            return ResponseEntity.ok("Pago eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
