package com.gestion_transporte.DTO.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gestion_transporte.DTO.VehiculoDTO;
import com.gestion_transporte.DTO.DispositivoGPSDTO;
import com.gestion_transporte.DTO.RutaDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsignacionResponseDTO {
    private UUID id;
    private UUID usuarioId;
    private LocalDateTime fechaAsignacion;
    private String detalle;

    private VehiculoDTO analisis;
    private RutaDTO paciente;
    private DispositivoGPSDTO laboratorio;
}