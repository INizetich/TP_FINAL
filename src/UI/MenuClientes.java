package UI;

import Aeropuerto.Aeropuerto;
import Excepciones.AsientoNoDisponibleException;
import Excepciones.CodigoVueloInexistenteException;
import Excepciones.DniRegistradoException;
import Gestiones.*;

import java.util.Scanner;

public class MenuClientes {

    public static void mostrarMenuCliente(){
        ///INSTANCIA DE CLASES IMPORTANTES
        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
        admin.cargarListaEmpleados();
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);
        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        almacenamientoAviones.generarAviones(15,admin.getListaEmpleados());

        // CREACIÓN DE VUELOS DE MANERA AUTOMÁTICA
        SistemaVuelo.generarVuelosDesdeHangares(15, almacenamientoAviones);
        // almacenamientoAviones.mostrarHangares();
        aeropuerto.cargarHangaresAeropuerto(admin.getListaEmpleados());
        // Crear el sistema de check-in
        SistemaReserva sistemaReserva = new SistemaReserva();



        Scanner scanner = new Scanner(System.in);
        int opcionCliente;
        System.out.println("\n======== Menú de Clientes ========");
        System.out.println("1️⃣ hacer una reserva 🛫");
        System.out.println("2️⃣ Ingresar a tiendas" + "\uD83D\uDED2");
        System.out.println("3️⃣ ATM" + "\uD83D\uDCB0");
        System.out.println("4️⃣ Salir 🚪");
        opcionCliente = scanner.nextInt();
        scanner.nextLine();


        switch(opcionCliente){
            case 1:
                System.out.println("🎉 ¡BIENVENIDO AL SISTEMA DE RESERVAS DE VUELOS! ✈️");
                System.out.println("=====================================================");
                System.out.println("Por favor, elija una opción:");
                System.out.println("1️⃣ Realizar una reserva en un vuelo 🛫");
                System.out.println("2️⃣ Mostrar una reserva asociada al pasajero 📋");
                System.out.println("3️⃣ Mostrar conexión del vuelo 🔗");
                System.out.println("4️⃣ Generar boleto de avión 🎟️");
                System.out.println("=====================================================");
                int opcionReserva = scanner.nextInt();
                scanner.nextLine();

                switch(opcionReserva){
                    case 1:
                        String opcionString = "";
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

                        break;

                }
                break;
        }
    }
}
