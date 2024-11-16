package Gestiones;

import Excepciones.*;
import Enums.EstadoEmbarque;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import JSON.GestionJSON;
import Personas.Pasajero;
import Pertenencias.Valija;

import java.util.*;
import java.util.Scanner;

public class SistemaReserva {
    private final Map<Pasajero, CheckIn> mapaReservas; // UUID como clave, CheckIn como valor

    public SistemaReserva() {
        this.mapaReservas = new HashMap<>();
    }

    public void realizarReserva() throws CodigoVueloInexistenteException, AsientoNoDisponibleException, DniRegistradoException {
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
            throw new CodigoVueloInexistenteException("Error, código de vuelo inexistente.");
        }

        // Mostrar asientos disponibles
        List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
        if (asientosDisponibles.isEmpty()) {
            throw new AsientoNoDisponibleException("No hay asientos disponibles para este vuelo.");
        }
        System.out.println();
        System.out.println("🛫 Asientos Disponibles ✨");
        System.out.println("==================================================");
        System.out.println("🔍 Aquí están los asientos disponibles en el vuelo:");
        System.out.println("==================================================");
        System.out.println("🪑 " + String.join(" 🪑 ", asientosDisponibles));
        System.out.println("==================================================");

        // Seleccionar asiento
        System.out.print("Seleccione un asiento disponible: ");
        String asientoSeleccionado = scanner.nextLine().toUpperCase();

        // Comprobar si el asiento está disponible
        if (!asientosDisponibles.contains(asientoSeleccionado)) {
            throw new AsientoNoDisponibleException("El asiento seleccionado no está disponible.");
        }

        Pasajero pasajero = crearPasajero(asientoSeleccionado);

        try {
            if (vueloSeleccionado.agregarPasajero(pasajero)) {
                vueloSeleccionado.ocuparAsiento(asientoSeleccionado);
                vueloSeleccionado.setEstadoEmbarque(EstadoEmbarque.CERRADO);
                mapaReservas.put(pasajero, new CheckIn(vueloSeleccionado, asientoSeleccionado, pasajero));

                // **Registrar la conexión entre aeropuertos**
                ConexionAeropuerto conexionAeropuerto = new ConexionAeropuerto();
                conexionAeropuerto.registrarConexion(vueloSeleccionado.getOrigen(), vueloSeleccionado.getDestino(), vueloSeleccionado.getIdVuelo());

                pasajero.setCheckIn(true);
                GestionJSON.serializarMapa(mapaReservas, "Archivos JSON/Check-In.json");
                GestionJSON.serializarMapa(ConexionAeropuerto.getConexiones(), "Archivos JSON/ConexionesAeropuertos.json");
                System.out.println("**********************************************************");
                System.out.println("Reserva realizada exitosamente para " + pasajero.getNombre() + " " + pasajero.getApellido());

            }
        } catch (CapacidadMaximaException e) {
            System.out.println(e.getMessage());
        }
    }

    private Pasajero crearPasajero(String asientoSeleccionado) throws DniRegistradoException, DatosInvalidoValijaException {
        String eleccion;
        String nombre;
        String apellido;
        int edad;
        String dni;
        double tarifaExtra = 0.0;
        List<Valija> valijas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println();
            System.out.println("=====================================");
            System.out.println("👤 Ingrese los datos del pasajero ✈️");
            System.out.print("📝 Nombre: ");
            nombre = scanner.nextLine();
            System.out.print("📝 Apellido: ");
            apellido = scanner.nextLine();
            System.out.print("🎂 Edad: ");
            edad = scanner.nextInt();
            scanner.nextLine();

            do {
                System.out.print("🆔 DNI: ");
                dni = scanner.nextLine();

                if (dni.length() != 8) {
                    System.out.println("❌ El DNI debe tener exactamente 8 caracteres.");
                }
            } while (dni.length() != 8);

            // Verificar si el DNI ya está asociado a un check-in
            if (mapaReservas.containsKey(dni)) {
                throw new DniRegistradoException("🚫 El DNI " + dni + " ya está asociado a una reserva");
            }

            System.out.print("📦 ¿Cuántas valijas llevará? ");
            int cantidadEquipaje = scanner.nextInt();
            scanner.nextLine();

            // Cobro por valijas adicionales
            if (cantidadEquipaje > 2) {
                tarifaExtra += (cantidadEquipaje - 2) * 50; // Cobro extra por cada valija adicional
                System.out.println("💸 Se aplicará un cargo adicional de $" + (cantidadEquipaje - 2) * 50 + " USD por valijas adicionales.");
            }

            // Recolectar detalles de cada valija
            for (int i = 1; i <= cantidadEquipaje; i++) {
                System.out.println("🎒 Ingrese los datos de la valija " + i + ":");
                System.out.print("📏 Dimensión: ");
                String dimension = scanner.nextLine();

                if (dimension.isEmpty()){
                    throw new DatosInvalidoValijaException("❌ La dimensión de la valija no puede estar vacía.");
                }
                System.out.print("⚖️ Peso (en kg): ");
                double peso = scanner.nextDouble();
                scanner.nextLine();

                // Cobro por peso extra
                if (peso > 25) {
                    tarifaExtra += (peso - 25) * 10; // Cobro extra por cada kg adicional
                    System.out.println("💸 Se aplicará un cargo adicional de $" + (peso - 25) * 10 + " USD por peso extra en la valija " + i + ".");
                } else if (peso <= 0) {
                    throw new DatosInvalidoValijaException("❌ El peso de la valija debe ser mayor a 0.");
                }

                valijas.add(new Valija(dimension, peso));
            }

            System.out.println("=====================================");
            System.out.println("📝 ¿Desea editar su información? (s/n)");
            eleccion = scanner.nextLine().trim().toLowerCase();
        } while (eleccion.equals("s"));

        // Mostrar tarifa total
        if (tarifaExtra > 0) {
            System.out.println("💰 Tarifa adicional total por equipaje: $" + tarifaExtra);
        }

        return new Pasajero(nombre, apellido, edad, dni, valijas, asientoSeleccionado);
    }





    private List<String> generarAsientosDisponibles(Vuelo vuelo) {
        List<String> asientosDisponibles = new ArrayList<>();

        // Generar asientos según la capacidad del avión
        for (int i = 1; i <= vuelo.getAvion().getCapacidadAvion(); i++) {
            String asientoA = "A" + i;
            String asientoB = "B" + i;
            String asientoC = "C" + i;
            //String asientoD = "D" + i;
            //String asientoE = "E" + i;

            // Agregar asientos si no están ocupados
            if (!vuelo.getAsientos().contains(asientoA)) asientosDisponibles.add(asientoA);
            if (!vuelo.getAsientos().contains(asientoB)) asientosDisponibles.add(asientoB);
            if (!vuelo.getAsientos().contains(asientoC)) asientosDisponibles.add(asientoC);
            // if (!vuelo.getAsientos().contains(asientoD)) asientosDisponibles.add(asientoD);
            //if (!vuelo.getAsientos().contains(asientoE)) asientosDisponibles.add(asientoE);
        }

        return asientosDisponibles;
    }






    public void mostrarMap() {
        mapaReservas.forEach((key, value) -> System.out.println("Código: " + key + " -> " + value));
    }

    public Map<Pasajero, CheckIn> getMapaReservas() {
        return mapaReservas;
    }
}
