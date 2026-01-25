package com.gestion_transporte.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_transporte.model.Ubicacion;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, UUID> {
}
