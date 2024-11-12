import GestionAeropuerto.SistemaAeropuerto;
import GestionAviones.Avion;
import GestionAviones.SistemaVuelo;
import GestionCheckIn.SistemaCheckIn;
import GestionCheckIn.dniNoEncontradoException;
import GestionCheckIn.DniRegistradoException;

import java.util.Scanner;



        public class Main {
            public static void main(String[] args) {
                String opcion;
                Scanner scanner = new Scanner(System.in);
                ///CREACION DE VUELOS DE MANERA AUTOMATICA
                SistemaVuelo.generarAvionesYVuelos(15);

                // Crear el sistema de check-in
                SistemaCheckIn sistemaCheckIn = new SistemaCheckIn();

                // Crear el sistema de aeropuertos y registrar aeropuertos
                SistemaAeropuerto.cargarAeropuertos();

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


            }
        }






