package UI;

import Aeropuerto.Aeropuerto;
import Aviones.Avion;
import Aviones.Hangar;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import Excepciones.*;
import Gestiones.*;
import JSON.GestionJSON;
import Personas.Empleado;


import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Menu {



    public static void Menu() throws InterruptedException, IOException {
        ///VARIABLES PARA VALIDACIONES
        int opc;
        String opcionString;
       
        boolean salir = false;
        Scanner scanner = new Scanner(System.in);
        ///INSTANCIA DE CLASES
        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
        admin.cargarListaEmpleados();
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);


        almacenamientoAviones.generarAviones(15,admin.getListaEmpleados());


        // CREACIÓN DE VUELOS DE MANERA AUTOMÁTICA
        SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);
       // almacenamientoAviones.mostrarHangares();
        aeropuerto.cargarHangaresAeropuerto(admin.getListaEmpleados());
        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();

        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        while (!salir) {

            // Menú principal con opciones generales
            System.out.println("Bienvenido al sistema de Aeropuerto. Elige una opción:");
            System.out.println("1️⃣ Ingresar al menú de Clientes 👤");
            System.out.println("2️⃣ Ingresar al menú de Administración 👨‍💼");
            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:


                    int opcionReserva = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcionReserva) {



                        case 2:
                            String opcionConsulta = "";
                            String dni = "";
                            do {

                                try {
                                    System.out.println("Ingrese su DNI para mostrar su información de reserva:");
                                    dni = scanner.nextLine().trim();
                                    CheckIn.mostrarReserva(dni, sistemaReserva);
                                    System.out.println("desea consultar otra reserva? (s: si/ n: no");
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
                            try {

                                System.out.println("¿Desea consultar su boleto de avión? (s: sí / n: no)");
                                opcionString = scanner.nextLine().trim().toLowerCase();
                                if (opcionString.equals("s")) {
                                    System.out.println("Ingrese su numero de DNI");
                                    nroDni = scanner.nextLine().trim();
                                    CheckIn.generarBoleto(nroDni, sistemaReserva);
                                }
                                System.out.println("desea generar otro boleto de avion? (s: si/ n: no");
                                String generarBoleto = scanner.nextLine().trim().toLowerCase();

                                if (generarBoleto.equals("s")) {
                                    System.out.println("Ingrese su numero de DNI");
                                    nroDni = scanner.nextLine().trim();
                                    CheckIn.generarBoleto(nroDni, sistemaReserva);
                                }
                            } catch (ReservaInexistenteException e) {
                                e.printStackTrace();
                            }
                            break;



                    }




                    break;

                case 2:

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
                                    System.out.println("\u001B[31m5.\u001B[0m Asignar piloto a un avion");
                                    System.out.println("\u001B[31m6.\u001B[0m Mostrar la lista de administradores");
                                    System.out.println("\u001B[31m7.\u001B[0m Mostrar la lista de empleados");
                                    System.out.println("\u001B[31m8.\u001B[0m Cerrar sesión");
                                    System.out.print("\u001B[32m > \u001B[0m");
                                    opcionAdmin = scanner.nextInt();
                                    scanner.nextLine();

                                    switch (opcionAdmin) {
                                        case 1:
                                            admin.agregarAdministradorManual();
                                            break;
                                        case 2:
                                            admin.agregarPersonal();
                                            break;
                                        case 3:
                                            try {
                                                System.out.print("\u001B[32m > \u001B[0m");
                                                System.out.println("Ingrese el dni de la persona a eliminar");
                                                String dni = scanner.nextLine().trim();
                                                admin.eliminarPersonalPorDNI(dni);
                                            } catch (EmpleadoInexistenteException e) {
                                                System.out.println(e.getMessage());
                                                e.printStackTrace();
                                                break;
                                            }
                                            break;
                                        case 4:
                                            try{
                                                System.out.print("\u001B[32m > \u001B[0m");
                                                System.out.println("ingrese el dni de la persona a eliminar de la lista de administradores");
                                                String dniAdmin = scanner.nextLine().trim();
                                                admin.eliminarAdministradorDNI(dniAdmin);
                                            }catch (dniNoEncontradoException e){
                                                e.printStackTrace();
                                            }

                                            break;
                                        case 5:
                                            try{
                                                System.out.print("\u001B[32m > \u001B[0m");
                                                almacenamientoAviones.mostrarHangares();
                                                System.out.println("Ingrese el codigo de avion que desea asignarle un piloto");
                                                admin.asignarPilotoAvionPorID(scanner.nextLine(),almacenamientoAviones);
                                            }catch (CodigoVueloInexistenteException e){
                                                e.printStackTrace();
                                            }

                                            break;
                                        case 6:
                                            admin.mostrarListaEmpleados();
                                            break;
                                        case 7:
                                            admin.mostrarCuentasAdmin();
                                            break;
                                        case 8:
                                            System.out.println("\u001B[31mCerrando sesión...\u001B[0m");
                                            break;
                                    }

                                    // Preguntar si desea volver al menú principal de administrador
                                    if (opcionAdmin != 8) { // Si no seleccionó la opción 8 para cerrar sesión
                                        System.out.print("\u001B[31m¿Desea volver al menú principal? (sí/no): \u001B[0m");
                                        String respuesta = scanner.nextLine().trim().toLowerCase();
                                        if (!respuesta.equals("sí") && !respuesta.equals("si")) {
                                            break; // Sale del ciclo y termina el caso
                                        }
                                    }

                                } while (opcionAdmin != 8); // Repite el ciclo hasta que elija salir
                            }
                        } catch (AccesoDenegadoException e) {
                            e.printStackTrace();
                            break;
                        }
                        break;
                    } while (opcionAdmin < 8); // Sale del ciclo si la opción es 8 (Cerrar sesión)
                    /*String eleccion;
                    System.out.println("------------LISTA DE HANGARES------------");
                    almacenamientoAviones.mostrarHangares();

                    do {
                        try{
                            System.out.println("Ingrese el avión a retirar del hangar:");
                            almacenamientoAviones.eliminarAvionPorID(scanner.nextLine());
                        }catch (CodigoAvionNoExistenteException e){
                            e.printStackTrace();
                        }

                        System.out.println("¿Desea retirar otro avión? (s: sí / n: no)");
                        eleccion = scanner.nextLine();
                    } while (eleccion.equalsIgnoreCase("s"));

                    try {
                        almacenamientoAviones.mostrarHangares();
                        Avion nuevoAvion = new Avion("Boeing 737", 200, "Motor turbofan", "Modelo B737", "AV123456");
                        almacenamientoAviones.agregarAvionAlHangar(7, nuevoAvion);
                    } catch (HangarNoExistenteException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("¿Desea ver la lista modificada? (s: sí / n: no)");
                    eleccion = scanner.nextLine().trim().toLowerCase();
                    if (eleccion.equals("s")) {
                        almacenamientoAviones.mostrarHangares();
                    }
                    break;

                case 3:
                    PreEmbarque embarque = new PreEmbarque();
                    if (checkInCompletado) {
                        embarque.verificarSeguridad();
                    } else {
                        System.out.println("Primero debe completar el check-in.");
                    }*/
                    break;

                case 4:

                    break;



                case 5:


                    // Lógica para cerrar sesión y serializar los datos
                    System.out.println("Cerrando sesión...");
                    // Serializar los objetos utilizando Jackson

                    ///SERIALIZO EL SET DE AEROPUERTOS
                    Set<Aeropuerto> aeropuertos = SistemaAeropuerto.getListaAeropuertos();
                    GestionJSON.serializarSet(aeropuertos, "Archivos JSON/aeropuertos.json");
                    ///SERIALIZO LA LISTA DE AVIONES
                    List<Avion> aviones = almacenamientoAviones.obtenerAvionesDeTodosLosHangares();
                    GestionJSON.serializarLista(aviones, "Archivos JSON/aviones.json");
                     ///SERIALIZO EL SET DE EMPLEADOS
                    Set<Empleado> empleados = admin.getListaEmpleados();
                    GestionJSON.serializarSet(empleados, "Archivos JSON/empleados.json");
                     ///SERIALIZO LA LISTA DE VUELOS
                    List<Vuelo> vuelos = SistemaVuelo.getVuelos();
                    GestionJSON.serializarLista(vuelos, "Archivos JSON/vuelos.json");
                    ///SERIALIZO LA LISTA DE HANGARES
                    List<Hangar<Avion>> listaHangares = almacenamientoAviones.getListaHangares();
                    GestionJSON.serializarLista(listaHangares, "Archivos JSON/listaHangares.json");



                    salir = true;
                    break;


                default:
                    System.out.println("Opción inválida. Por favor, elija nuevamente.");
                    break;
            }

            if (!salir) {
                System.out.println("Presione Enter para volver al menú principal...");
                scanner.nextLine();
            }
        }
    }
}
