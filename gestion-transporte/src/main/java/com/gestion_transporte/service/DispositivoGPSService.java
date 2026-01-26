package com.gestion_transporte.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestion_transporte.DTO.DispositivoGPSDTO;
import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.exception.ResourceNotFoundException;
import com.gestion_transporte.model.DispositivoGPS;
import com.gestion_transporte.model.Vehiculo;
import com.gestion_transporte.repository.DispositivoGPSRepository;
import com.gestion_transporte.repository.VehiculoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DispositivoGPSService {

    private final DispositivoGPSRepository dispositivoGPSRepository;
    private final VehiculoRepository vehiculoRepository;

    public DispositivoGPSService(DispositivoGPSRepository dispositivoGPSRepository,
            VehiculoRepository vehiculoRepository) {
        this.dispositivoGPSRepository = dispositivoGPSRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<DispositivoGPS> getAllDispositivosGPS() {
        log.info("Obteniendo todos los dispositivos GPS");
        return dispositivoGPSRepository.findAll();
    }

    @Transactional
    // CAMBIO: El método ahora recibe el DTO
    public DispositivoGPS createDispositivoGPS(DispositivoGPSDTO dto) {
        log.info("Creando dispositivo desde DTO: {}", dto);

        DispositivoGPS newDispositivoGPS = new DispositivoGPS();
        newDispositivoGPS.setSerialNumber(dto.getSerialNumber());
        newDispositivoGPS.setModelo(dto.getModelo());

        // LÓGICA DE ASIGNACIÓN:
        // Buscamos el vehículo por el ID que viene en el JSON
        if (dto.getVehiculoId() != null) {
            Vehiculo vehiculo = vehiculoRepository.findById(dto.getVehiculoId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Vehículo no encontrado con ID: " + dto.getVehiculoId()));

            // Asignamos la relación (JPA se encarga del resto)
            newDispositivoGPS.setVehiculo(vehiculo);
        }

        return dispositivoGPSRepository.save(newDispositivoGPS);
    }

    @Transactional
    public DispositivoGPS getDispositivoGPSById(UUID id) {
        log.info("Obteniendo el dispositivo GPS con ID: {}", id);
        return dispositivoGPSRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo GPS no encontrado con ID: " + id));
    }

    @Transactional
    public DispositivoGPS updateDispositivoGPS(UUID id, DispositivoGPS dispositivoGPS) {
        log.info("Actualizando el dispositivo GPS con ID: {}", id);
        DispositivoGPS existingDispositivoGPS = dispositivoGPSRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo GPS no encontrado con ID: " + id));
        existingDispositivoGPS.setSerialNumber(dispositivoGPS.getSerialNumber());
        existingDispositivoGPS.setModelo(dispositivoGPS.getModelo());
        existingDispositivoGPS.setVehiculo(dispositivoGPS.getVehiculo());
        return dispositivoGPSRepository.save(existingDispositivoGPS);
    }

    @Transactional
    public void deleteDispositivoGPS(UUID id) {
        log.info("Eliminando el dispositivo GPS con ID: {}", id);
        DispositivoGPS dispositivoGPS = dispositivoGPSRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dispositivo GPS no encontrado con ID: " + id));
        dispositivoGPSRepository.delete(dispositivoGPS);
    }

}
