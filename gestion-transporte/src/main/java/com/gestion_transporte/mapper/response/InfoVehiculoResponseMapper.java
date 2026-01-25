// package com.gestion_transporte.mapper.response;

// import org.springframework.stereotype.Component;

// import com.gestion_transporte.DTO.VehiculoDTO;
// import com.gestion_transporte.DTO.DispositivoGPSDTO;
// import com.gestion_transporte.DTO.RutaDTO;
// import com.gestion_transporte.DTO.response.AsignacionResponseDTO;
// import com.gestion_transporte.model.Analisis;
// import com.gestion_transporte.model.Asignacion;
// import com.gestion_transporte.model.Laboratorio;
// import com.gestion_transporte.model.Paciente;

// @Component
// public class AsignacionResponseMapper {

// public AsignacionResponseDTO toDto(Asignacion asignacion) {
// if (asignacion == null)
// return null;

// return AsignacionResponseDTO.builder()
// .id(asignacion.getId())
// .usuarioId(asignacion.getUsuarioId())
// .fechaAsignacion(asignacion.getFechaAsignacion())
// .detalle(asignacion.getDetalle())
// .analisis(toAnalisisDto(asignacion.getAnalisis()))
// .paciente(toPacienteDto(asignacion.getPaciente()))
// .laboratorio(toLaboratorioDto(asignacion.getLaboratorio()))
// .build();
// }

// private VehiculoDTO toAnalisisDto(Analisis analisis) {
// if (analisis == null)
// return null;
// return VehiculoDTO.builder()
// .id(analisis.getId())
// .codigo(analisis.getCodigo())
// .nombre(analisis.getNombre())
// .build();
// }

// private RutaDTO toPacienteDto(Paciente paciente) {
// if (paciente == null)
// return null;
// return RutaDTO.builder()
// .id(paciente.getId())
// .rut(paciente.getRut())
// .dv(paciente.getDv())
// .nombrePaciente(paciente.getNombrePaciente())
// .apellidoPaciente(paciente.getApellidoPaciente())
// .edad(paciente.getEdad())
// .telefono(paciente.getTelefono())
// .build();
// }

// private DispositivoGPSDTO toLaboratorioDto(Laboratorio lab) {
// if (lab == null)
// return null;
// return DispositivoGPSDTO.builder()
// .id(lab.getId())
// .nombre(lab.getNombre())
// .ubicacion(lab.getUbicacion())
// .build();
// }
// }
