package UI;
import PreEmbarque.PreEmbarque;
import Utilidades.Utilities;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
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

import static Utilidades.Utilities.printCentered;

public class MenuClientes {
    private static double credito = 0.0; // Crédito disponible del cliente
    private static final String Click = "src/Sonidos/click.mp3";
    private static final String Soundtrack = "src/Sonidos/SoundtrackTienda.mp3";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";
    private static volatile boolean stopMusic = false;
    private static Thread audioThread;
    public static void mostrarMenuCliente() throws IOException, InterruptedException {

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

        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();

        Scanner scanner = new Scanner(System.in);
        int opcionCliente;

        do {
            Utilities.limpiarPantalla();
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
                    Utilities.limpiarPantalla();
                    printCentered("4️⃣ Salir 👋");
                    printCentered("🎉 ¡BIENVENIDO AL SISTEMA DE RESERVAS DE VUELOS! ✈️");
                   printCentered("=====================================================");
                    printCentered("Por favor, elija una opción:");
                    printCentered("1️⃣ Realizar una reserva en un vuelo 🛫");
                    printCentered("2️⃣ Mostrar una reserva asociada al pasajero 📋");
                    printCentered("3️⃣ Mostrar conexión del vuelo 🔗");
                    printCentered("4️⃣ Generar boleto de avión 🎟️");
                    printCentered("5️⃣ Preembarque 🎟️");
                    printCentered("=====================================================");
                    int opcionReserva = scanner.nextInt();
                    reproducirClick();
                    scanner.nextLine(); // Limpiar el buffer de entrada
                   Utilities.limpiarPantalla();
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
                                    Utilities.limpiarPantalla();
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
                    printCentered("Usted ha elegido ingresar a tiendas. 🏬");
                    musicaMenu();
                    int opcion;
                    Utilities.limpiarPantalla();
                    do {
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
                    } while (opcion != 4);



                    printCentered("🔄Presione Enter para volver al menú principal...🔄");
                    scanner.nextLine();
                    break;

                case 3:
                    printCentered(GREEN + "===============================" + RESET);
                    printCentered(GREEN + "      █████╗ ████████╗███╗   ███╗" + RESET);
                    printCentered(GREEN + "     ██╔══██╗╚══██╔══╝████╗ ████║" + RESET);
                    printCentered(GREEN + "     ███████║   ██║   ██╔████╔██║" + RESET);
                    printCentered(GREEN + "     ██╔══██║   ██║   ██║╚██╔╝██║" + RESET);
                    printCentered(GREEN + "     ██║  ██║   ██║   ██║ ╚═╝ ██║" + RESET);
                    printCentered(GREEN + "     ╚═╝  ╚═╝   ╚═╝   ╚═╝     ╚═╝" + RESET);
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
                    detenerMusica();
                    printCentered("¿Desea volver al menú principal? (sí/no): ");
                    String respuesta = scanner.nextLine().trim().toLowerCase();
                    if (!respuesta.equals("sí") && !respuesta.equals("si")) {
                        Utilities.mostrarCargandoMenuPrincipal();
                        Menu.Menu();
                        // Sale del ciclo y termina el caso
                    } else if (respuesta.equals("no") && respuesta.equals("no")) {
                        System.exit(0);
                    }
                    break;

                default:
                    printCentered("Opción no válida. Por favor, intente nuevamente. 😤😤");
                    break;
            }
        } while (opcionCliente != 4);

    if (opcionCliente != 4) {

    } }
    private static void mostrarBebidas(Scanner scanner) {
        int cantidad = 0;
        reproducirClick();
        Utilities.limpiarPantalla();
        printCentered("====================================");
        printCentered("    🥤 BEBIDAS 🥤");
        printCentered("====================================");
        printCentered("1. 🧊 Agua mineral   - $1.00");
        printCentered("2. 🥤 Gaseosa        - $1.50");
        printCentered("3. 🍹 Jugo natural   - $2.00");
        printCentered("====================================");
        printCentered("Seleccione su bebida : ");
        int bebida = scanner.nextInt();
        scanner.nextLine();
        printCentered("ingrese la cantidad a comprar");
        if(cantidad==0) {
            try {
            } catch (CantidadIncorrectaException e) {
                System.out.println(e.toString());
            }
        }
        cantidad = scanner.nextInt();
        scanner.nextLine();
        reproducirClick();

        if (bebida > 0 && bebida <= 3) {
            String item = switch (bebida) {
                case 1 -> "Agua mineral";
                case 2 -> "Gaseosa";
                case 3 -> "Jugo natural";
                default -> "";
            };

            Map<String,Map<String,Integer>> stock = GestionJSON.deserializarStock("Archivos JSON/Stock.json");
            StockManager.setStock(stock);
            // Verificar stock antes de permitir la compra
            int stockDisponible = StockManager.consultarStock("Bebidas", item);
            GestionJSON.serializarMapa(StockManager.getStock(),"Archivos JSON/Stock.json");
            if (stockDisponible > 0) {
                double precio = bebida == 1 ? 1.00 : bebida == 2 ? 1.50 : 2.00;
                realizarCompra(precio, "bebida");
                // Reducir stock
                StockManager.eliminarDeStock("Bebidas", item, cantidad);
                reproducirClick();
            } else {
                printCentered("❌ No hay suficiente stock de " + item + ".");
            }
        } else if (bebida != 0) {
            Utilities.limpiarPantalla();
            printCentered("❌ Opción inválida.");
            reproducirClick();
        }
    }

    private static void mostrarComida(Scanner scanner) {
        reproducirClick();
        Utilities.limpiarPantalla();
        printCentered("====================================");
        printCentered("   🍔 COMIDA 🍔");
        printCentered("====================================");
        printCentered("1. 🥟 Empanada       - $1.50");
        printCentered("2. 🥪 Sandwich       - $2.50");
        printCentered("3. 🍟 Papas fritas   - $1.75");
        printCentered("4. 🌭 Hot Dog        - $2.00");
        printCentered("5. 🍕 Porcion pizza  - $3.00");
        printCentered("====================================");
        printCentered("Seleccione su comida (0 para volver): ");
        int comida = scanner.nextInt();
        reproducirClick();
        printCentered("Ingrese la cantidad a comprar: ");
        int cantidad = scanner.nextInt();
        reproducirClick();

        if (comida > 0 && comida <= 5) {
            String item = switch (comida) {
                case 1 -> "Empanada";
                case 2 -> "Sandwich";
                case 3 -> "Papas Fritas";
                case 4 -> "Hot Dog";
                case 5 -> "Porcion pizza";
                default -> null;
            };

            // Deserializar stock y establecerlo en StockManager
            Map<String, Map<String, Integer>> stock = GestionJSON.deserializarStock("Archivos JSON/Stock.json");
            StockManager.setStock(stock);

            // Verificar stock antes de permitir la compra
            int stockDisponible = StockManager.consultarStock("Comida", item);  // Cambiar a "Comida" en lugar de "Bebidas"
            GestionJSON.serializarMapa(StockManager.getStock(), "Archivos JSON/Stock.json");

            if (stockDisponible > 0) {
                // Definir el precio según el item seleccionado
                double precioComida = switch (comida) {
                    case 1 -> 1.50;
                    case 2 -> 2.50;
                    case 3 -> 1.75;
                    case 4 -> 2.00;
                    case 5 -> 3.00;
                    default -> 0.00;  // Aunque no debería llegar a este caso
                };

                realizarCompra(precioComida, "Comida");

                // Reducir el stock
                StockManager.eliminarDeStock("Comida", item, cantidad);  // Cambiar a "Comida" en lugar de "Bebidas"
                reproducirClick();
            } else {
                printCentered("❌ No hay suficiente stock de " + item + ".");
            }
        } else if (comida != 0) {
            printCentered("❌ Opción inválida.");
            reproducirClick();
        }
    }

    private static void mostrarArticulosVarios(Scanner scanner) {
        reproducirClick();
        Utilities.limpiarPantalla();
        printCentered("====================================");
        printCentered("🛍️ ARTÍCULOS VARIOS 🛍️");
        printCentered("====================================");
        printCentered("1. 📖 Revista        - $4.00");
        printCentered("2. 🍬 Chicle         - $0.50");
        printCentered("3. 🔥 Encendedor     - $1.00");
        printCentered("====================================");
        printCentered("Seleccione un artículo (0 para volver): ");
        int articulo = scanner.nextInt();
        reproducirClick();
        printCentered("Ingrese la cantidad a comprar: ");
        int cantidad = scanner.nextInt();
        reproducirClick();

        if (articulo > 0 && articulo <= 3) {
            String item = switch (articulo) {
                case 1 -> "Revista";
                case 2 -> "Chicle";
                case 3 -> "Encendedor";
                default -> null;
            };

            // Deserializar stock y establecerlo en StockManager
            Map<String, Map<String, Integer>> stock = GestionJSON.deserializarStock("Archivos JSON/Stock.json");
            StockManager.setStock(stock);

            // Imprimir el stock para depurar
            System.out.println("Stock cargado: " + stock);

            // Verificar stock antes de permitir la compra
            int stockDisponible = StockManager.consultarStock("Articulos varios", item);  // Usar "Articulos varios"
            GestionJSON.serializarMapa(StockManager.getStock(), "Archivos JSON/Stock.json");

            if (stockDisponible > 0) {
                // Definir el precio según el artículo seleccionado
                double precioArticulo = switch (articulo) {
                    case 1 -> 4.00;
                    case 2 -> 0.50;
                    case 3 -> 1.00;
                    default -> 0.00;  // Aunque no debería llegar a este caso
                };

                realizarCompra(precioArticulo, "artículo");

                // Reducir el stock
                StockManager.eliminarDeStock("Articulos varios", item, cantidad);  // Usar "Articulos varios"
                reproducirClick();
            } else {
                printCentered("❌ No hay suficiente stock de " + item + ".");
            }
        } else if (articulo != 0) {
            printCentered("❌ Opción inválida.");
            reproducirClick();
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
            Utilities.limpiarPantalla();
            printCentered("✅ Retiro exitoso. Crédito restante: $" + String.format("%.2f", credito));
            reproducirClick();

        } else if (monto > credito) {
            Utilities.limpiarPantalla();
            printCentered("❌ Fondos insuficientes. Intente con un monto menor.");
            reproducirClick();
        } else {
            Utilities.limpiarPantalla();
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
            Utilities.limpiarPantalla();
            printCentered("✅ Compra de " + tipo + " realizada con éxito. Crédito restante: $" + String.format("%.2f", credito));
            reproducirClick();
        } else {
            Utilities.limpiarPantalla();
            printCentered("❌ No tienes suficiente crédito para esta compra.");
            reproducirClick();
        }
    }

    private static void musicaMenu() {
        stopMusic = false;
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

    public static void detenerMusica() {
        stopMusic = true; // Cambiar la bandera para detener la música
        if (audioThread != null && audioThread.isAlive()) {
            audioThread.interrupt(); // Interrumpir el hilo
        }
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






}

