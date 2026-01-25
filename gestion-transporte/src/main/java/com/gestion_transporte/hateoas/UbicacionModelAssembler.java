package com.gestion_transporte.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.gestion_transporte.controller.UbicacionController;
import com.gestion_transporte.model.Ubicacion;

@Component
public class UbicacionModelAssembler extends RepresentationModelAssemblerSupport<Ubicacion, EntityModel<Ubicacion>> {
    @SuppressWarnings("unchecked")
    public UbicacionModelAssembler() {
        super(UbicacionController.class, (Class<EntityModel<Ubicacion>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Ubicacion> toModel(Ubicacion ubicacion) {
        EntityModel<Ubicacion> ubicacionModel = createModelWithId(ubicacion.getId(), ubicacion);
        return EntityModel.of(ubicacion,
                linkTo(methodOn(UbicacionController.class).getUbicacionById(ubicacion.getId())).withSelfRel());

    }

}
