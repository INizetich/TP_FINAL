
import GestionAeropuerto.SistemaAeropuerto;
import GestionAviones.Avion;
import GestionAviones.SistemaVuelo;
import GestionCheckIn.CheckIn;
import GestionCheckIn.SistemaCheckIn;
import GestionCheckIn.dniNoEncontradoException;
import java.util.Scanner;



        public class Main {

            public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);
                ///CREACION DE VUELOS DE MANERA AUTOMATICA
                SistemaVuelo.generarVuelosAutomaticos(15);

                // Crear el sistema de aeropuertos y registrar aeropuertos
                SistemaAeropuerto.cargarAeropuertos();


                // Crear una instancia de la clase avion
                Avion avion1 = new Avion("Boeing 747", 350, "Jet", "747X", "A001");
                Avion avion2 = new Avion("Airbus A320", 180, "Turbofan", "A320X", "A002");
                // Crear el sistema de check-in
                SistemaCheckIn sistemaCheckIn = new SistemaCheckIn();
                ///REGISTRO LOS AVIONES EN EL SISTEMA DE VUELO
                SistemaVuelo.registrarAvion(avion1);
                SistemaVuelo.registrarAvion(avion2);


              ///REALIZO EL CHECK IN DEL PASAJERO
                 sistemaCheckIn.realizarCheckIn();

                  ///INGRESO EL DNI ASOCIADO AL PASAJERO PARA MOSTRAR SU CHECK IN Y SU BOLETO DE AVION
                try{
                    System.out.println("ingrese su dni para mostrar su informacion de check in:");
                    sistemaCheckIn.mostrarInformacionCheckIn(scanner.nextLine());
                }catch (dniNoEncontradoException e){
                    System.out.println(e.getMessage());
                }


            }
        }






