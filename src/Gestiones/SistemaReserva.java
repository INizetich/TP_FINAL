package Gestiones;

import Config.Configs;
import Excepciones.*;
import Enums.EstadoEmbarque;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import JSON.GestionJSON;
import Personas.Pasajero;
import Pertenencias.Valija;
import Utilidades.Utilities;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Scanner;

public class SistemaReserva {
    private final Map<String, Set<CheckIn>> mapaReservas;
    private AlmacenamientoAviones almacenamiento;
    public SistemaReserva() {
        this.mapaReservas = new HashMap<>();
        this.almacenamiento = new AlmacenamientoAviones();
    }

    public void realizarReserva() throws CodigoVueloInexistenteException, AsientoNoDisponibleException, DniRegistradoException {
        Scanner scanner = new Scanner(System.in);
        boolean continuarReservas = true; // Variable para controlar si continuar con reservas

        // Si NO es la primera ejecución, deserializa los archivos correspondientes
        if (!Configs.isFirstRun()) {
            // Verificar si el archivo de vuelos existe antes de deserializar
            File vuelosFile = new File("Archivos JSON/vuelos.json");
            if (vuelosFile.exists()) {
                try {
                    SistemaVuelo.setVuelosGenerados(GestionJSON.deserializarVuelos(vuelosFile.getPath()));
                    if (SistemaVuelo.getVuelosGenerados().isEmpty()) {
                        printCentered("🚫 No se encontraron vuelos deserializados.");
                    }
                } catch (Exception e) {
                    printCentered("🚫 Error al deserializar los vuelos: " + e.getMessage());
                }
            } else {
                printCentered("🚫 El archivo de vuelos no existe. Asegúrese de que se haya creado correctamente.");
                return; // Salir si el archivo no existe
            }

            // Comprobar si el archivo de CheckIn existe antes de deserializar
            File checkInFile = new File("Archivos JSON/Check-In.json");
            Map<String, Set<CheckIn>> reservasJSON = new HashMap<>();

            if (checkInFile.exists()) {
                reservasJSON = GestionJSON.deserializarReservas(checkInFile.getPath());
            }

            // Inicializar el mapa de reservas
            mapaReservas.putAll(reservasJSON);

            File conexionesFile = new File("Archivos JSON/ConexionesAeropuertos.json");
            Map<String, Map<String, Set<String>>> conexionesJSON = new HashMap<>();
            if (conexionesFile.exists()) {
                try {
                    conexionesJSON = GestionJSON.deserializarConexiones(conexionesFile.getPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ConexionAeropuerto.setConexiones(conexionesJSON);
        }

        while (continuarReservas) {
            boolean vueloSeleccionadoCorrecto = false; // Reiniciar la variable en cada nueva reserva

            while (!vueloSeleccionadoCorrecto) {
                // Mostrar los vuelos disponibles
                SistemaVuelo.mostrarVuelos();

                // Selección del vuelo
                printCentered("Ingrese el ID del vuelo que desea abordar: ");
                String idVueloSeleccionado = scanner.nextLine().toUpperCase();

                // Filtrar la lista de vuelos por el id de vuelo
                Vuelo vueloSeleccionado = SistemaVuelo.getVuelosGenerados().stream()
                        .filter(v -> v.getIdVuelo().equalsIgnoreCase(idVueloSeleccionado))
                        .findFirst()
                        .orElse(null);

                if (vueloSeleccionado == null) {
                    printCentered("Error, código de vuelo inexistente. Intente nuevamente.");
                    continue; // Permitir al usuario intentar nuevamente sin lanzar una excepción
                }

                try {
                    // Verificar si el estado de embarque está cerrado
                    if (vueloSeleccionado.getEstadoEmbarque() == EstadoEmbarque.CERRADO) {
                        throw new EstadoEmbarqueCerradoException("❌ El estado de embarque del vuelo es CERRADO. No se puede realizar la reserva.");
                    }

                    // Si el vuelo está disponible, mostrar los detalles del vuelo
                    printCentered("\n🛫 Seleccionaste el vuelo:");
                    printCentered("🆔 ID de Vuelo: " + vueloSeleccionado.getIdVuelo() +
                            " | 🌍 Origen: " + vueloSeleccionado.getOrigen() +
                            " | ✈️ Destino: " + vueloSeleccionado.getDestino() +
                            " | 🛩️ Avión: " + vueloSeleccionado.getAvion().getNombre() +
                            " | 🛃 Estado de Embarque: " + vueloSeleccionado.getEstadoEmbarque());

                    // Preguntar si desea cambiar el vuelo seleccionado
                    printCentered("¿Deseas cambiar el vuelo seleccionado? (s: sí, n: no): ");
                    String respuesta = scanner.nextLine();

                    if (respuesta.equalsIgnoreCase("s")) {
                        printCentered("🔄 Cambiando vuelo seleccionado...\n");
                        continue; // Permitir al usuario seleccionar otro vuelo
                    }

                    // Si no desea cambiar el vuelo, continuar con el proceso
                    vueloSeleccionadoCorrecto = true;

                    // Continuar con el proceso de reserva
                    List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
                    if (asientosDisponibles.isEmpty()) {
                        printCentered("No hay asientos disponibles para este vuelo.");
                        vueloSeleccionadoCorrecto = false; // Permitir al usuario seleccionar otro vuelo
                        continue; // Regresar al inicio del bucle
                    }

                    // Mostrar asientos disponibles
                    Utilities.limpiarPantalla();
                    printCentered("\n🛫 Asientos Disponibles ✨");
                    printCentered("==================================================");
                    printCentered("🔍 Aquí están los asientos disponibles en el vuelo:");
                    printCentered("==================================================");
                    printCentered("🪑 " + String.join(" 🪑 ", asientosDisponibles));
                    printCentered("==================================================");

                    String asientoSeleccionado = "";
                    boolean asientoValido = false;
                    while (!asientoValido) {
                        // Seleccionar asiento
                        printCentered("Seleccione un asiento disponible: ");
                        asientoSeleccionado = scanner.nextLine().toUpperCase();

                        // Comprobar si el asiento está disponible
                        if (!asientosDisponibles.contains(asientoSeleccionado)) {
                            printCentered("❌ Error: El asiento seleccionado no existe o no está disponible.");
                        } else {
                            asientoValido = true; // Salir del bucle si el asiento es válido
                        }
                    }

                    // Crear el pasajero
                    Pasajero pasajero = crearPasajero(asientoSeleccionado);

                    try {
                        // Agregar pasajero al vuelo
                        if (vueloSeleccionado.agregarPasajero(pasajero)) {
                            vueloSeleccionado.ocuparAsiento(asientoSeleccionado);

                            // Cambiar el estado del embarque con una probabilidad del 20%
                            if (cambiarEstadoConProbabilidad(vueloSeleccionado)) {
                                printCentered("❗ El vuelo ha cambiado su estado de embarque a CERRADO.");
                            }

                            // Crear un nuevo CheckIn
                            CheckIn nuevoCheckIn = new CheckIn(vueloSeleccionado, asientoSeleccionado, pasajero);

                            // Verificar si ya existe un Set para ese DNI en el mapa de reservas
                            Set<CheckIn> checkInsExistentes = mapaReservas.getOrDefault(pasajero.getDni(), new HashSet<>());

                            // Agregar el nuevo CheckIn al Set de reservas
                            checkInsExistentes.add(nuevoCheckIn);

                            // Guardar el Set actualizado en el mapa de reservas
                            mapaReservas.put(pasajero.getDni(), checkInsExistentes);

                            // Registrar la conexión entre aeropuertos
                            ConexionAeropuerto conexionAeropuerto = new ConexionAeropuerto();
                            conexionAeropuerto.registrarConexion(vueloSeleccionado.getOrigen(), vueloSeleccionado.getDestino(), vueloSeleccionado.getIdVuelo());

                            pasajero.setCheckIn(true);

                            try {
                                List<Vuelo> vuelos = SistemaVuelo.getVuelosGenerados();
                                GestionJSON.serializarLista(vuelos, "Archivos JSON/vuelos.json");
                                // Serializar y guardar las reservas y conexiones
                                GestionJSON.serializarMapa(mapaReservas, "Archivos JSON/Check-In.json");
                                GestionJSON.serializarMapa(ConexionAeropuerto.getConexiones(), "Archivos JSON/ConexionesAeropuertos.json");
                                Configs.setFirstRunComplete();
                            } catch (Exception e) {
                                printCentered("Error al guardar reservas o conexiones.");
                                e.printStackTrace();
                            }
                            Utilities.limpiarPantalla();
                            printCentered("============================================================================");
                            printCentered("Reserva realizada exitosamente para " + pasajero.getNombre() + " " + pasajero.getApellido());
                        }
                    } catch (CapacidadMaximaException e) {
                        printCentered(e.getMessage());
                    }
                } catch (EstadoEmbarqueCerradoException e) {
                    printCentered(e.getMessage());
                    printCentered("Por favor, ingrese otro ID de vuelo.\n");
                    continue; // Volver a mostrar la lista de vuelos y permitir la selección de otro vuelo
                }
            }

            // Una vez completado el ciclo de selección y reserva, preguntar al usuario si desea continuar
            printCentered("==================================");
            printCentered("➡️ ¿Desea hacer otra reserva? ✈️");
            printCentered("👉 (s: sí, n: no): ");
            String continuar = scanner.nextLine();
            if (!continuar.equalsIgnoreCase("s")) {
                continuarReservas = false; // Salir del bucle principal
            }
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

            printCentered("=====================================");
            printCentered("👤 Ingrese los datos del pasajero ✈️");

            // Validar nombre
            printCentered("📝 Nombre: ");
            nombre = scanner.nextLine().trim();

            // Validar apellido
            printCentered("📝 Apellido: ");
            apellido = scanner.nextLine().trim();

            // Validar edad
            do {
                printCentered("🎂 Edad: ");
                try {
                    edad = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    if (edad <= 0 || edad >= 110) {
                        printCentered("❌ La edad debe ser mayor a 0 y menor que 110.");
                    }
                } catch (InputMismatchException e) {
                    printCentered("❌ Por favor, ingrese un número válido para la edad.");
                    scanner.nextLine(); // Limpiar entrada no válida
                    edad = -1; // Forzar la repetición del ciclo
                }
            } while (edad <= 0 || edad >= 110);

            // Validar DNI
            do {
                printCentered("🆔 DNI (8 caracteres): ");
                dni = scanner.nextLine().trim();

                // Verificar que el DNI tenga exactamente 8 caracteres
                if (dni.length() != 8) {
                    printCentered("❌ El DNI debe tener exactamente 8 caracteres.");
                } else if (mapaReservas.containsKey(dni)) {
                    throw new DniRegistradoException("🚫 El DNI " + dni + " ya está asociado a una reserva");
                }
            } while (dni.length() != 8); // Repetir hasta que el DNI sea válido

            printCentered("📦 ¿Cuántas valijas llevará? ");
            int cantidadEquipaje = scanner.nextInt();
            scanner.nextLine();

            // Cobro por valijas adicionales
            if (cantidadEquipaje > 2) {
                tarifaExtra += (cantidadEquipaje - 2) * 50; // Cobro extra por cada valija adicional
                printCentered("💸 Se aplicará un cargo adicional de $" + (cantidadEquipaje - 2) * 50 + " USD por valijas adicionales.");
            }

            // Recolectar detalles de cada valija
            for (int i = 1; i <= cantidadEquipaje; i++) {
                printCentered("🎒 Ingrese los datos de la valija " + i + ":");

                // Validar dimensión de la valija
                String dimension;
                do {
                    printCentered("📏 Dimensión (en cm): ");
                    dimension = scanner.nextLine().trim();
                    if (dimension.isEmpty()) {
                        printCentered("❌ La dimensión de la valija no puede estar vacía.");
                    }
                } while (dimension.isEmpty());

                // Validar peso de la valija
                double peso;
                do {
                    printCentered("⚖️ Peso (en kg): ");
                    try {
                        peso = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer

                        if (peso <= 0) {
                            printCentered("❌ El peso de la valija debe ser mayor a 0.");
                        } else if (peso > 25) {
                            tarifaExtra += (peso - 25) * 10; // Cobro extra por cada kg adicional
                            printCentered("💸 Se aplicará un cargo adicional de $" + (peso - 25) * 10 + " USD por peso extra en la valija " + i + ".");
                        }
                    } catch (InputMismatchException e) {
                        printCentered("❌ Por favor, ingrese un número válido para el peso.");
                        scanner.nextLine(); // Limpiar entrada no válida
                        peso = -1; // Forzar la repetición del ciclo
                    }
                } while (peso <= 0);

                valijas.add(new Valija(dimension, peso));
            }

            printCentered("=====================================");
            printCentered("📝 ¿Desea editar su información? (s/n)");
            eleccion = scanner.nextLine().trim().toLowerCase();
            Utilities.limpiarPantalla();
        } while (eleccion.equals("s"));

        // Mostrar tarifa total
        if (tarifaExtra > 0) {
            printCentered("💰 Tarifa adicional total por equipaje: $" + tarifaExtra);
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

            // Agregar asientos si no están ocupados
            if (!vuelo.getAsientos().contains(asientoA)) {
                asientosDisponibles.add(asientoA);
            }
            if (!vuelo.getAsientos().contains(asientoB)) {
                asientosDisponibles.add(asientoB);
            }
            if (!vuelo.getAsientos().contains(asientoC)) {
                asientosDisponibles.add(asientoC);
            }
        }

        // Eliminar asientos ocupados de la lista de disponibles
        for (Pasajero pasajero : vuelo.getListaPasajeros()) {
            String asientoOcupado = pasajero.getNroAsiento();
            asientosDisponibles.remove(asientoOcupado);
        }

        return asientosDisponibles;
    }






    public void mostrarMap() {
        mapaReservas.forEach((key, value) -> System.out.println("Código: " + key + " -> " + value));
    }

    public Map<String, Set<CheckIn>> getMapaReservas() {
        return mapaReservas;
    }


    /// -----------------------------METODOS PRIVATE--------------------------------///
    // Método para cambiar el estado del embarque con probabilidad del 20%
    private boolean cambiarEstadoConProbabilidad(Vuelo vuelo) {
        Random random = new Random();
        int probabilidad = random.nextInt(100); // Genera un número entre 0 y 99
        if (probabilidad < 20) { // Cambiar a CERRADO si la probabilidad es menor a 20
            vuelo.setEstadoEmbarque(EstadoEmbarque.CERRADO);
            return true; // Indica que el estado se cambió
        }
        return false; // El estado no cambió
    }


    public static void printCentered(String text) {
        int terminalWidth = 150; // Puedes ajustar este valor según el ancho de tu terminal
        int padding = Math.max((terminalWidth - text.length()) / 2, 0); // Evitar valores negativos

        String paddedText = " ".repeat(padding) + text;
        System.out.println(paddedText);
    }

    public static void limpiarPantalla() {
        // Imprime 50 líneas vacías para simular la limpieza de pantalla
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }}


