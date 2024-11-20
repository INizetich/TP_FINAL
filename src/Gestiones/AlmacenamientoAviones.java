package Gestiones;

import Enums.TipoEmpleado;
import Excepciones.CodigoAvionNoExistenteException;
import Excepciones.HangarNoExistenteException;
import Aviones.Avion;
import Aviones.Hangar;
import Excepciones.NoEsPilotoException;
import Personas.Empleado;

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
            printCentered("No hay hangares disponibles.");
            return;
        }

        for (Hangar<Avion> hangar : listaHangares) {
            printCentered("Hangar Número: " + hangar.getNumeroHangar());
            printCentered("Aviones en el hangar:");
            for (Avion avion : hangar.ObtenerListaAviones()) {
                printCentered(" - " + avion);
            }
        }
        limpiarPantalla();
    }

    public void eliminarAvionPorID(String codigoAvion) throws CodigoAvionNoExistenteException {
        if (listaHangares.isEmpty()) {
            printCentered("La lista de hangares está vacía.");
            return;
        }

        boolean avionEncontrado = false;

        for (Hangar<Avion> hangar : listaHangares) {
            // Buscar el avión en el hangar actual
            Optional<Avion> avionEncontradoEnHangar = hangar.ObtenerListaAviones()
                    .stream()
                    .filter(avion -> avion.getCodigoAvion().equals(codigoAvion))
                    .findFirst();

            if (avionEncontradoEnHangar.isPresent()) {
                avionEncontrado = true;
                printCentered("Avión encontrado en el hangar: " + hangar.getNumeroHangar());
                printCentered("Desea retirar el avión para que realice un vuelo? (s: Sí / n: No)");
                String eleccion = scanner.nextLine().trim().toLowerCase();

                if (eleccion.equalsIgnoreCase("s")) {
                    hangar.ObtenerListaAviones().remove(avionEncontradoEnHangar.get());
                    printCentered("Avión retirado para un vuelo correctamente.");
                } else {
                    printCentered("El avión no fue retirado del hangar.");
                }
                break; // Salir del bucle porque el avión ya fue encontrado
            }
        }

        if (!avionEncontrado) {
            throw new CodigoAvionNoExistenteException("El código de avión no existe.");
        }
        limpiarPantalla();
    }

    public void agregarAvionAlHangar(int numeroHangar, Avion avion) throws HangarNoExistenteException {
        // Buscar el hangar por su número
        Optional<Hangar<Avion>> hangarOpt = listaHangares.stream()
                .filter(h -> h.getNumeroHangar() == numeroHangar)
                .findFirst();

        if (hangarOpt.isEmpty()) {
            // Lanzar excepción si el hangar no existe
            throw new HangarNoExistenteException("El hangar número " + numeroHangar + " no existe.");
        }

        Hangar<Avion> hangar = hangarOpt.get();

        // Verificar si el hangar tiene capacidad para más aviones
        if (hangar.estaLleno()) {
            printCentered("El hangar número " + numeroHangar + " está lleno. No se puede agregar el avión.");
            return;
        }

        // Agregar el avión al hangar
        hangar.agregarAvion(avion);
        printCentered("El " + avion.toString() + " ha sido agregado al hangar número " + numeroHangar + " exitosamente.");
        limpiarPantalla();
    }

    public List<Avion> obtenerAvionesDeTodosLosHangares() {
        List<Avion> listaAviones = new ArrayList<>();

        // Recorrer todos los hangares y agregar los aviones a la lista
        for (Hangar<Avion> hangar : listaHangares) {
            listaAviones.addAll(hangar.ObtenerListaAviones()); // Agrega todos los aviones del hangar
        }

        return listaAviones;
    }

    public void generarAviones(int cantidadAviones, Set<Empleado> listaEmpleados) {
        if (listaHangares.isEmpty()) {
            printCentered("No hay hangares creados. Primero genera los hangares.");
            return;
        }

        // Filtrar la lista de empleados para obtener solo los pilotos
        List<Empleado> pilotosDisponibles = new ArrayList<>();
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getTipoEmpleado() == TipoEmpleado.PILOTO) {
                pilotosDisponibles.add(empleado);
            }
        }

        if (pilotosDisponibles.isEmpty()) {
            printCentered("No hay pilotos disponibles para asignar.");
            return;
        }

        Random random = new Random();
        for (int i = 1; i <= cantidadAviones; i++) {
            // Crear un avión con datos aleatorios
            String nombre = "Boeing-" + (745 + i);
            int capacidad = random.nextInt(300) + 50; // Capacidad entre 50 y 350
            String motor = random.nextBoolean() ? "Motor a reacción" : "Motor turbofan";
            String modelo = "Modelo-" + "A" + random.nextInt(100);
            String codigoAvion = "A70C1A" + i;

            Avion avion = new Avion(nombre, capacidad, motor, modelo, codigoAvion);

            // Asignar un piloto aleatorio
            Empleado pilotoAsignado = pilotosDisponibles.get(random.nextInt(pilotosDisponibles.size()));
            try {
                avion.asignarPiloto(pilotoAsignado); // Asignar piloto al avión
            } catch (NoEsPilotoException e) {
                printCentered("Error al asignar piloto al avión " + avion.getCodigoAvion() + ": " + e.getMessage());
            }

            // Asignar el avión a un hangar aleatorio
            Hangar<Avion> hangar = listaHangares.get(random.nextInt(listaHangares.size()));
            hangar.agregarAvion(avion);
        }
        limpiarPantalla();
    }


    public static void printCentered(String text) {
        int terminalWidth = 150; // Puedes ajustar este valor según el ancho de tu terminal
        int padding = (terminalWidth - text.length()) / 2;
        String paddedText = " ".repeat(padding) + text;
        System.out.println(paddedText);
    }

    public static void limpiarPantalla() {
        // Imprime 50 líneas vacías para simular la limpieza de pantalla
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }}


}