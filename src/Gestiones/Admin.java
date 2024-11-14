package Gestiones;

import Aviones.Avion;
import Aviones.Vuelo;
import Enums.TipoEmpleado;
import Excepciones.*;
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



    public void eliminarAdministradorDNI(String dni) throws dniNoEncontradoException {
        if (listaAdministradores.isEmpty()){
            System.out.println("no hay administradores en la lista de administradores");
        }else if (!listaAdministradores.isEmpty()) {

            Persona persona = listaAdministradores.stream()
                    .filter(a-> a.getDni().equalsIgnoreCase(dni))
                    .findFirst()
                    .orElse(null);

            if (persona == null){
              throw new  dniNoEncontradoException("El dni no se encuentra registrado en ninguna cuenta admin.");
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

    public void cargarListaEmpleados(){
        this.listaEmpleados = agregarPersonas();
    }


    public void asignarPilotoAVueloPorID(String idVuelo) throws CodigoVueloInexistenteException {
        // Buscar vuelo por ID
        Vuelo vueloSeleccionado = SistemaVuelo.getVuelos().stream()
                .filter(vuelo -> vuelo.getIdVuelo().equalsIgnoreCase(idVuelo))
                .findFirst()
                .orElse(null);

        if (vueloSeleccionado == null) {
            throw new CodigoVueloInexistenteException("Error, no existe un vuelo con ese codigo");

        }

        // Filtrar pilotos disponibles
        List<Empleado> pilotosDisponibles = new ArrayList<>();
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getTipoEmpleado() == TipoEmpleado.PILOTO) {
                pilotosDisponibles.add(empleado);
            }
        }

        if (pilotosDisponibles.isEmpty()) {
            System.out.println("No hay pilotos disponibles.");
            return;
        }

        // Mostrar pilotos disponibles
        System.out.println("Pilotos disponibles:");
        for (Empleado piloto : pilotosDisponibles) {
            System.out.println(piloto);
        }

        // Solicitar código de piloto
        System.out.print("Ingrese el código del piloto: ");
        if (scanner.hasNextInt()) { // Validar entrada del usuario
            int codigoPiloto = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            // Buscar piloto por código
            Empleado pilotoSeleccionado = pilotosDisponibles.stream()
                    .filter(piloto -> piloto.getNroEmpleado() == codigoPiloto)
                    .findFirst()
                    .orElse(null);

            if (pilotoSeleccionado != null) {
                try {
                    vueloSeleccionado.asignarPiloto(pilotoSeleccionado);
                }catch (NoEsPilotoException e){
                    e.printStackTrace();
                }
             // Asignar piloto al vuelo
            } else {
                System.out.println("No se encontró un piloto con el código proporcionado.");
            }
        } else {
            System.out.println("Por favor, ingrese un código numérico válido.");
            scanner.nextLine(); // Consumir entrada inválida
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


//String nombre, String apellido, int edad,String dni, TipoEmpleado tipoEmpleado


    private static Set<Empleado> agregarPersonas(){
        Set<Empleado> personas = new HashSet<>();

        personas.add(new Empleado("martin","ledesma",36,"28102912",TipoEmpleado.PILOTO));
        personas.add(new Empleado("julian","sanchez",45,"25192182",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("martina","suarez",26,"41019285",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("santiago","valero",38,"32105812",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("julieta","canale",58,"21581057",TipoEmpleado.PILOTO));
        personas.add(new Empleado("nahuel","pacheco",25,"46690655",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("crystal","campodonico",22,"92105375",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("micaela","ibañez",35,"31571920",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("cristian","chiliguay",49,"34581039",TipoEmpleado.PILOTO));

        return personas;
    }


}
