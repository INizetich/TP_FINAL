package UI;

import Aviones.Avion;
import CheckIn.CheckIn;
import Excepciones.*;
import Gestiones.*;
import PreEmbarque.PreEmbarque;

import java.util.Scanner;

public class Menu {



    public static void Menu(){
        ///VARIABLES PARA VALIDACIONES

        int opc;
        String opcionString;
        boolean checkInCompletado = false;
        boolean salir = false;
        Scanner scanner = new Scanner(System.in);

        Admin admin = new Admin();
        admin.cargarListaEmpleados();
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);
        almacenamientoAviones.generarAviones(30);

        // CREACIÓN DE VUELOS DE MANERA AUTOMÁTICA
        SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);

        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();

        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        while (!salir) {
            System.out.print("************************************************************\n");
            System.out.print("*       Bienvenido al Aeropuerto de la Programación         *\n");
            System.out.print("************************************************************\n");
            System.out.print("*                       MENÚ PRINCIPAL                      *\n");
            System.out.print("************************************************************\n");
            System.out.print("*                                                           *\n");
            System.out.print("*  1. Sistema Reserva/Check-In                              *\n");
            System.out.print("*  2. Gestion Administrador                                 *\n");
            System.out.print("*  3. Información de Vuelos                                 *\n");
            System.out.print("*  4. Servicios de Comida y Bebidas                         *\n");
            System.out.print("*  5. Zona de Descanso                                      *\n");
            System.out.print("*  6. Verificar Seguridad                                   *\n");
            System.out.print("*  7. Salir                                                 *\n");
            System.out.print("*                                                           *\n");
            System.out.print("*                                                           *\n");
            System.out.print("************************************************************\n");
            System.out.print("*                        __|__                             *\n");
            System.out.print("*                 --@--@--(_)--@--@--                      *\n");
            System.out.print("************************************************************\n");
            System.out.print("Por favor, elige una opción: ");



            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:

                    System.out.println("BIENVENIDO AL SISTEMA DE RESERVA!");
                    System.out.println("Por favor. elija una opcion:");
                    System.out.println("1.realizar una reserva en un vuelo");
                    System.out.println("2.mostrar una reserva asociada al pasajero");
                    System.out.println("3.generar boleto de avion");
                    int opcionReserva = scanner.nextInt();
                    scanner.nextLine();

                    switch (opcionReserva) {
                        case 1:
                            do {
                                try {
                                    sistemaReserva.realizarReserva();
                                } catch (DniRegistradoException e) {
                                    e.printStackTrace();
                                } catch (CodigoVueloInexistenteException e) {
                                    e.printStackTrace();

                                } catch (AsientoNoDisponibleException e) {
                                    e.printStackTrace();
                                } finally {
                                    System.out.println("¿Desea hacer otra reserva? (s: sí / n: no)");
                                    opcionString = scanner.nextLine().trim().toLowerCase();
                                }
                            } while (opcionString.equals("s"));
                            System.out.println("Fin del proceso de reservas.");
                            checkInCompletado = true;
                            break;


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
                    String eleccion;
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
                    }
                    break;

                case 4:
                    int opcionAdmin = 0;
                    System.out.println("\u001B[32mIngrese su dni para loguearte\u001B[0m");
                    String loguin = scanner.nextLine().trim();
                    do {
                        try {
                            if (admin.comprobarLogin(loguin)) {
                                System.out.println("\u001B[31m----------------------BIENVENIDO AL SISTEMA DE ADMINISTRADOR----------------------\u001B[0m");
                                do {
                                    System.out.println("\u001B[31m1.\u001B[0m Dar privilegios de administrador a una persona");
                                    System.out.println("\u001B[31m2.\u001B[0m Agregar una persona a la lista de personal");
                                    System.out.println("\u001B[31m3.\u001B[0m Eliminar una persona de la lista de personal por DNI");
                                    System.out.println("\u001B[31m4.\u001B[0m Eliminar una persona de los privilegios de administrador por DNI");
                                    System.out.println("\u001B[31m5.\u001B[0m Asignar piloto a un vuelo");
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
                                                SistemaVuelo.mostrarVuelos();
                                                System.out.println("Ingrese el ID de vuelo que desea asignarle un piloto");
                                                admin.asignarPilotoAVueloPorID(scanner.nextLine());
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
                    break;



                case 5:
                    System.out.println("Saliendo del programa...");
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
