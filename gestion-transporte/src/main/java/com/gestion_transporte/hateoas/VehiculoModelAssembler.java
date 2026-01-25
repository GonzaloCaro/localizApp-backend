package com.gestion_transporte.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.gestion_transporte.controller.VehiculoController;
import com.gestion_transporte.model.Vehiculo;

@Component
public class VehiculoModelAssembler
        extends RepresentationModelAssemblerSupport<Vehiculo, EntityModel<Vehiculo>> {
    @SuppressWarnings("unchecked")
    public VehiculoModelAssembler() {
        super(VehiculoController.class, (Class<EntityModel<Vehiculo>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Vehiculo> toModel(Vehiculo vehiculo) {
        EntityModel<Vehiculo> vehiculoModel = createModelWithId(vehiculo.getId(), vehiculo);
        return EntityModel.of(vehiculo,
                linkTo(methodOn(VehiculoController.class).getVehiculoById(vehiculo.getId())).withSelfRel());

    }

}
