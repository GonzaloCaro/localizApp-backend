package com.gestion_transporte.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.gestion_transporte.controller.DispositivoGPSController;
import com.gestion_transporte.model.DispositivoGPS;

@Component
public class DispositivoGPSModelAssembler
        implements RepresentationModelAssembler<DispositivoGPS, EntityModel<DispositivoGPS>> {

    @Override
    public EntityModel<DispositivoGPS> toModel(DispositivoGPS dispositivoGPS) {
        return EntityModel.of(dispositivoGPS,
                linkTo(methodOn(DispositivoGPSController.class).getDispositivoGPSById(dispositivoGPS.getId()))
                        .withSelfRel(),
                linkTo(methodOn(DispositivoGPSController.class).getAllDispositivosGPS()).withRel("dispositivosGPS"));
    }

}
