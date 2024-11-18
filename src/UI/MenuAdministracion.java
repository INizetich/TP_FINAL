package UI;
import Aeropuerto.Aeropuerto;
import Config.ConfigAdmin;
import Excepciones.AccesoDenegadoException;
import Excepciones.CodigoVueloInexistenteException;
import Excepciones.EmpleadoInexistenteException;
import Excepciones.dniNoEncontradoException;
import Gestiones.*;
import JSON.GestionJSON;
import Personas.Persona;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class MenuAdministracion {

    public static void mostrarMenuAdministracion() {
        ConfigAdmin.cargarConfiguracionAdmin();
        // INSTANCIA DE CLASES IMPORTANTES
        Admin admin = new Admin();
        Aeropuerto aeropuerto = new Aeropuerto();
        admin.cargarListaEmpleados();
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
                                //Set<Persona> listaAdmins = admin.getListaAdministradores();
                                //GestionJSON.serializarSet(listaAdmins,"Archivos JSON/admins.json");
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

