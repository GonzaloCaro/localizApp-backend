package com.gestion_transporte.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion_transporte.DTO.DispositivoGPSDTO;
import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.exception.ResourceNotFoundException;
import com.gestion_transporte.hateoas.DispositivoGPSModelAssembler;
import com.gestion_transporte.mapper.DispositivoGPSMapper;
import com.gestion_transporte.mapper.VehiculoMapper;
import com.gestion_transporte.model.DispositivoGPS;
import com.gestion_transporte.service.DispositivoGPSService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/dispositivos-gps")
public class DispositivoGPSController {

    private final DispositivoGPSService dispositivoGPSService;
    private final DispositivoGPSMapper dispositivoGPSMapper;
    private final DispositivoGPSModelAssembler dispositivoGPSModelAssembler;

    public DispositivoGPSController(DispositivoGPSService dispositivoGPSService,
            DispositivoGPSMapper dispositivoGPSMapper,
            DispositivoGPSModelAssembler dispositivoGPSModelAssembler) {
        this.dispositivoGPSService = dispositivoGPSService;
        this.dispositivoGPSMapper = dispositivoGPSMapper;
        this.dispositivoGPSModelAssembler = dispositivoGPSModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<DispositivoGPS>>> getAllDispositivosGPS() {
        log.info("Recibiendo solicitud para obtener todos los dispositivos GPS");
        List<DispositivoGPS> dispositivosGPSList = dispositivoGPSService.getAllDispositivosGPS();

        if (dispositivosGPSList.isEmpty()) {
            log.warn("No se encontraron dispositivos GPS");
            throw new ResourceNotFoundException("No se encontraron dispositivos GPS");
        }

        List<EntityModel<DispositivoGPS>> dispositivosGPSModels = dispositivosGPSList.stream()
                .map(dispositivoGPSModelAssembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<DispositivoGPS>> collectionModel = CollectionModel.of(dispositivosGPSModels,
                linkTo(methodOn(DispositivoGPSController.class).getAllDispositivosGPS()).withSelfRel());
        log.info("Enviando {} dispositivos GPS encontrados", dispositivosGPSModels.size());
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<DispositivoGPS>> getDispositivoGPSById(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para obtener el dispositivo GPS con ID: {}", id);
        DispositivoGPS dispositivoGPS = dispositivoGPSService.getDispositivoGPSById(id);

        EntityModel<DispositivoGPS> dispositivoGPSModel = dispositivoGPSModelAssembler.toModel(dispositivoGPS);

        log.info("Enviando dispositivo GPS con ID: {}", id);
        return ResponseEntity.ok(dispositivoGPSModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<DispositivoGPS>> createDispositivoGPS(@Valid @RequestBody DispositivoGPS dto) {
        log.info("Recibiendo solicitud para crear un nuevo dispositivo GPS");
        if (dto == null) {
            log.error("El DTO de dispositivo GPS es nulo");
            throw new IllegalArgumentException("El DTO de dispositivo GPS no puede ser nulo");
        }
        try {
            DispositivoGPS newDispositivoGPS = dispositivoGPSService.createDispositivoGPS(dto);
            EntityModel<DispositivoGPS> dispositivoGPSModel = dispositivoGPSModelAssembler.toModel(newDispositivoGPS);
            return ResponseEntity.ok(dispositivoGPSModel);
        } catch (Exception e) {
            log.error("Error al crear el dispositivo GPS: {}", e.getMessage());
            throw new RuntimeException("Error al crear el dispositivo GPS", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<DispositivoGPS>> updateDispositivoGPS(@PathVariable UUID id,
            @Valid @RequestBody DispositivoGPSDTO dto) {
        log.info("Recibiendo solicitud para actualizar el dispositivo GPS con ID: {}", id);
        if (dto == null) {
            log.error("El DTO de dispositivo GPS es nulo");
            throw new IllegalArgumentException("El DTO de dispositivo GPS no puede ser nulo");
        }
        try {
            DispositivoGPS dispositivoGPSToUpdate = dispositivoGPSMapper.toEntity(dto);
            DispositivoGPS updatedDispositivoGPS = dispositivoGPSService.updateDispositivoGPS(id,
                    dispositivoGPSToUpdate);
            EntityModel<DispositivoGPS> dispositivoGPSModel = dispositivoGPSModelAssembler
                    .toModel(updatedDispositivoGPS);
            return ResponseEntity.ok(dispositivoGPSModel);
        } catch (Exception e) {
            log.error("Error al actualizar el dispositivo GPS con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar el dispositivo GPS", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositivoGPS(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar el dispositivo GPS con ID: {}", id);
        if (id == null) {
            log.error("El ID del dispositivo GPS no puede ser nulo");
            throw new IllegalArgumentException("El ID del dispositivo GPS no puede ser nulo");
        }
        try {
            dispositivoGPSService.deleteDispositivoGPS(id);
            log.info("Dispositivo GPS con ID {} eliminado exitosamente", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error al eliminar el dispositivo GPS con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar el dispositivo GPS", e);
        }
    }

}
