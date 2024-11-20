package UI;

import Aeropuerto.Aeropuerto;
import Gestiones.*;
import Utilidades.Utilities;


import java.io.IOException;
import java.util.Scanner;

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
        // CREACIÓN DE AVIONES DE MANERA AUTOMÁTICA
        AlmacenamientoAviones almacenamientoAviones = new AlmacenamientoAviones();
        almacenamientoAviones.generarHangares(7);
        almacenamientoAviones.generarAviones(15,admin.getListaEmpleados());

        // Crear el sistema de check-in

        // Crear el sistema de aeropuertos y registrar aeropuertos
        SistemaAeropuerto.cargarAeropuertos();

        while (!salir) {
            // Menú principal con opciones generales
            System.out.println("Bienvenido al sistema de Aeropuerto. Elige una opción:");
            System.out.println("1️⃣ Ingresar al menú de Clientes 👤");
            System.out.println("2️⃣ Ingresar al menú de Administración 👨‍💼");
            System.out.println("3️⃣ Salir 👋");



            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {
                case 1:
                       MenuClientes.mostrarMenuCliente();


                case 2:
                    MenuAdministracion.mostrarMenuAdministracion();
                    break;

                case 3:
                    Utilities.mostrarCerrando();
                    System.exit(0);


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
