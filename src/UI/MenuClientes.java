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

    private static double credito = 0.0;
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
                                } catch (DniRegistradoException | CodigoVueloInexistenteException |
                                         AsientoNoDisponibleException e) {
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

                    // Implementar el caso 3 (ATM)

                    System.out.println("Usted ha elegido ATM. 💰");
                    System.out.println("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 3:
                    musicaMenuTiendas();
                    System.out.println("Usted ha elegido ingresar al Kiosco. 🏬");
                    int opcion;
                    do {
                        System.out.println("\n=== 🛒 Bienvenido al Mini Kiosco 🛒 ===");
                        System.out.println("1. 🥤 Bebidas");
                        System.out.println("2. 🍔 Comida");
                        System.out.println("3. 🛍️ Artículos varios");
                        System.out.println("4. 💵 Agregar crédito");
                        System.out.println("5. 🚪 Salir");
                        System.out.println("💰 Crédito disponible: $" + String.format("%.2f", credito));
                        System.out.print("Seleccione una opción: ");
                        musicaMenuTiendas();
                        opcion = scanner.nextInt();

                        switch (opcion) {
                            case 1 -> mostrarBebidas(scanner);
                            case 2 -> mostrarComida(scanner);
                            case 3 -> mostrarArticulosVarios(scanner);
                            case 4 -> agregarCredito(scanner);
                            case 5 -> System.out.println("¡Gracias por visitar el kiosko! 🛒");
                            default -> System.out.println("❌ Opción inválida. Intente nuevamente.");
                        }
                    } while (opcion != 5);

                    scanner.close();

                    System.out.println("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 4:
                    System.out.println("🚪 Gracias por utilizar nuestros servicios. ¡Hasta luego! 🚪");
                    List<Vuelo> vuelos = SistemaVuelo.obtenerVuelosGenerados();
                    GestionJSON.serializarLista(vuelos, "Archivos JSON/vuelos.json");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente. 😤😤");
                    break;
            }
        } while (opcionCliente != 4);

    }

///VARIABLES PARA EL APARTADO KIOSCO
private static void mostrarBebidas(Scanner scanner) {
    ClickSonido();
    System.out.println("\n====================================");
    System.out.println("           🥤 BEBIDAS 🥤");
    System.out.println("====================================");
    System.out.println("1. 🧊 Agua mineral   - $1.00");
    System.out.println("2. 🥤 Gaseosa        - $1.50");
    System.out.println("3. 🍹 Jugo natural   - $2.00");
    System.out.println("====================================");
    System.out.print("Seleccione su bebida (0 para volver): ");
    int bebida = scanner.nextInt();

    if (bebida > 0 && bebida <= 3) {
        double precio = bebida == 1 ? 1.00 : bebida == 2 ? 1.50 : 2.00;
        realizarCompra(precio, "bebida");
    } else if (bebida != 0) {
        System.out.println("❌ Opción inválida.");
    }
}

private static void mostrarComida(Scanner scanner) {
    ClickSonido();
    System.out.println("\n====================================");
    System.out.println("           🍔 COMIDA 🍔");
    System.out.println("====================================");
    System.out.println("1. 🥟 Empanada       - $1.50");
    System.out.println("2. 🥪 Sandwich       - $2.50");
    System.out.println("3. 🍟 Papas fritas   - $1.75");
    System.out.println("4. 🌭 Hot Dog        - $2.00");
    System.out.println("5. 🍕 Porción pizza  - $3.00");
    System.out.println("====================================");
    System.out.print("Seleccione su comida (0 para volver): ");
    int comida = scanner.nextInt();

    if (comida > 0 && comida <= 5) {
        double precio = switch (comida) {
            case 1 -> 1.50;
            case 2 -> 2.50;
            case 3 -> 1.75;
            case 4 -> 2.00;
            case 5 -> 3.00;
            default -> 0.0;
        };
        realizarCompra(precio, "comida");
    } else if (comida != 0) {
        System.out.println("❌ Opción inválida.");
    }
}

private static void mostrarArticulosVarios(Scanner scanner) {
    ClickSonido();
    System.out.println("\n====================================");
    System.out.println("       🛍️ ARTÍCULOS VARIOS 🛍️");
    System.out.println("====================================");
    System.out.println("1. 📖 Revista        - $3.00");
    System.out.println("2. 🍬 Chicle         - $0.50");
    System.out.println("3. 🔥 Encendedor     - $1.20");
    System.out.println("====================================");
    System.out.print("Seleccione un artículo (0 para volver): ");
    int articulo = scanner.nextInt();

    if (articulo > 0 && articulo <= 3) {
        double precio = switch (articulo) {
            case 1 -> 3.00;
            case 2 -> 0.50;
            case 3 -> 1.20;
            default -> 0.0;
        };
        realizarCompra(precio, "artículo");
    } else if (articulo != 0) {
        System.out.println("❌ Opción inválida.");
    }
}

private static void agregarCredito(Scanner scanner) {
    System.out.print("\n💵 Ingrese la cantidad de crédito a agregar: $");
    double monto = scanner.nextDouble();
    if (monto > 0) {
        credito += monto;
        System.out.println("✅ Crédito agregado exitosamente. Crédito actual: $" + String.format("%.2f", credito));
    } else {
        System.out.println("❌ El monto debe ser mayor a $0.");
    }
}

private static void realizarCompra(double precio, String tipo) {
    if (credito >= precio) {
        credito -= precio;
        System.out.println("✅ Compra de " + tipo + " realizada con éxito. Crédito restante: $" + String.format("%.2f", credito));
    } else {
        System.out.println("❌ No tienes suficiente crédito para esta compra.");
    }
}
/// //////////////////////////////////////////////////////////////////////////
/// /// METODOS PARA EL SONIDO
private static void ClickSonido() {
    Thread audioThread = new Thread(() -> {
        try (FileInputStream fis = new FileInputStream(Click)) {
            Player player = new Player(fis);
            player.play();
        } catch (Exception e) {
            System.out.println("Error al reproducir el archivo: " + e.getMessage());
        }
    });
    audioThread.setDaemon(true); // El hilo se detendrá automáticamente cuando termine el programa
    audioThread.start();
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////