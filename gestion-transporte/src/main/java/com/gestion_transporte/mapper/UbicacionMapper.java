package com.gestion_transporte.mapper;

import org.springframework.stereotype.Component;

import com.gestion_transporte.DTO.UbicacionDTO;
import com.gestion_transporte.model.Ubicacion;

@Component
public class UbicacionMapper {
    public Ubicacion toEntity(UbicacionDTO ubicacionDTO) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setId(ubicacionDTO.getId());
        ubicacion.setLatitud(ubicacionDTO.getLatitud());
        ubicacion.setLongitud(ubicacionDTO.getLongitud());
        ubicacion.setVelocidad(ubicacionDTO.getVelocidad());
        ubicacion.setFechaHora(ubicacionDTO.getFechaHora());
        return ubicacion;
    }
}
