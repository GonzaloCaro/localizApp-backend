package com.gestion_transporte.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gestion_transporte.model.DispositivoGPS;

public class UbicacionDTO {

    private UUID id;
    private Double latitud;
    private Double longitud;
    private Double velocidad;
    private LocalDateTime fechaHora;
    private DispositivoGPS gps;

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(Double velocidad) {
        this.velocidad = velocidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public DispositivoGPS getGps() {
        return gps;
    }

    public void setGps(DispositivoGPS gps) {
        this.gps = gps;
    }

}
