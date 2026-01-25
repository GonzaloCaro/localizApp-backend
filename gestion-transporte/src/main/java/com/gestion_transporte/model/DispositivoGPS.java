package com.gestion_transporte.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion_transporte.model.Vehiculo;
import com.gestion_transporte.model.Ubicacion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dispositivos_gps")
public class DispositivoGPS {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @OneToOne
    @JoinColumn(name = "vehiculo_id", referencedColumnName = "id")
    @JsonBackReference
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "gps", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ubicacion> historialUbicaciones = new ArrayList<>();

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

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Ubicacion> getHistorialUbicaciones() {
        return historialUbicaciones;
    }

    public void setHistorialUbicaciones(List<Ubicacion> historialUbicaciones) {
        this.historialUbicaciones = historialUbicaciones;
    }

}