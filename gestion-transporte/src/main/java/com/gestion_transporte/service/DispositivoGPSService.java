package com.gestion_transporte.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.model.DispositivoGPS;
import com.gestion_transporte.repository.DispositivoGPSRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DispositivoGPSService {

    private final DispositivoGPSRepository dispositivoGPSRepository;

    public DispositivoGPSService(DispositivoGPSRepository dispositivoGPSRepository) {
        this.dispositivoGPSRepository = dispositivoGPSRepository;
    }

    public List<DispositivoGPS> getAllDispositivosGPS() {
        log.info("Obteniendo todos los dispositivos GPS");
        return dispositivoGPSRepository.findAll();
    }

    @Transactional
    public DispositivoGPS createDispositivoGPS(DispositivoGPS dispositivoGPS) {
        log.info("Creando un nuevo dispositivo GPS: {}", dispositivoGPS);
        DispositivoGPS newDispositivoGPS = new DispositivoGPS();
        newDispositivoGPS.setSerialNumber(dispositivoGPS.getSerialNumber());
        newDispositivoGPS.setModelo(dispositivoGPS.getModelo());
        newDispositivoGPS.setVehiculo(dispositivoGPS.getVehiculo());
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
