package com.gestion_transporte.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion_transporte.model.Vehiculo;
import com.gestion_transporte.repository.VehiculoRepository;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> getAllVehiculos() {
        log.info("Obteniendo todos los vehiculos");
        return vehiculoRepository.findAll();
    }

    @Transactional
    public Vehiculo createVehiculo(Vehiculo vehiculo) {
        log.info("Creando un nuevo vehiculo: {}", vehiculo);
        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public Vehiculo getVehiculoById(UUID id) {
        log.info("Obteniendo el vehiculo con ID: {}", id);
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con ID: " + id));
    }

    @Transactional
    public Vehiculo updateVehiculo(UUID id, Vehiculo vehiculoDetails) {
        log.info("Actualizando el vehiculo con ID: {}", id);
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehiculo no encontrado con ID: " + id));

        vehiculo.setPatente(vehiculoDetails.getPatente());
        vehiculo.setMarca(vehiculoDetails.getMarca());
        vehiculo.setModelo(vehiculoDetails.getModelo());
        vehiculo.setEstado(vehiculoDetails.getEstado());
        vehiculo.setTipo(vehiculoDetails.getTipo());
        vehiculo.setKilometraje(vehiculoDetails.getKilometraje());
        vehiculo.setAnio(vehiculoDetails.getAnio());

        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public void deleteVehiculo(UUID id) {
        log.info("Eliminando el vehiculo con ID: {}", id);
        vehiculoRepository.deleteById(id);
    }
}
