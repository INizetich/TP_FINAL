import GestionAeropuerto.SistemaAeropuerto;
import GestionAviones.AlmacenamientoAviones;
import GestionAviones.SistemaVuelo;
import GestionCheckIn.SistemaCheckIn;
import Excepciones.dniNoEncontradoException;
import Excepciones.DniRegistradoException;

import java.util.Scanner;



        public class Main {
            public static void main(String[] args) {
                int opc;
                String opcion;

                Scanner scanner = new Scanner(System.in);
                AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
                almacenamientoAviones.generarHangares(7);
                almacenamientoAviones.generarAviones(30);

                 ///lauty puto
                ///Nize puto
                ///lauty gay

                ///tucu trolo


                ///CREACION DE VUELOS DE MANERA AUTOMATICA
                SistemaVuelo.generarVuelosDesdeHangares(15,almacenamientoAviones);

                // Crear el sistema de check-in
                SistemaCheckIn sistemaCheckIn = new SistemaCheckIn();

                // Crear el sistema de aeropuertos y registrar aeropuertos
                SistemaAeropuerto.cargarAeropuertos();

                System.out.println("elija una opcion");
                System.out.println("1. check in");
                System.out.println("2.sistema de hangares");
                opc = scanner.nextInt();
                scanner.nextLine();
                switch (opc){
                    case 1:
                        System.out.println("LISTA DE AVIONES EN HANGARES");
                        almacenamientoAviones.mostrarHangares();
                        do {
                            try {
                                // Realizar el check-in del pasajero
                                sistemaCheckIn.realizarCheckIn();
                            } catch (DniRegistradoException e) {
                                System.out.println(e.getMessage());
                            } finally {
                                // Preguntar si desea realizar otro check-in
                                System.out.println("¿Desea hacer otro check-in? (s/n)");
                                opcion = scanner.next().trim().toLowerCase(); // Convertir a minúsculas y eliminar espacios en blanco
                            }
                        } while (opcion.equals("s"));

                        System.out.println("Fin del proceso de check-in.");

                        ///INGRESO EL DNI ASOCIADO AL PASAJERO PARA MOSTRAR SU CHECK IN Y SU BOLETO DE AVION
                        try{
                            scanner.nextLine();
                            System.out.println("ingrese su dni para mostrar su informacion de check in:");
                            String dni = scanner.nextLine();
                            sistemaCheckIn.mostrarInformacionCheckIn(dni);
                        }catch (dniNoEncontradoException e){
                            System.out.println(e.getMessage());
                        }

                        break;

                    case 2:
                        String avion;

                        System.out.println("------------LISTA DE HANGARES------------");
                        almacenamientoAviones.mostrarHangares();
                        do {
                            System.out.println("ingrese el avion a eliminar");
                            almacenamientoAviones.eliminarAvionPorID(scanner.nextLine());
                            System.out.println("desea eliminar otro avion?");
                         avion = scanner.nextLine();
                        }while(avion.equalsIgnoreCase("s"));


                        almacenamientoAviones.mostrarHangares();


                        break;
                }




            }
        }






