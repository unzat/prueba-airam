package com.airam.test.controller;

import com.airam.test.model.Servicio;
import com.airam.test.service.ServicioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/servicios")
@Tag(name = "Servicios", description = "Servicio disponibles para pagar")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearServicio(@RequestBody Servicio servicio) {
        Servicio nuevoServicio = servicioService.crearServicio(servicio);
        return ResponseEntity.ok(nuevoServicio);
    }

    @GetMapping()
    public ResponseEntity<?> obtenerTodosLosServicios() {
        List<Servicio> servicios = servicioService.obtenerTodosLosServicios();
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerServicioPorId(@PathVariable Long id) {
        Optional<Servicio> servicios = servicioService.obtenerServicioPorId(id);
        return ResponseEntity.ok(servicios);
    }

    @GetMapping("/nombre/{nombreServicio}")
    public ResponseEntity<?> obtenerServicioPorNombre(@PathVariable String nombreServicio) {
        Servicio servicio = servicioService.obtenerServicioPorNombre(nombreServicio);
        return servicio != null ? ResponseEntity.ok(servicio) : ResponseEntity.notFound().build();
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarServicio(@PathVariable Long id, @RequestBody Servicio servicio) {
        try {
            Servicio servicioActualizado = servicioService.actualizarServicio(id, servicio);
            return ResponseEntity.ok(servicioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable Long id) {
        try {
            servicioService.eliminarServicio(id);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
