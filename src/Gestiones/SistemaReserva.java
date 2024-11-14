package Gestiones;


import Excepciones.CapacidadMaximaException;
import Excepciones.DniRegistradoException;
import Enums.EstadoEmbarque;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import Personas.Pasajero;

import java.util.*;
import java.util.Scanner;

public class SistemaReserva {
    private final Map<String, CheckIn> mapaReservas; // UUID como clave, CheckIn como valor


    public SistemaReserva() {

        this.mapaReservas = new HashMap<>();
    }


    public void relizarReserva()  {
        Scanner scanner = new Scanner(System.in);

       SistemaVuelo.mostrarVuelos();

        // Selección del vuelo
        System.out.print("Ingrese el ID del vuelo que desea abordar: ");
        String idVueloSeleccionado = scanner.nextLine().toUpperCase();

        // Filtro la lista de vuelos por el id de vuelo
        Vuelo vueloSeleccionado = SistemaVuelo.getVuelos().stream()
                .filter(v -> v.getIdVuelo().equalsIgnoreCase(idVueloSeleccionado))
                .findFirst()
                .orElse(null);

        if (vueloSeleccionado == null) {
            System.out.println("******************************************************************");
            System.out.println("El ID del vuelo ingresado no se encuentra en la lista de vuelos disponibles.");
            return;
        }

        // Mostrar asientos disponibles
        List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
        if (asientosDisponibles.isEmpty()) {
            System.out.println("No hay asientos disponibles. :(");
            return;
        }
        System.out.println("******************************************************************************************************************************************************************************");
        System.out.println("Asientos disponibles: " + asientosDisponibles);

        // Seleccionar asiento
        System.out.print("Seleccione un asiento disponible: ");
        String asientoSeleccionado = scanner.nextLine().toUpperCase();

        if (!asientosDisponibles.contains(asientoSeleccionado)) {
            asientosDisponibles.remove(asientoSeleccionado);
            System.out.println("El asiento seleccionado no está disponible.");
            return;
        }



        Pasajero pasajero = crearPasajero(asientoSeleccionado);

        try {
            if (vueloSeleccionado.agregarPasajero(pasajero)) {
                vueloSeleccionado.ocuparAsiento(asientoSeleccionado);
                vueloSeleccionado.setEstadoEmbarque(EstadoEmbarque.CERRADO);
                mapaReservas.put(pasajero.getDni(), new CheckIn(vueloSeleccionado, asientoSeleccionado, pasajero));
                System.out.println("**********************************************************");
                System.out.println("reserva realizada exitosamente para " + pasajero.getNombre() + " " + pasajero.getApellido());
                pasajero.setCheckIn(true);
            }
        } catch (CapacidadMaximaException e) {
            System.out.println(e.getMessage());
        }
    }


    private Pasajero crearPasajero(String asientoSeleccionado) throws DniRegistradoException {
        String eleccion;
        String nombre;
        String apellido;
        int edad;
        String dni;
        int cantidadEquipaje;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*******************************");
            System.out.println("Ingrese los datos del pasajero:");
            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
            System.out.print("Apellido: ");
             apellido = scanner.nextLine();
            System.out.print("Edad: ");
            edad = scanner.nextInt();
            scanner.nextLine();
            do {
                System.out.print("DNI: ");
                dni = scanner.nextLine();

                if (dni.length() != 8) {
                    System.out.println("El DNI debe tener exactamente 8 caracteres.");
                }
            } while (dni.length() != 8);



            // Verificar si el DNI ya está asociado a un check-in
            if (mapaReservas.containsKey(dni)) {
                throw new DniRegistradoException("El DNI " + dni + " ya está asociado a una reserva");
            }
            System.out.print("Cuanto equipaje desea llevar?: ");
            cantidadEquipaje = scanner.nextInt();
            scanner.nextLine();
            System.out.println("*********************************");
            System.out.println("Desea editar su informacion? s/n");
          eleccion = scanner.next().trim().toLowerCase() + "";

        }while (eleccion.equals("s"));

        return new Pasajero(nombre, apellido, edad, dni, cantidadEquipaje,asientoSeleccionado);

    }

    private List<String> generarAsientosDisponibles(Vuelo vuelo) {
        List<String> asientosDisponibles = new ArrayList<>();

        // Generar asientos según la capacidad del avión
        for (int i = 1; i <= vuelo.getAvion().getCapacidadAvion(); i++) {
            String asientoA = "A" + i;
            String asientoB = "B" + i;
            String asientoC = "C" + i;
            String asientoD = "D" + i;
            String asientoE = "E" + i;

            // Agregar asientos si no están ocupados
            if (!vuelo.getAsientos().contains(asientoA)) asientosDisponibles.add(asientoA);
            if (!vuelo.getAsientos().contains(asientoB)) asientosDisponibles.add(asientoB);
            if (!vuelo.getAsientos().contains(asientoC)) asientosDisponibles.add(asientoC);
            if (!vuelo.getAsientos().contains(asientoD)) asientosDisponibles.add(asientoD);
            if (!vuelo.getAsientos().contains(asientoE)) asientosDisponibles.add(asientoE);
        }

        return asientosDisponibles;
    }


    public void mostrarMap() {
        mapaReservas.forEach((key, value) -> System.out.println("Código: " + key + " -> " + value));
    }


    public Map<String, CheckIn> getMapaReservas() {
        return mapaReservas;
    }
}
