package com.consumidor_archivos.listener;

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

@Component
public class ArchivoListener {

    @Value("${app.files.path}")
    private String rutaBase;

    private final ObjectMapper objectMapper;

    public ArchivoListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.horarios}")
    public void recibirMensaje(String mensajeJson) {
        System.out.println("Recibido actualización de horario: " + mensajeJson);
        generarArchivoJson(mensajeJson, "horarios");
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