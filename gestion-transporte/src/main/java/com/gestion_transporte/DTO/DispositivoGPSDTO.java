package com.gestion_transporte.DTO;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DispositivoGPSDTO {
    private UUID id;

    private String serialNumber;
    private String modelo;
    private UUID vehiculoId;

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public UUID getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(UUID vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
}
