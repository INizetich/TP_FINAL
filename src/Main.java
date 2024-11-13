import CheckIn.CheckIn;
import Excepciones.*;
import Aviones.Avion;
import PreEmbarque.PreEmbarque;
import Gestiones.*;

import java.util.Scanner;

        public class Main {
            public static void main(String[] args) {
                int opc;
                String opcion;
                String continuar;
                boolean checkInCompletado = false;
                boolean salir = false;

                ///asasASSADA
                ///tucu gil

                Scanner scanner = new Scanner(System.in);
                Admin admin = new Admin();
                AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
                almacenamientoAviones.generarHangares(7);
                almacenamientoAviones.generarAviones(30);

                ///CREACION DE VUELOS DE MANERA AUTOMATICA
                SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);

                // Crear el sistema de check-in
                SistemaReserva sistemaReserva = new SistemaReserva();

                // Crear el sistema de aeropuertos y registrar aeropuertos
                SistemaAeropuerto.cargarAeropuertos();
                while (!salir) {
                System.out.println("elija una opcion");
                System.out.println("1. check in");
                System.out.println("2.sistema de hangares");
                System.out.println("3. Verificar seguridad");
                System.out.println("4. Salir");
                opc = scanner.nextInt();
                scanner.nextLine();

                    switch (opc) {
                        case 1:

                            do {
                                try {
                                    // Realizar el check-in del pasajero
                                    sistemaReserva.relizarReserva();
                                } catch (DniRegistradoException e) {
                                    System.out.println(e.getMessage());
                                } finally {
                                    // Preguntar si desea realizar otro check-in
                                    System.out.println("¿Desea hacer otra reserva? (s: si/n: no)");
                                    opcion = scanner.nextLine().trim().toLowerCase();  // Asegurarse de usar nextLine()
                                }
                            } while (opcion.equals("s"));

                            System.out.println("Fin del proceso de reservas.");
                            checkInCompletado = true;

                            // Bucle para consultar check-ins usando el DNI
                            do {
                                String dni = "";
                                try {
                                    System.out.println("Ingrese su DNI para mostrar su información de reserva:");
                                    dni = scanner.nextLine().trim();
                                    CheckIn.mostrarReserva(dni,sistemaReserva);
                                } catch (DniRegistradoException e) {  // Usar la excepción adecuada
                                    System.out.println(e.getMessage());
                                } finally {
                                    // Preguntar si desea mostrar otro check-in
                                    System.out.println("¿Desea consultar su boleto de avion? (s/n)");
                                    opcion = scanner.nextLine().trim().toLowerCase();

                                    if (opcion.equals("s")){
                                        CheckIn.generarBoleto(dni,sistemaReserva);
                                        break;
                                    }
                                }
                            } while (opcion.equals("s"));

                            System.out.println("Fin de la consulta de reservas.");
                            break;
                    case 2:
                        String eleccion = "";

                        System.out.println("------------LISTA DE HANGARES------------");
                        almacenamientoAviones.mostrarHangares();
                        do {
                         System.out.println("ingrese el avion a eliminar");
                        almacenamientoAviones.eliminarAvionPorID(scanner.nextLine());
                          System.out.println("desea retirar otro avion?");
                            eleccion = scanner.nextLine();
                        } while (eleccion.equalsIgnoreCase("s"));



                        try {
                            almacenamientoAviones.mostrarHangares();
                            Avion nuevoAvion = new Avion("Boeing 737", 200, "Motor turbofan", "Modelo B737", "AV123456");
                            almacenamientoAviones.agregarAvionAlHangar(7, nuevoAvion);




                        } catch (HangarNoExistenteException e) {
                            System.out.println(e.getMessage());
                        }
                            System.out.println("desea ver la lista modificada? (s: si / n: no");
                        eleccion = scanner.nextLine().trim().toLowerCase();
                            if(eleccion.equals("s")){
                                almacenamientoAviones.mostrarHangares();
                            }

                        break;

                    case 3:

                        PreEmbarque embarque = new PreEmbarque();
                        if(checkInCompletado = true) {
                            embarque.verificarSeguridad();
                        }
                        break;

                    case 4:
                        System.out.println("Saliendo del programa...");
                        salir = true;
                        break;


                        case 5:
                            int opcionAdmin = 0;
                            System.out.println("Ingrese su dni para loguearte");
                            String loguin = scanner.nextLine().trim();
                            do{
                                 try{
                                     if(admin.comprobarLogin(loguin)) {
                                         System.out.println("BIENVENIDO AL SISTEMA DE ADMINISTRADOR");
                                         System.out.println("Por favor, elija una opcion: ");
                                         System.out.println("1.dar privilegios de administrador a una persona");
                                         System.out.println("2.agregar una persona a la lista de personal");
                                         System.out.println("3.eliminar una persona de la lista de personal por DNI");
                                         System.out.println("4.eliminar una persona los privilegios de administrador por DNI");
                                         System.out.println("5.mostrar la lista de empleados");
                                         System.out.println("6.mostrar la lista de administradores");
                                         System.out.println("7.cerrar sesion");
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
                                                 try{
                                                     System.out.println("Ingrese el dni de la persona a eliminar");
                                                     String dni = scanner.nextLine().trim();
                                                     admin.eliminarPersonalPorDNI(dni);
                                                 }catch (EmpleadoInexistenteException e){
                                                     System.out.println(e.getMessage());
                                                     e.printStackTrace();
                                                     break;
                                                 }

                                                 break;

                                             case 4:
                                                 System.out.println("ingrese el dni de la persona a eliminar de la lista de administradores");
                                                 String dniAdmin = scanner.nextLine().trim();
                                                 admin.eliminarAdministradorDNI(dniAdmin);

                                                 break;

                                             case 5:
                                                 admin.mostrarListaEmpleados();
                                                 break;

                                             case 6:
                                                 admin.mostrarCuentasAdmin();
                                                 break;

                                             case 7:
                                                 System.out.println("Saliendo del programa...");
                                                 System.exit(0);
                                                 break;
                                         }
                                     }
                                 }catch (AccesoDenegadoException e){

                                     e.printStackTrace();
                                     break;
                                 }




                            }while (opcionAdmin < 8);

                            break;

                        default:
                            System.out.println("Opción inválida. Por favor, elija nuevamente.");
                            break;
                }
                    if(!salir){
                        System.out.println("Presione enter para volver al menu principal :) ");
                        scanner.nextLine();
                    }
            }
            }
        }






