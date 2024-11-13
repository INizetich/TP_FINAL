package Gestiones;

import Enums.TipoEmpleado;
import Excepciones.AccesoDenegadoException;
import Excepciones.EmpleadoInexistenteException;
import Personas.Empleado;
import Personas.Persona;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Admin {
    Scanner scanner = new Scanner(System.in);
    private  Set<Persona> listaAdministradores;
    private  Set<Empleado> listaEmpleados;

    public Admin() {
        this.listaAdministradores = agregarAdministradores();
        this.listaEmpleados = new HashSet<>();
    }


    public  void agregarPersonal(){
        Empleado empleado = crearEmpleado();
        this.listaEmpleados.add(empleado);
    }

    public void agregarAdministradorManual(){
        Persona personaAdmin = crearCuentaAdmin();
        listaAdministradores.add(personaAdmin);
    }

    public void eliminarCuentaAdmin(){}



    public  boolean comprobarLogin(String dni) throws AccesoDenegadoException {
        boolean token = false;
        if (listaAdministradores.isEmpty()){
            System.out.println("no hay ninguna cuenta ADMIN.");
            return token;
        }else if (!listaAdministradores.isEmpty()){
                Persona persona = listaAdministradores.stream()
                        .filter(a->a.getDni().equalsIgnoreCase(dni))
                        .findFirst()
                        .orElse(null);

                if (persona == null){
                   throw new AccesoDenegadoException("Acceso denegado, Usted no cuenta con privilegios de administrador");

                }else {
                    token = true;
                }
        }
        return token;

    }


        public  void eliminarPersonalPorDNI(String dni) throws EmpleadoInexistenteException {

         if (listaEmpleados.isEmpty()){
             System.out.println("no hay personal en la lista de empleados");
             return;
         }else if (!listaEmpleados.isEmpty()){
             Empleado empleado = listaEmpleados.stream()
                     .filter(a -> a.getDni().equalsIgnoreCase(dni))
                     .findFirst()
                     .orElse(null);

             if (empleado != null){
                     listaEmpleados.remove(empleado);
                     System.out.println("empleado eliminado correctamente de la lista de personal");

             }else {
                 throw new EmpleadoInexistenteException("el empleado no existe en la lista de empleados");
             }


         }
    }



    public void eliminarAdministradorDNI(String dni){
        if (listaAdministradores.isEmpty()){
            System.out.println("no hay administradores en la lista de administradores");
        }else if (!listaAdministradores.isEmpty()) {
            Persona persona = listaAdministradores.stream()
                    .filter(a-> a.getDni().equalsIgnoreCase(dni))
                    .findFirst()
                    .orElse(null);

            if (persona == null){
                System.out.println("la persona no existe en la lista de administradores");
            }else {
                listaAdministradores.remove(persona);
                System.out.println("persona eliminado correctamente de la lista de administradores");
            }

        }
    }

    public void mostrarCuentasAdmin(){

        if (listaAdministradores.isEmpty()){
            System.out.println("la lista esta vacia");
            return;
        }else if (!listaAdministradores.isEmpty()) {
            for (Persona persona : listaAdministradores){
                System.out.println(persona);
            }
        }

    }

    public void mostrarListaEmpleados() {
        if (listaEmpleados.isEmpty()){
            System.out.println("la lista de empleados esta vacia");
            return;
        }else if (!listaEmpleados.isEmpty()) {
            for (Empleado empleado : listaEmpleados) {
                System.out.println(empleado);
            }
        }

    }

///-----------------------------------------METODOS PRIVATE-----------------------------------------
    private static Set<Persona> agregarAdministradores(){
        Set<Persona> administradores = new HashSet<>();
        administradores.add(new Persona("ignacio","nizetich",21,"45462201"));
        administradores.add(new Persona("lautaro","arschak",24,"42569299"));
        administradores.add(new Persona("rufino","figueroa",21,"44617380"));
        administradores.add(new Persona("arian","shaffer",20,"45576748"));

        return administradores;
    }

    private  Persona crearCuentaAdmin(){

        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.println("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Dni: ");
        String dni = scanner.nextLine();

        return new Persona(nombre, apellido, edad, dni);
    }

    private Empleado crearEmpleado(){
        System.out.println("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Apellido: ");
        String apellido = scanner.nextLine();
        System.out.println("Edad: ");
        int edad = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Dni: ");
        String dni = scanner.nextLine();
        System.out.println("Tipo de empleo");
        TipoEmpleado tipo = TipoEmpleado.valueOf(scanner.nextLine().toUpperCase());

        return new Empleado(nombre,apellido,edad,dni,tipo);
    }






}
