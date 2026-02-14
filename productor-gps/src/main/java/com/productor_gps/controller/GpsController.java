package com.productor_gps.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping("/gps")
public class GpsController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.rabbitmq.queue.ubicaciones}")
    private String nombreCola;

    public GpsController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    // Endpoint que recibe la ubicación del Bus y la manda a la cola
    @PostMapping("/enviar")
    public String enviarUbicacion(@RequestBody Map<String, Object> ubicacionData) {
        try {
            // Convertimos el Map a JSON String para enviarlo por la cola
            String mensajeJson = objectMapper.writeValueAsString(ubicacionData);

            rabbitTemplate.convertAndSend(nombreCola, mensajeJson);
            System.out.println("Enviado a RabbitMQ: " + mensajeJson);
            return "Ubicación enviada a RabbitMQ: " + mensajeJson;
        } catch (Exception e) {
            return "Error al enviar mensaje: " + e.getMessage();
        }
    }
}