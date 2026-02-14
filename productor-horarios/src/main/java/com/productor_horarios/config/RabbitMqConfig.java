package com.productor_horarios.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.queue.horarios}")
    private String nombreCola;

    @Bean
    public Queue colaHorarios() {
        return new Queue(nombreCola, true);
    }
}