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

        boolean vueloSeleccionadoCorrecto = false;  // Variable para controlar la selección del vuelo

        while (!vueloSeleccionadoCorrecto) {
            // Mostrar los vuelos disponibles
            SistemaVuelo.mostrarVuelos();

            // Selección del vuelo
            System.out.print("Ingrese el ID del vuelo que desea abordar: ");
            String idVueloSeleccionado = scanner.nextLine().toUpperCase();

            // Filtrar la lista de vuelos por el id de vuelo
            Vuelo vueloSeleccionado = SistemaVuelo.getVuelos().stream()
                    .filter(v -> v.getIdVuelo().equalsIgnoreCase(idVueloSeleccionado))
                    .findFirst()
                    .orElse(null);

            if (vueloSeleccionado == null) {
                throw new CodigoVueloInexistenteException("Error, código de vuelo inexistente.");
            }

            // Mostrar los detalles del vuelo seleccionado
            System.out.println("\n🛫 Seleccionaste el vuelo:");
            System.out.println("🆔 ID de Vuelo: " + vueloSeleccionado.getIdVuelo() +
                    " | 🌍 Origen: " + vueloSeleccionado.getOrigen() +
                    " | ✈️ Destino: " + vueloSeleccionado.getDestino() +
                    " | 🛩️ Avión: " + vueloSeleccionado.getAvion().getNombre() +
                    " | 🛃 Estado de Embarque: " + vueloSeleccionado.getEstadoEmbarque());

            // Preguntar si desea cambiar el vuelo seleccionado
            System.out.print("\n¿Desea cambiar el vuelo seleccionado? (s/n): ");
            String cambiarVuelo = scanner.nextLine().trim().toLowerCase();

            if (cambiarVuelo.equals("s")) {
                System.out.println("==============================================");
                System.out.println("Por favor, ingrese el ID de otro vuelo.");
                continue;  // Continuar el ciclo para permitir al usuario seleccionar otro vuelo
            }

            // Si el usuario decide no cambiar el vuelo, salir del ciclo
            vueloSeleccionadoCorrecto = true;

            // Si no se cambia el vuelo, continuar con el proceso de reserva
            List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
            if (asientosDisponibles.isEmpty()) {
                throw new AsientoNoDisponibleException("No hay asientos disponibles para este vuelo.");
            }

            System.out.println("\n🛫 Asientos Disponibles ✨");
            System.out.println("==================================================");
            System.out.println("🔍 Aquí están los asientos disponibles en el vuelo:");
            System.out.println("==================================================");
            System.out.println("🪑 " + String.join(" 🪑 ", asientosDisponibles));
            System.out.println("==================================================");
            String asientoSeleccionado = "";
            boolean token = true;

            while (token) {
                try {
                    // Seleccionar asiento
                    System.out.print("Seleccione un asiento disponible: ");
                    asientoSeleccionado = scanner.nextLine().toUpperCase();

                    // Comprobar si el asiento está disponible
                    if (!asientosDisponibles.contains(asientoSeleccionado)) {
                        throw new AsientoNoDisponibleException("❌ Error: El asiento seleccionado no existe o no está disponible.");
                    }

                    // Si el asiento es válido, salir del bucle
                    token = false;

                } catch (AsientoNoDisponibleException e) {
                    // Informar al usuario y permitir un nuevo intento
                    System.out.println(e.getMessage());
                    System.out.println("Por favor, intente nuevamente.");
                }
            }

            Pasajero pasajero = crearPasajero(asientoSeleccionado);

            try {
                if (vueloSeleccionado.agregarPasajero(pasajero)) {
                    vueloSeleccionado.ocuparAsiento(asientoSeleccionado);
                    vueloSeleccionado.setEstadoEmbarque(EstadoEmbarque.CERRADO);
                    mapaReservas.put(pasajero, new CheckIn(vueloSeleccionado, asientoSeleccionado, pasajero));

                    // Registrar la conexión entre aeropuertos
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
    }


    private Pasajero crearPasajero(String asientoSeleccionado) throws DniRegistradoException, DatosInvalidoValijaException {
        String eleccion;
        String nombre;
        String apellido;
        int edad = 0;
        String dni;
        double tarifaExtra = 0.0;
        List<Valija> valijas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("\n=====================================");
            System.out.println("👤 Ingrese los datos del pasajero ✈️");

            // Ingreso del nombre
            System.out.print("📝 Nombre: ");
            nombre = scanner.nextLine();

            // Ingreso del apellido
            System.out.print("📝 Apellido: ");
            apellido = scanner.nextLine();

            // Validar edad
            while (true) {
                System.out.print("🎂 Edad: ");
                try {
                    edad = Integer.parseInt(scanner.nextLine());
                    if (edad <= 0) {
                        System.out.println("❌ La edad debe ser un número positivo.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("❌ Por favor, ingrese un número válido.");
                }
            }

            // Validar DNI
            do {
                System.out.print("🆔 DNI: ");
                dni = scanner.nextLine();

                if (dni.length() != 8) {
                    System.out.println("❌ El DNI debe tener exactamente 8 caracteres.");
                }
            } while (dni.length() != 8);

            // Verificar si el DNI ya está registrado
            if (mapaReservas.containsKey(dni)) {
                throw new DniRegistradoException("🚫 El DNI " + dni + " ya está asociado a una reserva.");
            }

            // Ingreso de valijas
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
                System.out.println("\n🎒 Ingrese los datos de la valija " + i + ":");
                System.out.print("📏 Dimensión: ");
                String dimension = scanner.nextLine();

                if (dimension.isEmpty()) {
                    throw new DatosInvalidoValijaException("❌ La dimensión de la valija no puede estar vacía.");
                }

                double peso = 0.0;
                while (true) {
                    System.out.print("⚖️ Peso (en kg): ");
                    try {
                        peso = Double.parseDouble(scanner.nextLine());
                        if (peso > 25) {
                            tarifaExtra += (peso - 25) * 10; // Cobro extra por cada kg adicional
                            System.out.println("💸 Se aplicará un cargo adicional de $" + (peso - 25) * 10 + " USD por peso extra.");
                        } else if (peso <= 0) {
                            System.out.println("❌ El peso de la valija debe ser mayor a 0.");
                            continue;
                        }
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Por favor, ingrese un valor numérico válido.");
                    }
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
