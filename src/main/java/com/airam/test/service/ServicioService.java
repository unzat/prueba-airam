package com.airam.test.service;

import com.airam.test.model.Servicio;
import com.airam.test.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public Servicio crearServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id);
    }

    public Servicio obtenerServicioPorNombre(String nombreServicio) {
        return servicioRepository.findByNombreServicio(nombreServicio);
    }

    public Servicio actualizarServicio(Long id, Servicio servicioActualizado) {
        return servicioRepository.findById(id)
                .map(servicio -> {
                    servicio.setNombreServicio(servicioActualizado.getNombreServicio());
                    return servicioRepository.save(servicio);
                })
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
    }

    public void eliminarServicio(Long id) {
        if (servicioRepository.existsById(id)) {
            servicioRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Servicio no encontrado");
        }
    }
}