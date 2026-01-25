package com.gestion_transporte.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion_transporte.model.Ruta;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, UUID> {

}
