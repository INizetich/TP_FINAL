package Gestiones;

import Aviones.Vuelo;
import Config.ConfigAdmin;
import Enums.TipoEmpleado;
import Excepciones.*;

import JSON.GestionJSON;
import Personas.Empleado;
import Personas.Persona;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Admin {
    Scanner scanner = new Scanner(System.in);
    @JsonProperty("admins")
    private  Set<Persona> listaAdministradores;
    @JsonProperty("empleados")
    private  Set<Empleado> listaEmpleados;


    public Admin() {
        this.listaAdministradores = agregarAdministradoresStandard();
        this.listaEmpleados = agregarPersonas();

    }


    public Set<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(Set<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public Set<Persona> getListaAdministradores() {
        return listaAdministradores;
    }

    public void setListaAdministradores(Set<Persona> listaAdministradores) {
        this.listaAdministradores = listaAdministradores;
    }

    public  void agregarPersonal() throws InputMismatchException{
        boolean seguir = true;
        if (ConfigAdmin.isFirstRunAdmin()) {
            File personal = new File("Archivos JSON/empleados.json");

            if (personal.exists()) {
                Set<Empleado> empleadosJSON = null;
                try {
                    // Deserializamos el archivo de administradores
                    empleadosJSON = GestionJSON.deserializarSet(Empleado.class, personal.getPath());
                    if (empleadosJSON.isEmpty()) {
                        System.out.println("🚫 No se encontraron empleados deserializados.");
                    } else {
                        setListaEmpleados(empleadosJSON); // Establecemos la lista de administradores
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("🚫 El archivo de empleados no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    personal.createNewFile();
                    System.out.println("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    System.out.println("🚫 Error al crear el archivo de administradores.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            while (seguir) {
                // Crear una nueva cuenta de administrador
                Empleado empleado = crearEmpleado();
                listaEmpleados.add(empleado);

                // Serializamos la lista de empleados nuevamente en el archivo JSON
                try {
                    GestionJSON.serializarSet(listaEmpleados, personal.getPath());
                    System.out.println("✔️ empleado/s agregado/s y archivo actualizado.");
                } catch (Exception e) {
                    System.out.println("🚫 Error al serializar el archivo de empleados.");
                    e.printStackTrace();
                }

                System.out.println("¿Desea agregar otra persona? (s/n)");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("n")) {
                    seguir = false; // Salir del bucle
                }
            }
        } else {
            System.out.println("🚫 El sistema está en su primera ejecución, no se pueden agregar administradores.");
        }
    }



    public void agregarAdministradorManual() throws InputMismatchException {
        boolean seguir = true;
        if (ConfigAdmin.isFirstRunAdmin()) {
            File admins = new File("Archivos JSON/admins.json");

            if (admins.exists()) {
                Set<Persona> adminsJSON = null;
                try {
                    // Deserializamos el archivo de administradores
                    adminsJSON = GestionJSON.deserializarSet(Persona.class, admins.getPath());
                    if (adminsJSON.isEmpty()) {
                        System.out.println("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(adminsJSON); // Establecemos la lista de administradores
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("🚫 El archivo de administradores no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    admins.createNewFile();
                    System.out.println("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    System.out.println("🚫 Error al crear el archivo de administradores.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            while (seguir) {
                // Crear una nueva cuenta de administrador
                Persona administrador = crearCuentaAdmin();
                listaAdministradores.add(administrador);

                // Serializamos la lista de administradores nuevamente en el archivo JSON
                try {
                    GestionJSON.serializarSet(listaAdministradores, admins.getPath());
                    System.out.println("✔️ Administrador agregado y archivo actualizado.");
                } catch (Exception e) {
                    System.out.println("🚫 Error al serializar el archivo de administradores.");
                    e.printStackTrace();
                }

                System.out.println("¿Desea crear otra cuenta de administrador? (s/n)");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("n")) {
                    seguir = false; // Salir del bucle
                }
            }
        } else {
            System.out.println("🚫 El sistema está en su primera ejecución, no se pueden agregar administradores.");
        }
    }





    public  boolean comprobarLogin(String dni) throws AccesoDenegadoException {
boolean token = false;
        if (ConfigAdmin.isFirstRunAdmin()) {
            File loguin = new File("Archivos JSON/admins.json");

            if (loguin.exists()) {
                Set<Persona> loguinPersonas = null;
                try {
                    // Deserializamos el archivo de administradores
                    loguinPersonas = GestionJSON.deserializarSet(Persona.class, loguin.getPath());
                    if (loguinPersonas.isEmpty()) {
                        System.out.println("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(loguinPersonas); // Establecemos la lista de administradores
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("🚫 El archivo de administradores no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    loguin.createNewFile();
                    System.out.println("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    System.out.println("🚫 Error al crear el archivo de administradores.");
                    e.printStackTrace();
                    return token;// Salir si no se puede crear el archivo
                }
            }
            Persona persona = listaAdministradores.stream()
                    .filter(a -> a.getDni().equalsIgnoreCase(dni))
                    .findFirst()
                    .orElse(null);

            if (persona == null) {
                throw new AccesoDenegadoException("🚫 Error: usted no cuenta con privilegios de administrador");
            }else {
                token = true;
            }

        }
        return token;
    }












    public void eliminarPersonalPorDNI() throws EmpleadoInexistenteException {
        if (ConfigAdmin.isFirstRunAdmin()) {
            File personalEliminado = new File("Archivos JSON/empleados.json");

            // Verificamos si el archivo existe
            if (personalEliminado.exists()) {
                Set<Empleado> empleadosAEliminar = null;
                try {
                    // Deserializamos el conjunto de empleados desde el archivo JSON
                    empleadosAEliminar = GestionJSON.deserializarSet(Empleado.class, personalEliminado.getPath());

                    // Verificamos si la lista está vacía
                    if (empleadosAEliminar.isEmpty()) {
                        System.out.println("🚫 No se encontraron empleados deserializados.");
                    } else {
                        // Actualizamos la lista de empleados
                        setListaEmpleados(empleadosAEliminar);

                        // Mostramos la lista de empleados después de deserializar
                        System.out.println("💼 Lista de empleados actualizada:");
                        mostrarListaEmpleados();
                    }
                } catch (JSONException e) {
                    System.out.println("🚫 Error al deserializar los empleados.");
                    e.printStackTrace();
                    return;
                }
            } else {
                // Si el archivo no existe, lo creamos vacío
                System.out.println("🚫 El archivo de empleados no existe. Creando archivo vacío...");
                try {
                    if (personalEliminado.createNewFile()) {
                        System.out.println("✔️ Archivo de empleados creado.");
                    } else {
                        System.out.println("🚫 No se pudo crear el archivo de empleados.");
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("🚫 Error al crear el archivo de empleados.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            // Pedimos el DNI del empleado a eliminar
            Scanner scanner = new Scanner(System.in);
            System.out.print("\u001B[32m > \u001B[0m");
            System.out.print("🔑 Ingresa el DNI del empleado a eliminar: ");
            String dni = scanner.nextLine();

            // Intentamos eliminar al empleado
            Empleado empleado = listaEmpleados.stream()
                    .filter(a -> a.getDni().equalsIgnoreCase(dni))
                    .findFirst()
                    .orElse(null);

            if (empleado == null) {
                throw new EmpleadoInexistenteException("🚫 Error: El empleado con DNI " + dni + " no existe.");
            } else {
                listaEmpleados.remove(empleado);
                System.out.println("✔️ Empleado eliminado correctamente de la lista.");
            }

            // Serializamos la lista de empleados nuevamente en el archivo JSON
            try {
                GestionJSON.serializarSet(listaEmpleados, personalEliminado.getPath());
                System.out.println("✔️ Archivo de empleados actualizado.");
            } catch (Exception e) {
                System.out.println("🚫 Error al serializar el archivo de empleados.");
                e.printStackTrace();
                return;
            }
        }
    }








    public void eliminarAdministradorDNI(String dni) throws dniNoEncontradoException {
        if (ConfigAdmin.isFirstRunAdmin()) {
            File eliminarAdmin = new File("Archivos JSON/admins.json");

            // Verificar si el archivo existe y deserializar los administradores
            if (eliminarAdmin.exists()) {
                Set<Persona> adminAeliminar = null;
                try {
                    adminAeliminar = GestionJSON.deserializarSet(Persona.class, eliminarAdmin.getPath());
                    if (adminAeliminar.isEmpty()) {
                        System.out.println("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(adminAeliminar);
                    }
                } catch (JSONException e) {
                    System.out.println("🚫 Error al deserializar el archivo de administradores.");
                    e.printStackTrace();
                }
            } else {
                // Si el archivo no existe, lo creamos vacío
                System.out.println("🚫 El archivo de administradores no existe. Creando archivo vacío...");
                try {
                    if (eliminarAdmin.createNewFile()) {
                        System.out.println("✔️ Archivo de administradores creado.");
                    } else {
                        System.out.println("🚫 No se pudo crear el archivo de administradores.");
                        return;
                    }
                } catch (IOException e) {
                    System.out.println("🚫 Error al crear el archivo de administradores.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            // Verificar si la lista de administradores no está vacía
            if (listaAdministradores.isEmpty()) {
                System.out.println("🚫 No hay administradores en la lista de administradores.");
            } else {
                // Buscar el administrador por DNI y eliminarlo
                Persona persona = listaAdministradores.stream()
                        .filter(a -> a.getDni().equalsIgnoreCase(dni))
                        .findFirst()
                        .orElse(null);

                if (persona == null) {
                    throw new dniNoEncontradoException("\uD83D\uDEAB" + " Error: El administrador con DNI \"" + dni + "\" no existe.");
                } else {
                    listaAdministradores.remove(persona);
                    System.out.println("✔️ Administrador eliminado correctamente de la lista.");
                }
            }

            // Serializar de nuevo la lista de administradores al archivo JSON
            try {
                GestionJSON.serializarSet(listaAdministradores, "Archivos JSON/admins.json");
                System.out.println("✔️ Archivo de administradores actualizado.");
            } catch (JSONException e) {
                System.out.println("🚫 Error al serializar el archivo de administradores.");
                e.printStackTrace();
            }
        }
    }




    public void mostrarCuentasAdmin() {
        if (listaAdministradores.isEmpty()) {
            System.out.println("🚨 La lista de administradores está vacía.");
            return;
        }

        System.out.println("======== Lista de Administradores ========");
        System.out.printf("%-15s %-20s %-20s %-10s%n", "DNI", "Nombre", "Apellido", "Edad"); // Encabezados de columnas
        System.out.println("==========================================="); // Separador visual

        // Imprimir cada persona de la lista con un formato bonito
        for (Persona persona : listaAdministradores) {
            System.out.printf("%-15s %-20s %-20s %-10d%n",
                    persona.getDni(),            // Mostrar el DNI
                    persona.getNombre(),         // Nombre
                    persona.getApellido(),       // Apellido
                    persona.getEdad());          // Edad
        }
    }


    public void mostrarListaEmpleados() {
        if (listaEmpleados.isEmpty()) {
            System.out.println("🚫 La lista de empleados está vacía.");
            return;
        }

        // Cabecera
        System.out.println("╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                       Lista de Empleados                          ║");
        System.out.println("╠════════════════════╦════════════════════╦════════════════════╦════════════════════╗");
        System.out.println("║      DNI           ║     Nombre         ║     Apellido       ║      Cargo          ║");
        System.out.println("╠════════════════════╬════════════════════╬════════════════════╬════════════════════╣");

        // Mostrar cada empleado
        for (Empleado empleado : listaEmpleados) {
            // Suponiendo que la clase Empleado tiene estos métodos getDni(), getNombre(), getApellido(), getCargo()
            System.out.printf("║ %-18s ║ %-18s ║ %-18s ║ %-18s ║\n",
                    empleado.getDni(),
                    empleado.getNombre(),
                    empleado.getApellido(),
                    empleado.getTipoEmpleado());
        }

        System.out.println("╚════════════════════╩════════════════════╩════════════════════╩════════════════════╝");
    }

public  void eliminarVueloPorID(String idVuelo) throws CodigoVueloInexistenteException {
    if (ConfigAdmin.isFirstRunAdmin()) {
        File eliminarVuelo = new File("Archivos JSON/vuelos.json");

        // Verificar si el archivo existe y deserializar los administradores
        if (eliminarVuelo.exists()) {
            List<Vuelo> vuelosJSON = null;
            try {
                vuelosJSON = GestionJSON.deserializarLista(Vuelo.class, eliminarVuelo.getPath());
                if (vuelosJSON.isEmpty()) {
                    System.out.println("🚫 No se encontraron vuelos deserializados.");
                } else {
                    SistemaVuelo.setVuelosGenerados(vuelosJSON);
                }
            } catch (JSONException e) {
                System.out.println("🚫 Error al deserializar el archivo de vuelos.");
                e.printStackTrace();
            }
        } else {
            // Si el archivo no existe, lo creamos vacío
            System.out.println("🚫 El archivo de vuelos no existe. Creando archivo vacío...");
            try {
                if (eliminarVuelo.createNewFile()) {
                    System.out.println("✔️ Archivo de vuelos creado.");
                } else {
                    System.out.println("🚫 No se pudo crear el archivo de vuelos.");
                    return;
                }
            } catch (IOException e) {
                System.out.println("🚫 Error al crear el archivo de vuelos.");
                e.printStackTrace();
                return; // Salir si no se puede crear el archivo
            }
        }

        Vuelo vuelo = SistemaVuelo.getVuelosGenerados().stream()
                .filter(a -> a.getIdVuelo().equalsIgnoreCase(idVuelo))
                .findFirst()
                .orElse(null);

        if (vuelo == null) {
            throw new CodigoVueloInexistenteException("🚫 Error: no existe ningun vuelo con ese codigo.");
        }

        if(!vuelo.getListaPasajeros().isEmpty()) {
            System.out.println("🚫 Error: no se puede eliminar el vuelo porque tiene reservas hechas");
            return;
        }

        SistemaVuelo.getVuelosGenerados().remove(vuelo);
        System.out.println("✅ Vuelo con ID " + idVuelo + " eliminado exitosamente.");


        try {
            List<Vuelo> vuelosAserializar = SistemaVuelo.getVuelosGenerados();
            GestionJSON.serializarLista(vuelosAserializar, "Archivos JSON/vuelos.json");
            System.out.println("✔️ Archivo de vuelos actualizado.");
        } catch (JSONException e) {
            System.out.println("🚫 Error al serializar el archivo de vuelos.");
            e.printStackTrace();
        }




    }

}






    ///-----------------------------------------METODOS PRIVATE-----------------------------------------///
    private static Set<Persona> agregarAdministradoresStandard(){
        Set<Persona> administradores = new HashSet<>();
        administradores.add(new Persona("ignacio","nizetich",21,"45462201"));
        administradores.add(new Persona("lautaro","arschak",24,"42569299"));
        administradores.add(new Persona("rufino","figueroa",21,"44617380"));
        administradores.add(new Persona("arian","shaffer",20,"45576748"));

        return administradores;
    }



    private Persona crearCuentaAdmin() {
        String nombre, apellido, dni;
        int edad;

        // Validar nombre
        do {
            System.out.println("Nombre: ");
            nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                System.out.println("🚨 El nombre no puede estar vacío. Por favor, ingrésalo nuevamente.");
            }
        } while (nombre.trim().isEmpty());

        // Validar apellido
        do {
            System.out.println("Apellido: ");
            apellido = scanner.nextLine();
            if (apellido.trim().isEmpty()) {
                System.out.println("🚨 El apellido no puede estar vacío. Por favor, ingrésalo nuevamente.");
            }
        } while (apellido.trim().isEmpty());

        // Validar edad
        do {
            System.out.println("Edad: ");
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            if (edad <= 0 || edad >= 110) {
                System.out.println("🚨 La edad debe ser mayor que 0 y menor que 110. Intenta nuevamente.");
            }
        } while (edad <= 0 || edad >= 110);

        // Validar DNI
        do {
            System.out.println("Dni (8 caracteres): ");
            dni = scanner.nextLine();
            if (dni.length() != 8) {
                System.out.println("🚨 El DNI debe tener exactamente 8 caracteres. Intenta nuevamente.");
            } else if (!dni.matches("\\d+")) { // Verifica que el DNI contenga solo números
                System.out.println("🚨 El DNI debe contener solo números. Intenta nuevamente.");
            }
        } while (dni.length() != 8 || !dni.matches("\\d+"));

        // Crear y retornar la cuenta admin
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
        personas.add(new Empleado("rodolfo","rodriguez",65,"59104958",TipoEmpleado.PILOTO));
        personas.add(new Empleado("gabriel","ruiz",43,"28454151",TipoEmpleado.PILOTO));
        personas.add(new Empleado("jose","calvo",35,"30195815",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("valentina","martinez",25,"41958571",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("lucila","fernandez",47,"25317232",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("sasha","rodiguez",22,"44591481",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("ignacio","nizetich",21,"45462201",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("nicolas","roskoczy",25,"41716501",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("gustavo","roskoczy",51,"27105918",TipoEmpleado.PILOTO));
        personas.add(new Empleado("gerardo","rubio",51,"26104958",TipoEmpleado.COPILOTO));
        personas.add(new Empleado("carmela","garcia",18,"47195019",TipoEmpleado.AZAFATA));
        personas.add(new Empleado("rufino","figueroa",22,"46689104",TipoEmpleado.PILOTO));
        personas.add(new Empleado("jeronimo","benavidez",34,"35105918",TipoEmpleado.PILOTO));


        return personas;
    }


}
