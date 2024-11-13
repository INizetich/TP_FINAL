import Excepciones.HangarNoExistenteException;
import GestionAviones.Avion;
import GestionPreEmbarque.PreEmbarque;
import Gestiones.SistemaAeropuerto;
import Gestiones.AlmacenamientoAviones;
import Gestiones.SistemaVuelo;
import Gestiones.SistemaCheckIn;
import Excepciones.dniNoEncontradoException;
import Excepciones.DniRegistradoException;
import java.util.Scanner;

        public class Main {
            public static void main(String[] args) {
                int opc;
                String opcion;
                String continuar;
                boolean checkInCompletado = false;
                boolean salir = false;

                Scanner scanner = new Scanner(System.in);
                AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
                almacenamientoAviones.generarHangares(7);
                almacenamientoAviones.generarAviones(30);

                ///CREACION DE VUELOS DE MANERA AUTOMATICA
                SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);

                // Crear el sistema de check-in
                SistemaCheckIn sistemaCheckIn = new SistemaCheckIn();

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
                                    sistemaCheckIn.realizarCheckIn();
                                } catch (DniRegistradoException e) {
                                    System.out.println(e.getMessage());
                                } finally {
                                    // Preguntar si desea realizar otro check-in
                                    System.out.println("¿Desea hacer otro check-in? (s/n)");
                                    opcion = scanner.nextLine().trim().toLowerCase();  // Asegurarse de usar nextLine()
                                }
                            } while (opcion.equals("s"));

                            System.out.println("Fin del proceso de check-in.");
                            checkInCompletado = true;

                            // Bucle para consultar check-ins usando el DNI
                            do {
                                try {
                                    System.out.println("Ingrese su DNI para mostrar su información de check-in:");
                                    String dni = scanner.nextLine().trim();
                                    sistemaCheckIn.mostrarInformacionCheckIn(dni);
                                } catch (DniRegistradoException e) {  // Usar la excepción adecuada
                                    System.out.println(e.getMessage());
                                } finally {
                                    // Preguntar si desea mostrar otro check-in
                                    System.out.println("¿Desea consultar otro check-in? (s/n)");
                                    opcion = scanner.nextLine().trim().toLowerCase();
                                }
                            } while (opcion.equals("s"));

                            System.out.println("Fin de la consulta de check-ins.");
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






