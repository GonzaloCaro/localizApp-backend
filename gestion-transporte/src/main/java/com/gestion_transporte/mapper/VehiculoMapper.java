package com.gestion_transporte.mapper;

import org.springframework.stereotype.Component;

import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.model.Vehiculo;

@Component
public class VehiculoMapper {
    public Vehiculo toEntity(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(vehiculoDTO.getId());
        vehiculo.setPatente(vehiculoDTO.getPatente());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setEstado(vehiculoDTO.getEstado());
        vehiculo.setTipo(vehiculoDTO.getTipo());
        vehiculo.setKilometraje(vehiculoDTO.getKilometraje());
        vehiculo.setAnio(vehiculoDTO.getAnio());
        return vehiculo;
    }
}
