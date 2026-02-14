package com.gestion_transporte.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestion_transporte.model.DispositivoGPS;
import com.gestion_transporte.model.Ubicacion;
import com.gestion_transporte.repository.DispositivoGPSRepository;
import com.gestion_transporte.repository.UbicacionRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

// @Service
// @Slf4j
// public class SimulacionService {

//     private final DispositivoGPSRepository gpsRepository;
//     private final UbicacionRepository ubicacionRepository;
//     private final ObjectMapper objectMapper; // Jackson para leer el JSON

//     // Mapa maestro de rutas cargadas desde el JSON
//     private Map<String, List<List<Double>>> rutasDisponibles = new HashMap<>();

//     // Memoria de la simulación
//     private final Map<UUID, String> gpsRouteAssignment = new HashMap<>();
//     // índice (paso) de la ruta que va cada GPS
//     private final Map<UUID, Integer> gpsProgressIndex = new HashMap<>();

//     public SimulacionService(DispositivoGPSRepository gpsRepository,
//             UbicacionRepository ubicacionRepository,
//             ObjectMapper objectMapper) {
//         this.gpsRepository = gpsRepository;
//         this.ubicacionRepository = ubicacionRepository;
//         this.objectMapper = objectMapper;
//     }

//     // Se ejecuta una sola vez al levantar la aplicación
//     @PostConstruct
//     public void cargarRutasDesdeJson() {
//         log.info("Cargando rutas desde JSON para simulación...");
//         try {
//             // Buscamos el archivo en src/main/resources/static/rutas_simulacion.json
//             ClassPathResource resource = new ClassPathResource("static/rutas_simulacion.json");
//             InputStream inputStream = resource.getInputStream();

//             // Convertimos el JSON a un Mapa de Java
//             rutasDisponibles = objectMapper.readValue(
//                     inputStream,
//                     new TypeReference<Map<String, List<List<Double>>>>() {
//                     });

//             log.info("✅ Simulación: Rutas cargadas exitosamente: " + rutasDisponibles.keySet());
//         } catch (IOException e) {
//             log.error("❌ Error al cargar el archivo de rutas: " + e.getMessage());
//         }
//     }

//     // Se ejecuta cada 5 segundos
//     @Scheduled(fixedRate = 5000)
//     @Transactional
//     public void simularMovimiento() {
//         log.info("🔄 Ejecutando ciclo de simulación de movimiento GPS...");
//         if (rutasDisponibles.isEmpty())
//             return;

//         List<DispositivoGPS> dispositivos = gpsRepository.findAll();
//         List<String> nombresDeRutas = new ArrayList<>(rutasDisponibles.keySet());

//         int i = 0; // Para asignar rutas en orden (Round Robin)

//         for (DispositivoGPS gps : dispositivos) {
//             UUID gpsId = gps.getId();

//             // 1. Asignar ruta si no tiene una asignada
//             if (!gpsRouteAssignment.containsKey(gpsId)) {
//                 String rutaAsignada = nombresDeRutas.get(i % nombresDeRutas.size());
//                 gpsRouteAssignment.put(gpsId, rutaAsignada);
//                 gpsProgressIndex.put(gpsId, 0); // Empieza en el paso 0
//                 i++;
//                 log.info("🚎 Bus " + gps.getSerialNumber() + " asignado a ruta: " + rutaAsignada);
//             }

//             // 2. Obtener datos de su ruta actual
//             String nombreRuta = gpsRouteAssignment.get(gpsId);
//             List<List<Double>> puntosRuta = rutasDisponibles.get(nombreRuta);
//             int indiceActual = gpsProgressIndex.get(gpsId);

//             // 3. Extraer coordenadas [Lon, Lat]
//             List<Double> punto = puntosRuta.get(indiceActual);
//             double longitud = punto.get(0);
//             double latitud = punto.get(1);

//             // 4. Crear y Guardar la Ubicación "Real"
//             Ubicacion nuevaUbicacion = new Ubicacion();
//             nuevaUbicacion.setLatitud(latitud);
//             nuevaUbicacion.setLongitud(longitud);
//             nuevaUbicacion.setFechaHora(LocalDateTime.now());
//             nuevaUbicacion.setGps(gps);

//             // Opcional: Simular velocidad aleatoria entre 20 y 60 km/h
//             nuevaUbicacion.setVelocidad(20.0 + (Math.random() * 40.0));

//             ubicacionRepository.save(nuevaUbicacion);

//             // 5. Avanzar el índice para la próxima vuelta (Loop infinito)
//             int siguienteIndice = (indiceActual + 1) % puntosRuta.size();
//             gpsProgressIndex.put(gpsId, siguienteIndice);
//         }
//     }
// }