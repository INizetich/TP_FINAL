package Gestiones;

import Config.Configs;
import Excepciones.*;
import Enums.EstadoEmbarque;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import JSON.GestionJSON;
import Personas.Pasajero;
import Pertenencias.Valija;

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
        if (!Configs.isFirstRun() ) {
            // Verificar si el archivo de vuelos existe antes de deserializar
            File vuelosFile = new File("Archivos JSON/vuelos.json");
            if (vuelosFile.exists()) {
                List<Vuelo> vuelos = null;
                try {
                    vuelos = GestionJSON.deserializarVuelos(vuelosFile.getPath());
                    System.out.println("Vuelos deserializados: " + vuelos.size()); // Verifica el tamaño
                    if (vuelos.isEmpty()) {
                        System.out.println("🚫 No se encontraron vuelos deserializados.");
                    } else {
                        SistemaVuelo.setVuelosGenerados(vuelos);
                    }
                } catch (Exception e) {
                    System.out.println("🚫 Error al deserializar los vuelos: " + e.getMessage());
                }
            } else {
                System.out.println("🚫 El archivo de vuelos no existe. Asegúrese de que se haya creado correctamente.");
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
                System.out.print("Ingrese el ID del vuelo que desea abordar: ");
                String idVueloSeleccionado = scanner.nextLine().toUpperCase();

                // Filtrar la lista de vuelos por el id de vuelo
                Vuelo vueloSeleccionado = SistemaVuelo.getVuelosGenerados().stream()
                        .filter(v -> v.getIdVuelo().equalsIgnoreCase(idVueloSeleccionado))
                        .findFirst()
                        .orElse(null);

                if (vueloSeleccionado == null) {
                    System.out.println("Error, código de vuelo inexistente. Intente nuevamente.");
                    continue; // Permitir al usuario intentar nuevamente sin lanzar una excepción
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

                // Continuar con el proceso de reserva
                List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
                if (asientosDisponibles.isEmpty()) {
                    System.out.println("No hay asientos disponibles para este vuelo.");
                    vueloSeleccionadoCorrecto = false; // Permitir al usuario seleccionar otro vuelo
                    continue; // Regresar al inicio del bucle
                }

                // Mostrar asientos disponibles
                System.out.println("\n🛫 Asientos Disponibles ✨");
                System.out.println("==================================================");
                System.out.println("🔍 Aquí están los asientos disponibles en el vuelo:");
                System.out.println("==================================================");
                System.out.println("🪑 " + String.join(" 🪑 ", asientosDisponibles));
                System.out.println("==================================================");

                String asientoSeleccionado = "";
                boolean asientoValido = false;
                while (!asientoValido) {
                    // Seleccionar asiento
                    System.out.print("Seleccione un asiento disponible: ");
                    asientoSeleccionado = scanner.nextLine().toUpperCase();

                    // Comprobar si el asiento está disponible
                    if (!asientosDisponibles.contains(asientoSeleccionado)) {
                        System.out.println("❌ Error: El asiento seleccionado no existe o no está disponible.");
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
                        vueloSeleccionado.setEstadoEmbarque(EstadoEmbarque.CERRADO);

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
                            GestionJSON.serializarLista(vuelos,"Archivos JSON/vuelos.json");
                            // Serializar y guardar las reservas y conexiones
                            GestionJSON.serializarMapa(mapaReservas, "Archivos JSON/Check-In.json");
                            GestionJSON.serializarMapa(ConexionAeropuerto.getConexiones(), "Archivos JSON/ConexionesAeropuertos.json");
                            Configs.setFirstRunComplete();
                        } catch (Exception e) {
                            System.out.println("Error al guardar reservas o conexiones.");
                            e.printStackTrace();
                        }

                        System.out.println("============================================================================");
                        System.out.println("Reserva realizada exitosamente para " + pasajero.getNombre() + " " + pasajero.getApellido());
                    }
                } catch (CapacidadMaximaException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Una vez completado el ciclo de selección y reserva, preguntar al usuario si desea continuar
            System.out.println("==================================");
            System.out.println("➡️ ¿Desea hacer otra reserva? ✈️");
            System.out.print("👉 (s: ✔️ / n: ❌): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();
            continuarReservas = respuesta.equals("s");
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

            // Validar nombre
            System.out.print("📝 Nombre: ");
            nombre = scanner.nextLine().trim();

            // Validar apellido
            System.out.print("📝 Apellido: ");
            apellido = scanner.nextLine().trim();

            // Validar edad
            do {
                System.out.print("🎂 Edad: ");
                try {
                    edad = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    if (edad <= 0 || edad >= 110) {
                        System.out.println("❌ La edad debe ser mayor a 0 y menor que 110.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("❌ Por favor, ingrese un número válido para la edad.");
                    scanner.nextLine(); // Limpiar entrada no válida
                    edad = -1; // Forzar la repetición del ciclo
                }
            } while (edad <= 0 || edad >= 110);

            // Validar DNI
            System.out.print("🆔 DNI: ");
            dni = scanner.nextLine().trim();

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

                // Validar dimensión de la valija
                String dimension;
                do {
                    System.out.print("📏 Dimensión: ");
                    dimension = scanner.nextLine().trim();
                    if (dimension.isEmpty()) {
                        System.out.println("❌ La dimensión de la valija no puede estar vacía.");
                    }
                } while (dimension.isEmpty());

                // Validar peso de la valija
                double peso;
                do {
                    System.out.print("⚖️ Peso (en kg): ");
                    try {
                        peso = scanner.nextDouble();
                        scanner.nextLine(); // Limpiar el buffer

                        if (peso <= 0) {
                            System.out.println("❌ El peso de la valija debe ser mayor a 0.");
                        } else if (peso > 25) {
                            tarifaExtra += (peso - 25) * 10; // Cobro extra por cada kg adicional
                            System.out.println("💸 Se aplicará un cargo adicional de $" + (peso - 25) * 10 + " USD por peso extra en la valija " + i + ".");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("❌ Por favor, ingrese un número válido para el peso.");
                        scanner.nextLine(); // Limpiar entrada no válida
                        peso = -1; // Forzar la repetición del ciclo
                    }
                } while (peso <= 0);

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
}