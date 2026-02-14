package com.gestion_transporte.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion_transporte.DTO.UbicacionDTO;
import com.gestion_transporte.model.Ruta;
import com.gestion_transporte.model.Ubicacion;
import com.gestion_transporte.repository.UbicacionRepository;
import com.gestion_transporte.repository.RutaRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    public UbicacionService(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    public List<Ubicacion> getAllUbicaciones() {
        log.info("Obteniendo todas las ubicaciones");
        return ubicacionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Ubicacion getUbicacionById(UUID id) {
        log.info("Obteniendo la ubicacion con ID: {}", id);
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con ID: " + id));
    }

    @Transactional
    public Ubicacion crearUbicacion(UbicacionDTO dto) {
        log.info("Creando una nueva ubicacion para GPS: {}", dto.getGps());

        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(dto.getLatitud());
        ubicacion.setLongitud(dto.getLongitud());
        ubicacion.setVelocidad(dto.getVelocidad());
        ubicacion.setFechaHora(dto.getFechaHora());
        ubicacion.setGps(dto.getGps());
        return ubicacionRepository.save(ubicacion);
    } 

    @Transactional
    public Ubicacion updateUbicacion(UUID id, UbicacionDTO dto) {
        log.info("Actualizando la ubicacion con ID: {}", id);

        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada con ID: " + id));

        ubicacion.setLatitud(dto.getLatitud());
        ubicacion.setLongitud(dto.getLongitud());
        ubicacion.setVelocidad(dto.getVelocidad());
        ubicacion.setFechaHora(dto.getFechaHora());
        ubicacion.setGps(dto.getGps());
        return ubicacionRepository.save(ubicacion);
    }

    @Transactional
    public void deleteUbicacion(UUID id) {
        log.info("Eliminando la ubicacion con ID: {}", id);
        if (!ubicacionRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, ubicacion no encontrada");
        }
        ubicacionRepository.deleteById(id);
    }
}