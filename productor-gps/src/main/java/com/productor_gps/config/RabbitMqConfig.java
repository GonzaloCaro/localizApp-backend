package com.productor_gps.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.queue.ubicaciones}")
    private String nombreCola;

    @Bean
    public Queue colaUbicaciones() {
        return new Queue(nombreCola, true); // true = durable
    }
}