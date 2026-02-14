package com.productor_horarios.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping("/horarios")
public class HorariosController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${app.rabbitmq.queue.horarios}")
    private String nombreCola;

    public HorariosController(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/actualizar")
    public String actualizarHorario(@RequestBody Map<String, Object> horarioData) {
        try {
            String mensajeJson = objectMapper.writeValueAsString(horarioData);
            rabbitTemplate.convertAndSend(nombreCola, mensajeJson);
            return "Actualización de horario enviada a RabbitMQ: " + mensajeJson;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}