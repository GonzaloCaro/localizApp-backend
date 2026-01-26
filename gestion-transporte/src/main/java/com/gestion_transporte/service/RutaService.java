package com.gestion_transporte.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion_transporte.DTO.RutaDTO;
import com.gestion_transporte.model.Ruta;
import com.gestion_transporte.repository.RutaRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RutaService {

    private final RutaRepository rutaRepository;

    public RutaService(RutaRepository rutaRepository) {
        this.rutaRepository = rutaRepository;
    }

    public List<Ruta> getAllRutas() {
        log.info("Obteniendo todas las rutas");
        return rutaRepository.findAll();
    }

    @Transactional
    public Ruta createRuta(RutaDTO rutaDTO) {
        log.info("Creando una nueva ruta");
        Ruta ruta = new Ruta();
        ruta.setVehiculo(rutaDTO.getVehiculo());
        ruta.setNombre(rutaDTO.getNombre());
        ruta.setOrigen(rutaDTO.getOrigen());
        ruta.setDestino(rutaDTO.getDestino());
        return rutaRepository.save(ruta);
    }

    @Transactional
    public Ruta getRutaById(UUID id) {
        log.info("Obteniendo la ruta con ID: {}", id);
        return rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));
    }

    @Transactional
    public Ruta getRutaByNombre(String nombre) {
        log.info("Obteniendo la ruta con nombre: {}", nombre);
        return rutaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con nombre: " + nombre));
    }

    @Transactional
    public Ruta updateRuta(UUID id, Ruta ruta) {
        log.info("Actualizando la ruta con ID: {}", id);
        Ruta existingRuta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));
        existingRuta.setVehiculo(ruta.getVehiculo());
        existingRuta.setNombre(ruta.getNombre());
        existingRuta.setOrigen(ruta.getOrigen());
        existingRuta.setDestino(ruta.getDestino());
        return rutaRepository.save(existingRuta);
    }

    @Transactional
    public void deleteRuta(UUID id) {
        log.info("Eliminando la ruta con ID: {}", id);
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada con ID: " + id));
        rutaRepository.delete(ruta);
    }
}
