package com.gestion_transporte.mapper;

import org.springframework.stereotype.Component;

import com.gestion_transporte.DTO.RutaDTO;
import com.gestion_transporte.model.Ruta;

@Component
public class RutaMapper {
    public Ruta toEntity(RutaDTO rutaDTO) {
        Ruta ruta = new Ruta();
        ruta.setId(rutaDTO.getId());
        ruta.setVehiculo(rutaDTO.getVehiculo());
        ruta.setNombre(rutaDTO.getNombre());
        ruta.setOrigen(rutaDTO.getOrigen());
        ruta.setDestino(rutaDTO.getDestino());
        return ruta;
    }
}
