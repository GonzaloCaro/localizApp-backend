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

import com.gestion_transporte.DTO.RutaDTO;
import com.gestion_transporte.exception.ResourceNotFoundException;
import com.gestion_transporte.hateoas.RutaModelAssembler;
import com.gestion_transporte.mapper.RutaMapper;
import com.gestion_transporte.model.Ruta;
import com.gestion_transporte.service.RutaService;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Slf4j
@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    private final RutaService rutaService;
    private final RutaMapper rutaMapper;
    private final RutaModelAssembler rutaModelAssembler;

    public RutaController(RutaService rutaService, RutaMapper rutaMapper,
            RutaModelAssembler rutaModelAssembler) {
        this.rutaService = rutaService;
        this.rutaMapper = rutaMapper;
        this.rutaModelAssembler = rutaModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Ruta>>> getAllRutas() {
        log.info("Recibiendo solicitud para obtener todas las rutas");
        List<Ruta> rutas = rutaService.getAllRutas();
        if (rutas.isEmpty()) {
            log.warn("No se encontraron rutas");
            throw new ResourceNotFoundException("No se encontraron rutas");
        }

        List<EntityModel<Ruta>> rutaModels = rutas.stream()
                .map(rutaModelAssembler::toModel)
                .collect(Collectors.toList());
        CollectionModel<EntityModel<Ruta>> collectionModel = CollectionModel.of(rutaModels,
                linkTo(methodOn(RutaController.class).getAllRutas()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Ruta>> getRutaById(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para obtener la ruta con ID: {}", id);
        Ruta ruta = rutaService.getRutaById(id);
        EntityModel<Ruta> rutaModel = rutaModelAssembler.toModel(ruta);
        return ResponseEntity.ok(rutaModel);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<EntityModel<Ruta>> getRutaByNombre(@PathVariable String nombre) {
        log.info("Recibiendo solicitud para obtener la ruta con nombre: {}", nombre);
        Ruta ruta = rutaService.getRutaByNombre(nombre);
        EntityModel<Ruta> rutaModel = rutaModelAssembler.toModel(ruta);
        return ResponseEntity.ok(rutaModel);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Ruta>> createRuta(@RequestBody RutaDTO rutaDTO) {
        log.info("Recibiendo solicitud para crear una nueva ruta");
        if (rutaDTO == null) {
            log.error("El DTO de ruta es nulo");
            throw new IllegalArgumentException("El DTO de ruta no puede ser nulo");
        }
        try {
            Ruta nuevaRuta = rutaService.createRuta(rutaDTO);
            EntityModel<Ruta> rutaModel = rutaModelAssembler.toModel(nuevaRuta);
            return ResponseEntity.status(201).body(rutaModel);
        } catch (Exception e) {
            log.error("Error al crear la ruta: {}", e.getMessage());
            throw new RuntimeException("Error al crear la ruta", e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Ruta>> updateRuta(@PathVariable UUID id,
            @RequestBody RutaDTO rutaDTO) {
        log.info("Recibiendo solicitud para actualizar la ruta con ID: {}", id);
        if (rutaDTO == null) {
            log.error("El DTO de ruta es nulo");
            throw new IllegalArgumentException("El DTO de ruta no puede ser nulo");
        }
        try {
            Ruta rutaToUpdate = rutaMapper.toEntity(rutaDTO);
            Ruta updatedRuta = rutaService.updateRuta(id, rutaToUpdate);
            EntityModel<Ruta> rutaModel = rutaModelAssembler.toModel(updatedRuta);
            return ResponseEntity.ok(rutaModel);
        } catch (Exception e) {
            log.error("Error al actualizar la ruta con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al actualizar la ruta", e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRuta(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar la ruta con ID: {}", id);
        try {
            rutaService.deleteRuta(id);
            log.info("Ruta con ID {} eliminada exitosamente", id);
            return ResponseEntity.status(200).build();
        } catch (Exception e) {
            log.error("Error al eliminar la ruta con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error al eliminar la ruta", e);
        }
    }
}
