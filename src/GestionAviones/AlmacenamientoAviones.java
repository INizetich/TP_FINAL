package GestionAviones;

import java.util.*;
import java.util.Scanner;
public class AlmacenamientoAviones {
    Scanner scanner = new Scanner(System.in);
    private List<Hangar<Avion>> listaHangares;

    public AlmacenamientoAviones() {
        this.listaHangares = new ArrayList();
    }

    // Generar hangares automáticamente
    public void generarHangares(int cantidadHangares) {
        for (int i = 1; i <= cantidadHangares; i++) {
            Hangar<Avion> hangar = new Hangar<>(i);
            this.listaHangares.add(hangar);
        }
        System.out.println(cantidadHangares + " hangares generados automáticamente.");
    }

    // Generar aviones automáticamente y asignarlos a hangares
    public void generarAviones(int cantidadAviones) {
        if (listaHangares.isEmpty()) {
            System.out.println("No hay hangares creados. Primero genera los hangares.");
            return;
        }

        Random random = new Random();
        for (int i = 1; i <= cantidadAviones; i++) {
            // Crear un avión con datos aleatorios
            String nombre = "Boeing-" + (745 + i);
            int capacidad = random.nextInt(300) + 50; // Capacidad entre 50 y 350
            String motor = random.nextBoolean() ? "Motor a reacción" : "Motor turbofan";
            String modelo = "Modelo-" + "A" + random.nextInt(100);
            String codigoAvion = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Avion avion = new Avion(nombre, capacidad, motor, modelo, codigoAvion);

            // Asignar el avión a un hangar aleatorio
            Hangar<Avion> hangar = listaHangares.get(random.nextInt(listaHangares.size()));
            hangar.agrearAvion(avion);
        }
        System.out.println(cantidadAviones + " aviones generados automáticamente y asignados a hangares.");
    }

    public List<Hangar<Avion>> getListaHangares() {
        return listaHangares;
    }

    public void setListaHangares(List<Hangar<Avion>> listaHangares) {
        AlmacenamientoAviones.this.listaHangares = listaHangares;
    }

    // Mostrar la lista de hangares y los aviones en cada hangar
    public void mostrarHangares() {
        if (listaHangares.isEmpty()) {
            System.out.println("No hay hangares disponibles.");
            return;
        }

        for (Hangar<Avion> hangar : listaHangares) {
            System.out.println("Hangar Número: " + hangar.getNumeroHangar());
            System.out.println("Aviones en el hangar:");
            for (Avion avion : hangar.ObtenerListaAviones()) {
                System.out.println(" - " + avion);
            }
        }


    }

    public void eliminarAvionPorID(String codigoAvion) {
        if (listaHangares.isEmpty()) {
            System.out.println("La lista de hangares está vacía.");
            return;
        }

        boolean avionEncontrado = false;
        Scanner scanner = new Scanner(System.in);

        for (Hangar<Avion> hangar : listaHangares) {
            // Buscar el avión en el hangar actual
            Optional<Avion> avionEncontradoEnHangar = hangar.ObtenerListaAviones()
                    .stream()
                    .filter(avion -> avion.getCodigoAvion().equals(codigoAvion))
                    .findFirst();

            if (avionEncontradoEnHangar.isPresent()) {
                avionEncontrado = true;
                System.out.println("Avión encontrado en el hangar: " + hangar.getNumeroHangar());
                System.out.println("Desea eliminarlo? (s: Sí / n: No)");
                String eleccion = scanner.nextLine().trim().toLowerCase();

                if (eleccion.equalsIgnoreCase("s")) {
                    hangar.ObtenerListaAviones().remove(avionEncontradoEnHangar.get());
                    System.out.println("Avión eliminado correctamente.");
                } else {
                    System.out.println("El avión no fue eliminado.");
                }
                break; // Salir del bucle porque el avión ya fue encontrado
            }
        }

        if (!avionEncontrado) {
            System.out.println("No se encontró un avión con el código: " + codigoAvion);
        }
    }
}

