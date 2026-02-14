package com.gestion_transporte.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_transporte.model.DispositivoGPS;
import com.gestion_transporte.model.Ubicacion;
import com.gestion_transporte.repository.UbicacionRepository;
import com.gestion_transporte.service.DispositivoGPSService;
import com.gestion_transporte.service.UbicacionService;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class UbicacionListener {

    @Value("${app.files.path}")
    private String rutaBase;

    private final UbicacionService ubicacionService;
    private final DispositivoGPSService dispositivoGPSService;
    private final UbicacionRepository ubicacionRepository;
    private final ObjectMapper objectMapper;

    public UbicacionListener(UbicacionService ubicacionService, DispositivoGPSService dispositivoGPSService,
            UbicacionRepository ubicacionRepository,
            ObjectMapper objectMapper) {
        this.ubicacionService = ubicacionService;
        this.dispositivoGPSService = dispositivoGPSService;
        this.ubicacionRepository = ubicacionRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.ubicaciones}")
    public void recibirUbicacion(String mensajeJson) {
        log.info("RabbitMQ - Nueva ubicación recibida: {}", mensajeJson);
        try {
            log.info("RabbitMQ - Procesando ubicación...");
            // Convertir el JSON recibido a objeto Ubicacion
            Map<String, Object> ubicacionData = objectMapper.readValue(mensajeJson, Map.class);
            Ubicacion ubicacion = new Ubicacion();
            ubicacion.setLatitud(Double.valueOf(ubicacionData.get("latitud").toString()));
            ubicacion.setLongitud(Double.valueOf(ubicacionData.get("longitud").toString()));
            ubicacion.setVelocidad(Double.valueOf(ubicacionData.get("velocidad").toString()));
            ubicacion.setFechaHora(LocalDateTime.parse(ubicacionData.get("fechaHora").toString()));
            DispositivoGPS gps = dispositivoGPSService
                    .getDispositivoGPSById(UUID.fromString(ubicacionData.get("gps_id").toString()));
            ubicacion.setGps(gps);

            log.info("RabbitMQ - GPS asociado: {}", gps);
            log.info("RabbitMQ - Guardando ubicación en Oracle..." + ubicacion);
            ubicacionRepository.save(ubicacion);
            log.info("RabbitMQ - Ubicación guardada en Oracle correctamente.");

            System.out.println("Recibido actualización de ubicación: " + mensajeJson);
            generarArchivoJson(mensajeJson, "ubicaciones");
        } catch (Exception e) {
            log.error("RabbitMQ - Error al procesar ubicación: {}", e.getMessage());
        }
    }

    private void generarArchivoJson(String contenido, String tipo) {
        try {
            // Asegurar que el directorio existe
            File directorio = new File(rutaBase + tipo);
            if (!directorio.exists()) {
                directorio.mkdirs();
            }

            // Crear nombre único: horario_TIMESTAMP_UUID.json
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String nombreArchivo = tipo + "_" + timestamp + "_" + UUID.randomUUID().toString().substring(0, 5)
                    + ".json";

            // Escribir archivo
            File archivo = new File(directorio, nombreArchivo);
            objectMapper.writeValue(archivo, objectMapper.readTree(contenido)); // Escribir como JSON bonito

            System.out.println("Archivo generado: " + archivo.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error al escribir archivo: " + e.getMessage());
        }
    }
}