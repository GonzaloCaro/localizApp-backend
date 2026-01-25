package com.gestion_transporte.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gestion_transporte.controller.RutaController;
import com.gestion_transporte.model.Ruta;

@Component
public class RutaModelAssembler implements RepresentationModelAssembler<Ruta, EntityModel<Ruta>> {

    @Override
    public EntityModel<Ruta> toModel(Ruta ruta) {
        return EntityModel.of(ruta,
                linkTo(methodOn(RutaController.class).getRutaById(ruta.getId())).withSelfRel(),
                linkTo(methodOn(RutaController.class).getAllRutas()).withRel("rutas"));
    }
}
