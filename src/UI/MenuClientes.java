package UI;
import PreEmbarque.PreEmbarque;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import Aeropuerto.Aeropuerto;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import Config.Configs;
import Excepciones.*;
import Gestiones.*;
import JSON.GestionJSON;

import java.util.List;
import java.util.Scanner;

public class MenuClientes {
    private static double credito = 0.0; // Crédito disponible del cliente
    private static final String Click = "src/Sonidos/click.mp3";
    private static final String Soundtrack = "src/Sonidos/SoundtrackTienda.mp3";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";
    public static void mostrarMenuCliente() {

        ///VARIABLES IMPORTANTES
        String opcionString = "";
        // INSTANCIA DE CLASES IMPORTANTES
        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
        //admin.cargarListaEmpleados();
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);
        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        almacenamientoAviones.generarAviones(15, admin.getListaEmpleados());

        SistemaVuelo.obtenerVuelosGenerados(almacenamientoAviones);
        aeropuerto.cargarHangaresAeropuerto(admin.getListaEmpleados());
        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();

        Scanner scanner = new Scanner(System.in);
        int opcionCliente;

        do {
            limpiarPantalla();
            printCentered("======== Menú de Clientes ========");
            printCentered("1️⃣ Hacer una reserva 🛫");
            printCentered("2️⃣ Ingresar a tiendas 🏬");
            printCentered("3️⃣ ATM 💰");
            printCentered("4️⃣ Salir 👋");
            opcionCliente = scanner.nextInt();
            scanner.nextLine(); //
            reproducirClick();

            switch (opcionCliente) {
                case 1:
                    limpiarPantalla();
                    printCentered("🎉 ¡BIENVENIDO AL SISTEMA DE RESERVAS DE VUELOS! ✈️");
                    printCentered("=====================================================");
                    printCentered("Por favor, elija una opción:");
                    printCentered("1️⃣ Realizar una reserva en un vuelo 🛫");
                    printCentered("2️⃣ Mostrar una reserva asociada al pasajero 📋");
                    printCentered("3️⃣ Mostrar conexión del vuelo 🔗");
                    printCentered("4️⃣ Generar boleto de avión 🎟️");
                    printCentered("4️⃣ Prembarque 🎟️");
                    printCentered("=====================================================");
                    int opcionReserva = scanner.nextInt();
                    reproducirClick();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                    limpiarPantalla();
                    switch (opcionReserva) {
                        case 1:
                            do {
                                try {
                                    sistemaReserva.realizarReserva();
                                } catch (DniRegistradoException | CodigoVueloInexistenteException | AsientoNoDisponibleException e) {
                                    e.printStackTrace();
                                } finally {
                                    reproducirClick();
                                }
                            } while (opcionString.equals("s"));
                            printCentered("==================================");
                            printCentered("✅ Fin del proceso de reservas. ✈️");
                            printCentered("==================================");
                            reproducirClick();
                            break;

                        case 2:
                            String opcionConsulta = "";
                            String dni = "";
                            do {
                                try {
                                    limpiarPantalla();
                                    printCentered("==================================");
                                    printCentered("🆔 Ingrese su DNI para mostrar su información de reserva: 📑");
                                    printCentered("==================================");
                                    dni = scanner.nextLine().trim();
                                    reproducirClick();
                                    CheckIn.mostrarReserva(dni, sistemaReserva);
                                    printCentered("==================================");
                                    printCentered("🔄 ¿Desea consultar otra reserva? 🤔");
                                    printCentered("👉 (s: ✔️ / n: ❌)");
                                    printCentered("==================================");
                                    opcionConsulta = scanner.nextLine().trim().toLowerCase();
                                    reproducirClick();
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
                                        printCentered("==============================================");
                                        printCentered("🌟 ¿Desea consultar su boleto de avión? 🌟");
                                        printCentered("📝 Ingrese 's' para ✔️ o 'n' para ❌.");
                                        opcionString = scanner.nextLine().trim().toLowerCase();
                                        reproducirClick();
                                    }

                                    if (opcionString.equals("s")) {
                                        printCentered("==============================================");
                                        printCentered("🔍 Ingrese su número de DNI: ");
                                        nroDni = scanner.nextLine().trim();
                                        reproducirClick();

                                        // Generar el boleto
                                        CheckIn.generarBoleto(nroDni, sistemaReserva);
                                    }

                                    printCentered("¿Desea generar otro boleto de avión? 🤔");
                                    printCentered("📝 Ingrese 's' para ✔️ o 'n' para ❌.");
                                    generarBoleto = scanner.nextLine().trim().toLowerCase();
                                    reproducirClick();
                                } catch (ReservaInexistenteException e) {
                                    Utilidades.Utilities.mostrarCargando();
                                    printCentered("❌ No se encontró una reserva asociada al DNI ingresado. ❌");
                                    printCentered("🛑 Por favor, verifique la información e intente nuevamente. 🛑");
                                    printCentered("\nPresione Enter para seguir con el programa... 👈");
                                    scanner.nextLine();
                                    reproducirClick();

                                    mostrarMensajeFinal = false;
                                    break;
                                }
                            } while (generarBoleto.equals("s"));

                            if (mostrarMensajeFinal) {
                                printCentered("Gracias por utilizar nuestro servicio. ¡Buen viaje! ✈️🌍");
                            }
                            break;


                        case 5:

                            PreEmbarque.verificarSeguridad();


                            break;
                    }

                    printCentered("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 2:
                    // Implementar el caso 2 (Ingresar a tiendas)
                    printCentered("Usted ha elegido ingresar a tiendas. 🏬");
                    musicaMenu();
                    int opcion;
                    do {
                        limpiarPantalla();
                        printCentered("=== 🛒 Bienvenido al Mini Kiosko 🛒 ===");
                        printCentered("1. 🥤 Bebidas");
                        printCentered("2. 🍔 Comida");
                        printCentered("3. 🛍️ Artículos varios");
                        printCentered("4. 🚪 Salir");
                        printCentered("💰 Crédito disponible: $" + String.format("%.2f", credito));
                        printCentered("Seleccione una opción: ");

                        opcion = scanner.nextInt();

                        switch (opcion) {
                            case 1 -> mostrarBebidas(scanner);
                            case 2 -> mostrarComida(scanner);
                            case 3 -> mostrarArticulosVarios(scanner);
                            case 4 -> printCentered("¡Gracias por visitar el kiosko! 🛒");
                            default -> printCentered("❌ Opción inválida. Intente nuevamente.");
                        }
                    } while (opcion != 5);

                    scanner.close();

                    printCentered("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 3:
                    printCentered(GREEN + "===============================" + RESET);
                    printCentered(GREEN + "         █████╗ ████████╗███╗   ███╗" + RESET);
                    printCentered(GREEN + "        ██╔══██╗╚══██╔══╝████╗ ████║" + RESET);
                    printCentered(GREEN + "        ███████║   ██║   ██╔████╔██║" + RESET);
                    printCentered(GREEN + "        ██╔══██║   ██║   ██║╚██╔╝██║" + RESET);
                    printCentered(GREEN + "        ██║  ██║   ██║   ██║ ╚═╝ ██║" + RESET);
                    printCentered(GREEN + "        ╚═╝  ╚═╝   ╚═╝   ╚═╝     ╚═╝" + RESET);
                    printCentered(GREEN + "===============================" + RESET);

                    printCentered(WHITE + "🏦 Opciones disponibles:" + RESET);
                    printCentered(GREEN + "1. 💳 Agregar Crédito" + RESET);
                    printCentered(GREEN + "2. 💵 Consultar Saldo" + RESET);
                    printCentered(GREEN + "3. 💸 Retirar Dinero" + RESET);
                    printCentered(GREEN + "4. 🚪 Salir" + RESET);
                    printCentered("Seleccione una opción 👉: ");

                    opcion = scanner.nextInt();
                    switch (opcion) {
                        case 1:
                            agregarCredito(scanner);
                            break;
                        case 2:
                            consultarSaldo();
                            break;
                        case 3:
                            retirarDinero(scanner);
                            break;
                        case 4:
                            printCentered("👋 Gracias por usar el ATM. ¡Hasta luego!");
                            break;

                        default:
                            printCentered("❌ Opción inválida. Por favor, intente de nuevo.");
                            break;
                    }
                    printCentered("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 4:
                    printCentered("🚪 Gracias por utilizar nuestros servicios. ¡Hasta luego! 🚪");
                    List<Vuelo> vuelos = SistemaVuelo.getVuelosGenerados();
                    GestionJSON.serializarLista(vuelos, "Archivos JSON/vuelos.json");
                    Configs.setFirstRunComplete();
                    break;

                default:
                    printCentered("Opción no válida. Por favor, intente nuevamente. 😤😤");
                    break;
            }
        } while (opcionCliente != 4);
    }

    private static void mostrarBebidas(Scanner scanner) {
        reproducirClick();
        printCentered("====================================");
        printCentered("    🥤 BEBIDAS 🥤");
        printCentered("====================================");
        printCentered("1. 🧊 Agua mineral   - $1.00");
        printCentered("2. 🥤 Gaseosa        - $1.50");
        printCentered("3. 🍹 Jugo natural   - $2.00");
        printCentered("====================================");
        printCentered("Seleccione su bebida (0 para volver): ");
        int bebida = scanner.nextInt();
        reproducirClick();

        if (bebida > 0 && bebida <= 3) {
            double precio = bebida == 1 ? 1.00 : bebida == 2 ? 1.50 : 2.00;
            realizarCompra(precio, "bebida");
            reproducirClick();
        } else if (bebida != 0) {
            printCentered("❌ Opción inválida.");
            reproducirClick();
        }
    }

    private static void mostrarComida(Scanner scanner) {
        reproducirClick();
        printCentered("====================================");
        printCentered("   🍔 COMIDA 🍔");
        printCentered("====================================");
        printCentered("1. 🥟 Empanada       - $1.50");
        printCentered("2. 🥪 Sandwich       - $2.50");
        printCentered("3. 🍟 Papas fritas   - $1.75");
        printCentered("4. 🌭 Hot Dog        - $2.00");
        printCentered("5. 🍕 Porción pizza  - $3.00");
        printCentered("====================================");
        printCentered("Seleccione su comida (0 para volver): ");
        int comida = scanner.nextInt();
        reproducirClick();

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
            printCentered("❌ Opción inválida.");
            reproducirClick();
        }
    }
    private static void mostrarArticulosVarios(Scanner scanner) {
        reproducirClick();
        printCentered("====================================");
        printCentered("🛍️ ARTÍCULOS VARIOS 🛍️");
        printCentered("====================================");
        printCentered("1. 📖 Revista        - $3.00");
        printCentered("2. 🍬 Chicle         - $0.50");
        printCentered("3. 🔥 Encendedor     - $1.20");
        printCentered("====================================");
        printCentered("Seleccione un artículo (0 para volver): ");
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
            printCentered("❌ Opción inválida.");
        }
    }

    private static void consultarSaldo() {
        printCentered("\n💳 Saldo actual en su cuenta: $" + String.format("%.2f", credito));
        reproducirClick();
    }

    // Método para retirar dinero
    private static void retirarDinero(Scanner scanner) {
        printCentered("\n💸 Ingrese la cantidad de dinero a retirar: $");
        double monto = scanner.nextDouble();
        if (monto > 0 && monto <= credito) {
            credito -= monto;
            printCentered("✅ Retiro exitoso. Crédito restante: $" + String.format("%.2f", credito));
            reproducirClick();
        } else if (monto > credito) {
            printCentered("❌ Fondos insuficientes. Intente con un monto menor.");
            reproducirClick();
        } else {
            printCentered("❌ El monto debe ser mayor a $0.");
            reproducirClick();
        }
    }

    private static void agregarCredito(Scanner scanner) {
        printCentered("\n💵 Ingrese la cantidad de crédito a agregar: $");
        double monto = scanner.nextDouble();
        if (monto > 0) {
            credito += monto;
            printCentered("✅ Crédito agregado exitosamente. Crédito actual: $" + String.format("%.2f", credito));
            reproducirClick();
        } else {
            printCentered("❌ El monto debe ser mayor a $0.");
            reproducirClick();
        }
    }

    private static void realizarCompra(double precio, String tipo) {
        if (credito >= precio) {
            credito -= precio;
            printCentered("✅ Compra de " + tipo + " realizada con éxito. Crédito restante: $" + String.format("%.2f", credito));
            reproducirClick();
        } else {
            printCentered("❌ No tienes suficiente crédito para esta compra.");
            reproducirClick();
        }
    }

    private static void musicaMenu() {
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
    private static void reproducirClick() {
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
    }

}

