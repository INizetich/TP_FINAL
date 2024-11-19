package Gestiones;


import Aviones.Avion;
import Aviones.Vuelo;
import Enums.EstadoEmbarque;
import Enums.PuertaEmbarque;
import Personas.Pasajero;

import java.time.LocalDateTime;
import java.util.*;
public class SistemaVuelo{
    // Lista para almacenar los vuelos
    private static List<Avion> aviones = new ArrayList<>(); // Lista para almacenar los aviones
    private static List<Vuelo> vuelosGenerados = new ArrayList<>();



    public static List<Avion> getAviones() {
        return aviones;
    }

    public static void setAviones(List<Avion> aviones) {
        SistemaVuelo.aviones = aviones;
    }

   /* public static void generarVuelosDesdeHangares(int cantidadVuelos, AlmacenamientoAviones gestionHangares) {
        // Asegurarse de que solo se generen los vuelos una vez
        if (!vuelosGenerados.isEmpty()) {
            System.out.println("Ya existen vuelos generados, se utilizarán los mismos.");
            return; // Si ya se generaron vuelos, no se generan de nuevo
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

        // Generar vuelos solo si no existen previamente
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

            // Registrar el vuelo en la lista estática
            vuelosGenerados.add(vuelo); // Los vuelos se añaden una vez y no se modifican después
        }
    }*/

    public static void mostrarVuelos() {
        System.out.println("✈️  Lista de Vuelos Disponibles  ✈️");
        System.out.println("-------------------------------------------------");
        SistemaVuelo.getVuelosGenerados().forEach(v -> System.out.println(
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
    public static void obtenerVuelosGenerados(AlmacenamientoAviones gestionHangar) {
        vuelosGenerados = crearVuelosConIDUnico(gestionHangar);
    }

    public static List<Vuelo> getVuelosGenerados() {
        return vuelosGenerados;
    }

    private static List<Vuelo> crearVuelosConIDUnico(AlmacenamientoAviones gestionHangares) {

        List<Vuelo> vuelos = new ArrayList<>();

        // Obtener la lista de aviones disponibles
        List<Avion> avionesDisponibles = gestionHangares.obtenerAvionesDeTodosLosHangares();

        if (avionesDisponibles.isEmpty()) {
            System.out.println("No hay aviones disponibles para asignar a los vuelos.");
            return vuelos; // Regresar una lista vacía si no hay aviones
        }

        Random random = new Random();

        // Crear los vuelos con un avión aleatorio asignado y una puerta de embarque aleatoria
        vuelos.add(crearVuelo("C6103495", "Aeropuerto Internacional Ezeiza", "Miami International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("B26A01FF", "Frankfurter Flughafen", "Aeropuerto Internacional El Dorado", avionesDisponibles, random));
        vuelos.add(crearVuelo("B3182DFD", "北京首都国际机场", "Aeropuerto Internacional de la Ciudad de México", avionesDisponibles, random));
        vuelos.add(crearVuelo("959BB1F9", "John F. Kennedy International Airport", "Los Angeles International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("8B6D37C3", "Dubai International Airport", "Sydney Kingsford Smith International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("2224890D", "Aeropuerto Internacional São Paulo-Guarulhos", "Miami International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("E2230368", "Hong Kong International Airport", "Aeropuerto Internacional Ezeiza", avionesDisponibles, random));
        vuelos.add(crearVuelo("DEC08F87", "Frankfurter Flughafen", "Miami International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("946B006E", "John F. Kennedy International Airport", "北京首都国际机场", avionesDisponibles, random));
        vuelos.add(crearVuelo("CF082E1B", "Sydney Kingsford Smith International Airport", "Aeropuerto Internacional São Paulo-Guarulhos", avionesDisponibles, random));
        vuelos.add(crearVuelo("28B441FC", "Aeropuerto Internacional de la Ciudad de México", "Aeropuerto Internacional El Dorado", avionesDisponibles, random));
        vuelos.add(crearVuelo("8F763DFB", "Aeropuerto Internacional São Paulo-Guarulhos", "Sydney Kingsford Smith International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("EC68E773", "北京首都国际机场", "Miami International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("E08C0EC8", "Dubai International Airport", "Los Angeles International Airport", avionesDisponibles, random));
        vuelos.add(crearVuelo("F30BDB13", "Miami International Airport", "Aeropuerto Internacional Ezeiza", avionesDisponibles, random));
        vuelos.add(crearVuelo("ZP140A49", "John F. Kennedy International Airport", "Miami International Airport", avionesDisponibles, random));

        return vuelos;
    }


    private static Avion obtenerAvionAleatorio(List<Avion> avionesDisponibles, Random random) {
        return avionesDisponibles.get(random.nextInt(avionesDisponibles.size()));
    }

    private static Vuelo crearVuelo(String idVuelo, String aeropuertoOrigen, String aeropuertoDestino, List<Avion> avionesDisponibles, Random random) {
        // Obtener un avión aleatorio de la lista de aviones disponibles
        Avion avion = obtenerAvionAleatorio(avionesDisponibles, random);

        // Generar una puerta de embarque aleatoria
        String puertaEmbarque = String.valueOf(PuertaEmbarque.obtenerPuertaAleatoria());

        // Inicializar lista de pasajeros y asientos vacíos
        Set<Pasajero> listaPasajeros = new HashSet<>();
        List<String> asientos = new ArrayList<>();

        // Crear el vuelo usando el constructor completo
        return new Vuelo(idVuelo, aeropuertoOrigen, aeropuertoDestino,
                LocalDateTime.now().plusMinutes(random.nextInt(60)).toString(),
                EstadoEmbarque.ABIERTO.name(),
                avion,
                puertaEmbarque,
                listaPasajeros,
                asientos);
    }


}