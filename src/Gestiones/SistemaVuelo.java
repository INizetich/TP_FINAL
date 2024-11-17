package Gestiones;

import Aeropuerto.Aeropuerto;
import Aviones.Avion;
import Aviones.Hangar;
import Aviones.Vuelo;
import Enums.EstadoEmbarque;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
public class SistemaVuelo{
    private static List<Vuelo> vuelos = new ArrayList<>(); // Lista para almacenar los vuelos
    private static List<Avion> aviones = new ArrayList<>(); // Lista para almacenar los aviones
    private static List<Vuelo> vuelosGenerados = new ArrayList<>();

    public static List<Vuelo> getVuelos() {
        return vuelos;
    }

    public static void setVuelos(List<Vuelo> vuelos) {
        SistemaVuelo.vuelos = vuelos;
    }

    public static List<Avion> getAviones() {
        return aviones;
    }

    public static void setAviones(List<Avion> aviones) {
        SistemaVuelo.aviones = aviones;
    }

    public static void generarVuelosDesdeHangares(int cantidadVuelos, AlmacenamientoAviones gestionHangares) {
        if (!vuelosGenerados.isEmpty()) {
            System.out.println("Ya existen vuelos generados, se utilizarán los mismos.");
            return;
        }

        Set<Aeropuerto> aeropuertos = SistemaAeropuerto.agregarAeropuertos(); // Obtener lista de aeropuertos
        if (aeropuertos.size() < 2) {
            System.out.println("Se necesitan al menos dos aeropuertos para generar vuelos.");
            return;
        }

        // Obtener la lista de aviones desde los hangares
        List<Avion> avionesDisponibles = new ArrayList<>();
        for (Hangar<Avion> hangar : gestionHangares.getListaHangares()) {
            avionesDisponibles.addAll(hangar.ObtenerListaAviones());
        }

        if (avionesDisponibles.isEmpty()) {
            System.out.println("No hay aviones disponibles en los hangares para asignar a vuelos.");
            return;
        }

        List<Aeropuerto> listaAeropuertos = new ArrayList<>(aeropuertos);
        Random random = new Random();

        // Obtener los ID únicos predefinidos
        List<Vuelo> vuelosConIDUnico = crearVuelosConIDUnico(); // Tu método de vuelos con ID.

        for (int i = 0; i < cantidadVuelos && i < vuelosConIDUnico.size(); i++) {
            if (avionesDisponibles.isEmpty()) {
                System.out.println("No hay más aviones disponibles en los hangares para generar vuelos.");
                break;
            }

            // Seleccionar un avión disponible y removerlo de la lista
            Avion avion = avionesDisponibles.remove(0);

            // Seleccionar un vuelo predefinido por su ID
            Vuelo vuelo = vuelosConIDUnico.get(i);

            // Generar origen y destino aleatorios
            Aeropuerto origen = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            Aeropuerto destino;
            do {
                destino = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            } while (origen.equals(destino)); // Asegurarse de que origen y destino no sean iguales

            // Asignar valores al vuelo
            vuelo.setOrigen(origen.getNombre());
            vuelo.setDestino(destino.getNombre());
            vuelo.setAvion(avion);

            // Generar una hora aleatoria para el vuelo
            LocalTime horaInicio = LocalTime.of(6, 0); // Hora inicial: 6:00 AM
            LocalTime horaFin = LocalTime.of(23, 59);  // Hora final: 11:59 PM
            long segundosAleatorios = ThreadLocalRandom.current().nextLong(horaInicio.toSecondOfDay(), horaFin.toSecondOfDay());
            LocalTime horaAleatoria = LocalTime.ofSecondOfDay(segundosAleatorios);

            vuelo.setHorario(Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), horaAleatoria)).toLocalDateTime());

            vuelo.setEstadoEmbarque(EstadoEmbarque.ABIERTO);

            vuelosGenerados.add(vuelo); // Registrar el vuelo en la lista estática
        }
    }

    public static void mostrarVuelos() {
        System.out.println("✈️  Lista de Vuelos Disponibles  ✈️");
        System.out.println("-------------------------------------------------");
        SistemaVuelo.obtenerVuelosGenerados().forEach(v -> System.out.println(
                "🆔 ID de Vuelo: " + v.getIdVuelo() +
                        " | 🌍 Origen: " + v.getOrigen() +
                        " | ✈️ Destino: " + v.getDestino() +
                        " | 🛩️ Avión: " + v.getAvion().getNombre() +
                        " | 🛃 Estado de Embarque: " + v.getEstadoEmbarque()
        ));
        System.out.println("-------------------------------------------------");
    }


    public static void setVuelosGenerados(List<Vuelo> vuelosGenerados) {
        SistemaVuelo.vuelosGenerados = vuelosGenerados;
    }

    // Método para obtener los vuelos generados
    public static List<Vuelo> obtenerVuelosGenerados() {
        return vuelosGenerados;
    }


    private static List<Vuelo> crearVuelosConIDUnico(){
        List<Vuelo> vuelos = new ArrayList<>();

        vuelos.add(new Vuelo("CB901D39"));
        vuelos.add(new Vuelo("B26A01FF"));
        vuelos.add(new Vuelo("B3182DFD"));
        vuelos.add(new Vuelo("959BB1F9"));
        vuelos.add(new Vuelo("8B6D37C3"));
        vuelos.add(new Vuelo("2224890D"));
        vuelos.add(new Vuelo("E2230368"));
        vuelos.add(new Vuelo("DEC08F87"));
        vuelos.add(new Vuelo("946B006E"));
        vuelos.add(new Vuelo("CF082E1B"));
        vuelos.add(new Vuelo("28B441FC"));
        vuelos.add(new Vuelo("8F763DFB"));
        vuelos.add(new Vuelo("EC68E773"));
        vuelos.add(new Vuelo("E08C0EC8"));
        vuelos.add(new Vuelo("F30BDB13"));


        return vuelos;
    }


}

