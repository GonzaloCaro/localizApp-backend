package com.gestion_transporte.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.gestion_transporte.model.ResponseWrapper;
import com.gestion_transporte.model.Vehiculo;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.exception.ResourceNotFoundException;
import com.gestion_transporte.hateoas.VehiculoModelAssembler;
import com.gestion_transporte.mapper.VehiculoMapper;
import com.gestion_transporte.service.VehiculoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final VehiculoMapper vehiculoMapper;
    private final VehiculoModelAssembler vehiculoModelAssembler;

    public VehiculoController(VehiculoService vehiculoService, VehiculoMapper vehiculoMapper,
            VehiculoModelAssembler vehiculoModelAssembler) {
        this.vehiculoService = vehiculoService;
        this.vehiculoMapper = vehiculoMapper;
        this.vehiculoModelAssembler = vehiculoModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Vehiculo>>> getAllVehiculos() {
        log.info("Recibiendo solicitud para obtener todos los vehiculos");
        List<Vehiculo> vehiculos = vehiculoService.getAllVehiculos();

        if (vehiculos.isEmpty()) {
            log.warn("No se encontraron vehiculos");
            throw new ResourceNotFoundException("No se encontraron vehiculos");
        }
        log.debug("Controller: Se encontraron {} vehiculos", vehiculos.size());

        List<EntityModel<Vehiculo>> vehiculoModels = vehiculos.stream()
                .map(vehiculoModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(vehiculoModels,
                        linkTo(methodOn(VehiculoController.class).getAllVehiculos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Vehiculo>> getVehiculoById(UUID id) {
        log.info("Recibiendo solicitud para obtener el vehiculo con ID: {}", id);
        Vehiculo vehiculo = vehiculoService.getVehiculoById(id);

        if (vehiculo == null) {
            log.warn("Vehiculo no encontrado con ID: {}", id);
            throw new ResourceNotFoundException("Vehiculo no encontrado con ID: " + id);
        }
        log.debug("Controller: Vehiculo encontrado: {}", vehiculo);

        EntityModel<Vehiculo> vehiculoModel = vehiculoModelAssembler.toModel(vehiculo);
        return ResponseEntity.ok(vehiculoModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Vehiculo>> createVehiculo(@Valid @RequestBody VehiculoDTO vehiculo) {
        log.info("Recibiendo solicitud para crear un nuevo vehiculo: {}", vehiculo);
        if (vehiculo == null) {
            log.warn("El vehiculo proporcionado es nulo");
            throw new IllegalArgumentException("El vehiculo no puede ser nulo");
        }
        try {
            Vehiculo createdVehiculo = vehiculoService
                    .createVehiculo(vehiculoMapper.toEntity(vehiculo));
            log.debug("Controller: Vehiculo creado: {}", createdVehiculo);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(vehiculoModelAssembler.toModel(createdVehiculo));
        } catch (Exception e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Vehiculo>> updateVehiculo(@PathVariable UUID id,
            @RequestBody VehiculoDTO vehiculoDTO) {
        log.info("Recibiendo solicitud para actualizar el vehiculo con ID: {}", id);
        if (id == null) {
            log.error("El ID del vehiculo no puede ser nulo");
            throw new IllegalArgumentException("El ID del vehiculo no puede ser nulo");
        }
        try {
            Vehiculo vehiculoDetails = vehiculoMapper.toEntity(vehiculoDTO);
            Vehiculo vehiculo = vehiculoService.updateVehiculo(id, vehiculoDetails);
            EntityModel<Vehiculo> vehiculoModel = vehiculoModelAssembler.toModel(vehiculo);
            return ResponseEntity.ok(vehiculoModel);
        } catch (Exception e) {
            throw e;
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehiculo(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar el vehiculo con ID: {}", id);
        if (id == null) {
            log.error("El ID del vehiculo no puede ser nulo");
            throw new IllegalArgumentException("El ID del vehiculo no puede ser nulo");
        }
        try {
            vehiculoService.deleteVehiculo(id);
            log.debug("Controller: Vehiculo con ID {} eliminado", id);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(
                            "Vehiculo eliminado exitosamente",
                            0,
                            null));
        } catch (Exception e) {
            throw e;
        }

    }

}
