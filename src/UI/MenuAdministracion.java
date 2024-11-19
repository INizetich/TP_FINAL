package UI;
import Aeropuerto.Aeropuerto;
import Config.ConfigAdmin;
import Excepciones.AccesoDenegadoException;
import Excepciones.CodigoVueloInexistenteException;
import Excepciones.EmpleadoInexistenteException;
import Excepciones.dniNoEncontradoException;
import Gestiones.*;
import Utilidades.Utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuAdministracion {

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
                        System.out.println("\u001B[31m5.\u001B[0m Asignar piloto a un avion");
                        System.out.println("\u001B[31m6.\u001B[0m Mostrar la lista de administradores");
                        System.out.println("\u001B[31m7.\u001B[0m Mostrar la lista de empleados");
                        System.out.println("\u001B[31m8.\u001B[0m Cerrar sesión");
                        System.out.print("\u001B[32m > \u001B[0m");
                        opcionAdmin = scanner.nextInt();
                        scanner.nextLine();

                        switch (opcionAdmin) {
                            case 1:
                                try{
                                    admin.agregarAdministradorManual();
                                }catch (InputMismatchException e){
                                    e.printStackTrace();
                                }

                                break;
                            case 2:
                                try{
                                    admin.agregarPersonal();
                                }catch (InputMismatchException e){
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
                                try{
                                    // Mostramos la lista de empleados después de deserializar
                                    System.out.println("💼 Lista de administradores actualizada:");
                                   admin.mostrarCuentasAdmin();
                                    System.out.print("\u001B[32m > \u001B[0m");
                                    System.out.print("🔑 Ingresa el DNI del administrador a eliminar: ");
                                    String dniAdmin = scanner.nextLine();
                                    admin.eliminarAdministradorDNI(dniAdmin);
                                }catch (dniNoEncontradoException e){
                                    e.printStackTrace();
                                }

                                break;
                            case 5:

                                 try{
                                     SistemaVuelo.mostrarVuelos();
                                     System.out.println("Ingrese el ID de vuelo a eliminar");
                                     String idVuelo = scanner.nextLine();
                                     admin.eliminarVueloPorID(idVuelo);
                                 }catch (CodigoVueloInexistenteException e){
                                     e.printStackTrace();
                                 }

                                break;
                            case 6:
                                System.out.print("\u001B[32m > \u001B[0m");
                                System.out.println("\n================================================");
                                System.out.println("✨✨ ¿Desea mostrar la lista de empleados? ✨✨");
                                System.out.println("================================================");
                                System.out.println("👉 Opción 1: Mostrar lista de empleados");
                                System.out.println("❌ Opción 2: No mostrar lista de empleados");
                                System.out.println("================================================\n");

                                String listaEm = scanner.nextLine();

                                if(listaEm.equalsIgnoreCase("s")){
                                    admin.mostrarListaEmpleados();
                                }else {
                                    return;
                                }

                                break;
                            case 7:
                                System.out.print("\u001B[32m > \u001B[0m");
                                System.out.println("\n================================================");
                                System.out.println("📋 ¿Te gustaría ver la lista de administradores? 📋");
                                System.out.println("================================================");
                                System.out.println("\u001B[32m✅ Opción 1: Ver la lista de administradores\u001B[0m");
                                System.out.println("\u001B[31m❌ Opción 2: No ver la lista de administradores\u001B[0m");
                                System.out.println("================================================\n");

                                admin.mostrarCuentasAdmin();
                                break;
                            case 8:
                                Utilities.mostrarCerrandoAdmin();
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            break;
        } while (opcionAdmin < 8);


    }
}

