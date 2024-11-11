package GestionAviones;

import GestionAeropuerto.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SistemaVuelo {
    private static List<Vuelo> vuelos = new ArrayList<>();  // Lista para almacenar los vuelos
    private static List<Avion> aviones = new ArrayList<>(); //lista para almacenar los aviones


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

    public static void registrarAvion(Avion avion) {
        if (!vuelos.contains(avion)) {
            aviones.add(avion);
            System.out.println("Avión registrado exitosamente.");
        } else {
            System.out.println("El avión ya está registrado.");
        }
    }



    public static void generarVuelosAutomaticos(int cantidad) {
        if (aviones.isEmpty()) {
            System.out.println("No hay aviones registrados para asignar a los vuelos.");
            return;
        }

        Set<Aeropuerto> aeropuertos = SistemaAeropuerto.agregarAeropuertos(); // Obtener lista de aeropuertos
        if (aeropuertos.size() < 2) {
            System.out.println("Se necesitan al menos dos aeropuertos para generar vuelos.");
            return;
        }

        List<Aeropuerto> listaAeropuertos = new ArrayList<>(aeropuertos);
        Random random = new Random();

        for (int i = 0; i < cantidad; i++) {
            // Selección aleatoria de aeropuertos de origen y destino
            Aeropuerto origen = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            Aeropuerto destino;

            do {
                destino = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            } while (origen.equals(destino)); // Asegurarse de que origen y destino no sean iguales

            // Selección aleatoria de un avión
            Avion avion = aviones.get(random.nextInt(aviones.size()));

            // Creación del vuelo
            Vuelo vuelo = new Vuelo();
            vuelo.setOrigen(origen.getNombre());
            vuelo.setDestino(destino.getNombre());
            vuelo.setHorario(new Date()); // Usar la fecha y hora actuales
            vuelo.setAvion(avion);
            vuelo.setEstadoEmbarque(EstadoEmbarque.ABIERTO);

            // Agregar el vuelo a la lista
            vuelos.add(vuelo);
        }

        System.out.println(cantidad + " vuelos generados automáticamente.");
    }





    public static void mostrarVuelos() {
        if (vuelos.isEmpty()) {
            System.out.println("No hay vuelos registrados.");
        } else {
            System.out.println("LISTA DE VUELOS:");
            vuelos.forEach(System.out::println);
        }
    }


    // Método para eliminar un vuelo por ID
    public static void eliminarVuelo(String idVuelo) {
        vuelos.removeIf(vuelo -> vuelo.getIdVuelo().equals(idVuelo));
        System.out.println("Vuelo con ID " + idVuelo + " eliminado.");
    }


    public void mostrarListaAviones() {
        if (aviones.isEmpty()) {
            System.out.println("No hay aviones registrados.");
        } else {
           aviones.forEach(System.out::println);
        }
    }


    }



