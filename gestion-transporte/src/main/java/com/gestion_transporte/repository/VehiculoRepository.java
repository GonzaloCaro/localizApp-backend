package com.gestion_transporte.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_transporte.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, UUID> {

}
