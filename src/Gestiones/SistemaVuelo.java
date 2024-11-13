package Gestiones;

import Enums.EstadoEmbarque;
import Aeropuerto.*;
import Aviones.Avion;
import Aviones.Hangar;
import Aviones.Vuelo;

import java.util.*;

public class SistemaVuelo {
    private static List<Vuelo> vuelos = new ArrayList<>(); // Lista para almacenar los vuelos
    private static List<Avion> aviones = new ArrayList<>(); // Lista para almacenar los aviones

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

    // Método para registrar un avión manualmente
    public static void registrarAvion(Avion avion) {
        if (!aviones.contains(avion)) {
            aviones.add(avion);
            System.out.println("Avión registrado exitosamente!.");
        } else {
            System.out.println("El avión ya está registrado.");
        }
    }


    // Método para generar vuelos utilizando aviones almacenados en hangares
    public static void generarVuelosDesdeHangares(int cantidadVuelos, AlmacenamientoAviones gestionHangares) {
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

        for (int i = 0; i < cantidadVuelos; i++) {
            if (avionesDisponibles.isEmpty()) {
                System.out.println("No hay más aviones disponibles en los hangares para generar vuelos.");
                break;
            }

            // Seleccionar un avión disponible y removerlo de la lista
            Avion avion = avionesDisponibles.remove(0);

            // Generar un vuelo para el avión seleccionado
            Aeropuerto origen = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            Aeropuerto destino;
            do {
                destino = listaAeropuertos.get(random.nextInt(listaAeropuertos.size()));
            } while (origen.equals(destino)); // Asegurarse de que origen y destino no sean iguales

            Vuelo vuelo = new Vuelo();
            vuelo.setOrigen(origen.getNombre());
            vuelo.setDestino(destino.getNombre());
            vuelo.setHorario(new Date()); // Usar la fecha y hora actuales
            vuelo.setAvion(avion); // Asignar el avión al vuelo
            vuelo.setEstadoEmbarque(EstadoEmbarque.ABIERTO);

            vuelos.add(vuelo); // Registrar el vuelo en la lista de vuelos
        }


    }




    // Método para mostrar la lista de vuelos
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

    // Método para mostrar la lista de aviones
    public static void mostrarListaAviones() {
        if (aviones.isEmpty()) {
            System.out.println("No hay aviones registrados.");
        } else {
            System.out.println("LISTA DE AVIONES:");
            aviones.forEach(System.out::println);
        }
    }
}
