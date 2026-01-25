package com.gestion_transporte.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

import com.gestion_transporte.DTO.UbicacionDTO;
import com.gestion_transporte.exception.ResourceNotFoundException;
import com.gestion_transporte.hateoas.UbicacionModelAssembler;
import com.gestion_transporte.model.ResponseWrapper;
import com.gestion_transporte.model.Ubicacion;
import com.gestion_transporte.service.UbicacionService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/ubicaciones")
public class UbicacionController {

    private final UbicacionService ubicacionService;
    private final UbicacionModelAssembler ubicacionModelAssembler;

    public UbicacionController(UbicacionService ubicacionService,
            UbicacionModelAssembler ubicacionModelAssembler) {
        this.ubicacionService = ubicacionService;
        this.ubicacionModelAssembler = ubicacionModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Ubicacion>>> getAllUbicaciones() {
        log.info("Recibiendo solicitud para obtener todas las ubicaciones");
        List<Ubicacion> ubicaciones = ubicacionService.getAllUbicaciones();

        if (ubicaciones.isEmpty()) {
            log.warn("No se encontraron ubicaciones");
            throw new ResourceNotFoundException("No se encontraron ubicaciones");
        }
        log.debug("Controller: Se encontraron {} ubicaciones", ubicaciones.size());

        List<EntityModel<Ubicacion>> ubicacionModels = ubicaciones.stream()
                .map(ubicacionModelAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(ubicacionModels,
                        linkTo(methodOn(UbicacionController.class).getAllUbicaciones()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ubicacion>> getUbicacionById(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para obtener la ubicacion con ID: {}", id);
        Ubicacion ubicacion = ubicacionService.getUbicacionById(id);

        if (ubicacion == null) {
            log.warn("Ubicacion no encontrada con ID: {}", id);
            throw new ResourceNotFoundException("Ubicacion no encontrada con ID: " + id);
        }
        log.debug("Controller: Ubicacion encontrada: {}", ubicacion);

        EntityModel<Ubicacion> ubicacionModel = ubicacionModelAssembler.toModel(ubicacion);
        return ResponseEntity.ok(ubicacionModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Ubicacion>> createUbicacion(@Valid @RequestBody UbicacionDTO ubicacionDTO) {
        log.info("Recibiendo solicitud para crear una nueva ubicación: {}", ubicacionDTO);

        if (ubicacionDTO == null) {
            log.warn("La ubicación proporcionada es nula");
            throw new IllegalArgumentException("La ubicación no puede ser nula");
        }

        try {
            // El servicio se encarga de validar si el paciente existe o crear uno nuevo
            log.debug("Controller: Enviando datos al servicio para crear la ubicación: {}", ubicacionDTO);
            Ubicacion createdUbicacion = ubicacionService.crearUbicacion(ubicacionDTO);

            log.debug("Controller: Ubicación creada exitosamente con ID {}", createdUbicacion.getId());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ubicacionModelAssembler.toModel(createdUbicacion));
        } catch (Exception e) {
            log.error("Error al crear la asignación: {}", e.getMessage(), e);
            throw e; // o podrías envolverlo en una excepción custom tipo AsignacionCreationException
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ubicacion>> updateUbicacion(@PathVariable UUID id,
            @RequestBody UbicacionDTO ubicacionDTO) {
        log.info("Recibiendo solicitud para actualizar la ubicacion con ID: {}", id);
        if (id == null) {
            log.error("El ID de la ubicacion no puede ser nulo");
            throw new IllegalArgumentException("El ID de la ubicacion no puede ser nulo");
        }
        try {
            Ubicacion ubicacion = ubicacionService.updateUbicacion(id, ubicacionDTO);
            EntityModel<Ubicacion> ubicacionModel = ubicacionModelAssembler.toModel(ubicacion);
            return ResponseEntity.ok(ubicacionModel);
        } catch (Exception e) {
            throw e;
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUbicacion(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar la ubicacion con ID: {}", id);
        if (id == null) {
            log.error("El ID de la ubicacion no puede ser nulo");
            throw new IllegalArgumentException("El ID de la ubicacion no puede ser nulo");
        }
        try {
            ubicacionService.deleteUbicacion(id);
            log.debug("Controller: Ubicacion con ID {} eliminada", id);
            return ResponseEntity.ok(
                    new ResponseWrapper<>(
                            "Ubicacion eliminada exitosamente",
                            0,
                            null));
        } catch (Exception e) {
            throw e;
        }

    }

}
