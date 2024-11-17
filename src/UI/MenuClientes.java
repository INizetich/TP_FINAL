package UI;

import Aeropuerto.Aeropuerto;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import Excepciones.*;
import Gestiones.*;
import JSON.GestionJSON;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.util.List;
import java.util.Scanner;

public class MenuClientes {

    private static final String Click = "src/Sonidos/Click.mp3";
    private static final String Soundtrack = "src/Sonidos/SoundtrackTienda.mp3";

    public static void mostrarMenuCliente() {

        ///VARIABLES IMPORTANTES
        String opcionString = "";
        // INSTANCIA DE CLASES IMPORTANTES

        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
        admin.cargarListaEmpleados();
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);
        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        almacenamientoAviones.generarAviones(15, admin.getListaEmpleados());

        // CREACIÓN DE VUELOS DE MANERA AUTOMÁTICA
        SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);
        aeropuerto.cargarHangaresAeropuerto(admin.getListaEmpleados());
        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();

        Scanner scanner = new Scanner(System.in);
        int opcionCliente;

        do {

            System.out.println("\n======== Menú de Clientes ========");
            System.out.println("1️⃣ Hacer una reserva 🛫");
            System.out.println("2️⃣ Ingresar a tiendas 🏬");
            System.out.println("3️⃣ ATM 💰");
            System.out.println("4️⃣ Salir 👋");
            opcionCliente = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcionCliente) {
                case 1:
                    System.out.println("🎉 ¡BIENVENIDO AL SISTEMA DE RESERVAS DE VUELOS! ✈️");
                    System.out.println("=====================================================");
                    System.out.println("Por favor, elija una opción:");
                    System.out.println("1️⃣ Realizar una reserva en un vuelo 🛫");
                    System.out.println("2️⃣ Mostrar una reserva asociada al pasajero 📋");
                    System.out.println("3️⃣ Mostrar conexión del vuelo 🔗");
                    System.out.println("4️⃣ Generar boleto de avión 🎟️");
                    System.out.println("=====================================================");
                    int opcionReserva = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer de entrada

                    switch (opcionReserva) {
                        case 1:

                            do {
                                try {
                                    sistemaReserva.realizarReserva();
                                } catch (DniRegistradoException | CodigoVueloInexistenteException | AsientoNoDisponibleException e) {
                                    e.printStackTrace();
                                } finally {
                                    System.out.println("==================================");
                                    System.out.println(" ➡️ ¿Desea hacer otra reserva? ✈️");
                                    System.out.println("👉 (s: ✔️ / n: ❌)");
                                    System.out.println("==================================");
                                    opcionString = scanner.nextLine().trim().toLowerCase();
                                }
                            } while (opcionString.equals("s"));
                            System.out.println("==================================");
                            System.out.println("✅ Fin del proceso de reservas. ✈️");
                            System.out.println("==================================");
                            break;

                        case 2:
                            String opcionConsulta = "";
                            String dni = "";
                            do {
                                try {
                                    System.out.println("==================================");
                                    System.out.println("🆔 Ingrese su DNI para mostrar su información de reserva: 📑");
                                    System.out.println("==================================");
                                    dni = scanner.nextLine().trim();
                                    CheckIn.mostrarReserva(dni, sistemaReserva);
                                    System.out.println("==================================");
                                    System.out.println("🔄 ¿Desea consultar otra reserva? 🤔");
                                    System.out.println("👉 (s: ✔️ / n: ❌)");
                                    System.out.println("==================================");
                                    opcionConsulta = scanner.nextLine().trim().toLowerCase();
                                } catch (dniNoEncontradoException e) {
                                    e.printStackTrace();
                                }
                            } while (opcionConsulta.equals("s"));
                            break;

                        case 3:

                            ConexionAeropuerto.mostrarConexiones();
                            break;



                        case 4:
                            String nroDni = "";
                            String generarBoleto = "";
                            boolean mostrarMensajeFinal = true;

                            do {
                                try {
                                    if (!generarBoleto.equals("s")) {
                                        System.out.println("==============================================");
                                        System.out.println("🌟 ¿Desea consultar su boleto de avión? 🌟");
                                        System.out.println("📝 Ingrese 's' para ✔️ o 'n' para ❌.");
                                        opcionString = scanner.nextLine().trim().toLowerCase();
                                    }

                                    if (opcionString.equals("s")) {
                                        System.out.println("==============================================");
                                        System.out.print("🔍 Ingrese su número de DNI: ");
                                        nroDni = scanner.nextLine().trim();


                                        // Generar el boleto
                                        CheckIn.generarBoleto(nroDni, sistemaReserva);  // Este método puede lanzar la excepción

                                    }

                                    // Preguntar si desea generar otro boleto
                                    System.out.println("¿Desea generar otro boleto de avión? 🤔");
                                    System.out.println("📝 Ingrese 's' para ✔️ o 'n' para ❌.");
                                    generarBoleto = scanner.nextLine().trim().toLowerCase();
                                } catch (ReservaInexistenteException e) {
                                    // Mostrar animación de carga
                                    Utilidades.Utilities.mostrarCargando();

                                    // Mensaje si no se encuentra la reserva
                                    System.out.println("❌ No se encontró una reserva asociada al DNI ingresado. ❌");
                                    System.out.println("🛑 Por favor, verifique la información e intente nuevamente. 🛑");
                                    System.out.println("\nPresione Enter para seguir con el programa... 👈");
                                    scanner.nextLine();

                                    mostrarMensajeFinal = false;
                                    break;
                                }
                            } while (generarBoleto.equals("s"));

                            // Mostrar mensaje final solo si no ocurrió una excepción
                            if (mostrarMensajeFinal) {
                                System.out.println("Gracias por utilizar nuestro servicio. ¡Buen viaje! ✈️🌍");
                            }

                            break;



                        // Agregar más casos si es necesario
                    }
                    // Esperar que el usuario presione Enter para volver al menú principal
                    System.out.println("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 2:
                    musicaMenuTiendas();
                    // Implementar el caso 2 (Ingresar a tiendas)
                    System.out.println("Usted ha elegido ingresar a tiendas. 🏬");
                    System.out.println("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 3:
                    // Implementar el caso 3 (ATM)
                    System.out.println("Usted ha elegido ATM. 💰");
                    System.out.println("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.println("🚪 Gracias por utilizar nuestros servicios. ¡Hasta luego! 🚪");
                    List<Vuelo> vuelos = SistemaVuelo.obtenerVuelosGenerados();
                    GestionJSON.serializarLista(vuelos,"Archivos JSON/vuelos.json");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente. 😤😤");
                    break;
            }
        } while (opcionCliente != 4);
    }

    private static void musicaMenuTiendas() {
        Thread audioThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(Soundtrack)) {
                Player player = new Player(fis);
                player.play();
            } catch (Exception e) {
                System.out.println("Error al reproducir el archivo: " + e.getMessage());
            }
        });
        audioThread.setDaemon(true); // El hilo se detendrá automáticamente cuando termine el programa
        audioThread.start();
    }
}
