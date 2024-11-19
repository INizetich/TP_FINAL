package UI;
import Aeropuerto.Aeropuerto;
import Config.ConfigAdmin;
import Excepciones.*;
import Gestiones.*;
import JSON.GestionJSON;
import Utilidades.Utilities;
import javazoom.jl.player.Player;
import Gestiones.StockManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class MenuAdministracion {

    private static final String Click = "src/Sonidos/click.mp3";
    private static final String Soundtrack = "src/Sonidos/SoundtrackTienda.mp3";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";


    public static void mostrarMenuAdministracion() {

        ConfigAdmin.cargarConfiguracionAdmin();
        // INSTANCIA DE CLASES IMPORTANTES
        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
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

        int opcionAdmin = 0;
        System.out.println("\u001B[32mIngrese su dni para loguearte\u001B[0m");
        String loguin = scanner.nextLine().trim();
        do {
            try {
                if (admin.comprobarLogin(loguin)) {
                    System.out.println("\u001B[32mACCESSO GARANTIZADO\u001B[0m");
                    Thread.sleep(250);
                    System.out.println("\u001B[31m----------------------BIENVENIDO AL SISTEMA DE ADMINISTRADOR----------------------\u001B[0m");
                    do {
                        System.out.println("\u001B[31m1.\u001B[0m Dar privilegios de administrador a una persona");
                        System.out.println("\u001B[31m2.\u001B[0m Agregar una persona a la lista de personal");
                        System.out.println("\u001B[31m3.\u001B[0m Eliminar una persona de la lista de personal por DNI");
                        System.out.println("\u001B[31m4.\u001B[0m Eliminar una persona de los privilegios de administrador por DNI");
                        System.out.println("\u001B[31m5.\u001B[0m Eliminar un vuelo registrado");
                        System.out.println("\u001B[31m6.\u001B[0m Agregar un vuelo a la lista");
                        System.out.println("\u001B[31m7.\u001B[0m Mostrar la lista de empleados");
                        System.out.println("\u001B[31m8.\u001B[0m Mostrar la lista de administradores");
                        System.out.println("\u001B[31m8.\u001B[0m Menu Stock ");
                        System.out.println("\u001B[31m9.\u001B[0m Cerrar sesión");
                        System.out.print("\u001B[32m > \u001B[0m");
                        opcionAdmin = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionAdmin) {
                            case 1:
                                try {
                                    admin.agregarAdministradorManual();
                                } catch (InputMismatchException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case 2:
                                try {
                                    admin.agregarPersonal();
                                } catch (InputMismatchException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case 3:
                                try {
                                    admin.eliminarPersonalPorDNI();
                                } catch (EmpleadoInexistenteException e) {
                                    System.out.println(e.getMessage());
                                    e.printStackTrace();
                                    break;
                                }
                                break;
                            case 4:
                                try {
                                    // Mostramos la lista de empleados después de deserializar
                                    System.out.println("💼 Lista de administradores actualizada:");
                                    admin.mostrarCuentasAdmin();
                                    System.out.print("\u001B[32m > \u001B[0m");
                                    System.out.print("🔑 Ingresa el DNI del administrador a eliminar: ");
                                    String dniAdmin = scanner.nextLine();
                                    admin.eliminarAdministradorDNI(dniAdmin);
                                } catch (dniNoEncontradoException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case 5:

                                try {
                                    SistemaVuelo.setVuelosGenerados(GestionJSON.deserializarVuelos("Archivos JSON/vuelos.json"));
                                    SistemaVuelo.mostrarVuelos();
                                    System.out.println("Ingrese el ID de vuelo a eliminar");
                                    String idVuelo = scanner.nextLine();
                                    admin.eliminarVueloPorID(idVuelo);
                                } catch (CodigoVueloInexistenteException e) {
                                    e.printStackTrace();
                                }

                                break;
                            case 6:
                                try {

                                    SistemaAeropuerto.mostrarAeropuertos();
                                    System.out.println("ingrese el origen del vuelo");
                                    String origen = scanner.nextLine();
                                    System.out.println("Ingrese el destino del vuelo");
                                    String destino = scanner.nextLine();
                                    admin.agregarVuelo(origen, destino, almacenamientoAviones);
                                } catch (AeropuertoNoEncontradoException e) {
                                    e.printStackTrace();
                                }


                                break;
                            case 7:
                                System.out.print("\u001B[32m > \u001B[0m");
                                System.out.println("\n================================================");
                                System.out.println("✨✨ ¿Desea mostrar la lista de empleados? ✨✨");
                                System.out.println("================================================");
                                System.out.println("👉 Opción 1: Mostrar lista de empleados");
                                System.out.println("❌ Opción 2: No mostrar lista de empleados");
                                System.out.println("================================================\n");
                                String listaEm = scanner.nextLine().trim();

                                if (listaEm.equalsIgnoreCase("1")) {
                                    admin.mostrarListaEmpleados();
                                } else if (listaEm.equalsIgnoreCase("2")) {
                                    return;
                                }
                                break;
                            case 8:
                                System.out.print("\u001B[32m > \u001B[0m");
                                System.out.println("\n================================================");
                                System.out.println("📋 ¿Te gustaría ver la lista de administradores? 📋");
                                System.out.println("================================================");
                                System.out.println("\u001B[32m✅ Opción 1: Ver la lista de administradores\u001B[0m");
                                System.out.println("\u001B[31m❌ Opción 2: No ver la lista de administradores\u001B[0m");
                                System.out.println("================================================\n");
                                String verAdmins = scanner.nextLine();

                                if (verAdmins.equalsIgnoreCase("1")) {
                                    admin.mostrarCuentasAdmin();
                                } else if (verAdmins.equalsIgnoreCase("2")) {
                                    return;
                                }


                                break;

                            case 9:
                                int opcionkiosco = 0;
                                do {
                                    limpiarPantalla();
                                    printCentered("===== Menú de Kiosco =====");
                                    printCentered("1️⃣ Control de Stock 📦");
                                    printCentered("2️⃣ Otras opciones de administrador...");
                                    printCentered("3️⃣ Salir");
                                    printCentered("=================================");
                                    opcionkiosco = scanner.nextInt();
                                    scanner.nextLine(); // Limpiar buffer
                                    reproducirClick();

                                    switch (opcionkiosco) {
                                        case 1:
                                            int opcionStock = 0;
                                            do {
                                            limpiarPantalla();
                                            printCentered("===== Control de Stock =====");
                                            printCentered("1️⃣ Agregar a stock ➕");
                                            printCentered("2️⃣ Eliminar de stock ➖");
                                            printCentered("3️⃣ Ver stock 📋");
                                            printCentered("4️⃣ Salir 🚪");
                                            printCentered("=============================");
                                            opcionkiosco = scanner.nextInt();
                                            scanner.nextLine(); // Limpiar buffer
                                            reproducirClick();

                                            switch (opcionkiosco) {
                                                case 1:
                                                    printCentered("Ingrese el nombre del producto: ");
                                                    String producto = scanner.nextLine();
                                                    printCentered("Ingrese la cantidad a agregar: ");
                                                    int cantidadAgregar = scanner.nextInt();
                                                    StockManager.agregarAStock(producto, cantidadAgregar);
                                                    printCentered("✅ Producto agregado exitosamente.");
                                                    break;

                                                case 2:
                                                    printCentered("Ingrese el nombre del producto: ");
                                                    producto = scanner.nextLine();
                                                    printCentered("Ingrese la cantidad a eliminar: ");
                                                    int cantidadEliminar = scanner.nextInt();
                                                    if (StockManager.eliminarDeStock(producto, cantidadEliminar)) {
                                                        printCentered("✅ Producto eliminado exitosamente.");
                                                    } else {
                                                        printCentered("❌ No hay suficiente stock o el producto no existe.");
                                                    }
                                                    break;

                                                case 3:
                                                    printCentered("===== Stock Actual =====");
                                                    Map<String, Integer> stock = StockManager.obtenerStock();
                                                    if (stock.isEmpty()) {
                                                        printCentered("📦 El stock está vacío.");
                                                    } else {
                                                        stock.forEach((item, cantidad) ->
                                                                printCentered(item + ": " + cantidad + " unidades")
                                                        );
                                                    }
                                                    break;

                                                case 4:
                                                    printCentered("👋 Saliendo del control de stock.");

                                                    break;

                                                default:
                                                    printCentered("❌ Opción inválida. Intente nuevamente.");
                                                    break;
                                            }
                                            printCentered("🔄 Presione Enter para continuar...");
                                            scanner.nextLine();
                                        }while (opcionkiosco!= 3);
                                            break; ///break de la linea 184
                                        case 2:
                                            // Implementar otras opciones según sea necesario
                                            printCentered("⚙️ Opciones de administrador aún no implementadas.");
                                            break;
                                        case 3:
                                            printCentered("👋 Saliendo del Menú Administrador.");
                                            break;
                                        default:
                                            printCentered("❌ Opción inválida. Intente nuevamente.");
                                            break;
                                    }
                                } while (opcionkiosco != 4);
                                break;

                            case 10:
                                Utilities.mostrarCerrandoAdmin();
                                break;
                            }
}while (opcionAdmin < 11);
                            // Preguntar si desea volver al menú principal de administrador
                            if (opcionAdmin != 10) { // Si no seleccionó la opción 8 para cerrar sesión
                                System.out.print("\u001B[31m¿Desea volver al menú principal? (sí/no): \u001B[0m");
                                String respuesta = scanner.nextLine().trim().toLowerCase();
                                if (!respuesta.equals("sí") && !respuesta.equals("si")) {
                                    Utilities.mostrarCargandoMenuPrincipal();
                                    Menu.Menu();
                                    // Sale del ciclo y termina el caso
                                } else if (respuesta.equals("no") && respuesta.equals("no")) {
                                    System.exit(0);
                                }
                            }

                        } while (opcionAdmin != 10) ; // Repite el ciclo hasta que elija salir

                    } catch(AccesoDenegadoException e){
                        e.printStackTrace();
                        break;
                    } catch(InterruptedException e){
                        throw new RuntimeException(e);
                    } catch(IOException e){
                        throw new RuntimeException(e);
                    }
                    break;
                } while (opcionAdmin < 9) ;
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
}}

