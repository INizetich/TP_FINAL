package Gestiones;

import Aeropuerto.Aeropuerto;
import Aviones.Avion;
import Aviones.Vuelo;
import Config.ConfigAdmin;
import Config.Configs;
import Enums.EstadoEmbarque;
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
                        printCentered("🚫 No se encontraron empleados deserializados.");
                    } else {
                        setListaEmpleados(empleadosJSON); // Establecemos la lista de administradores
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                printCentered("🚫 El archivo de empleados no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    personal.createNewFile();
                    printCentered("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de administradores.");
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
                    printCentered("✔️ empleado/s agregado/s y archivo actualizado.");
                } catch (Exception e) {
                    printCentered("🚫 Error al serializar el archivo de empleados.");
                    e.printStackTrace();
                }

                printCentered("¿Desea agregar otra persona? (s/n)");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("n")) {
                    seguir = false; // Salir del bucle
                }
            }
        } else {
            printCentered("🚫 El sistema está en su primera ejecución, no se pueden agregar administradores.");
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
                        printCentered("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(adminsJSON); // Establecemos la lista de administradores
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                printCentered("🚫 El archivo de administradores no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    admins.createNewFile();
                    printCentered("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de administradores.");
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
                    printCentered("✔️ Administrador agregado y archivo actualizado.");
                } catch (Exception e) {
                    printCentered("🚫 Error al serializar el archivo de administradores.");
                    e.printStackTrace();
                }

                printCentered("¿Desea crear otra cuenta de administrador? (s/n)");
                String opcion = scanner.nextLine();

                if (opcion.equalsIgnoreCase("n")) {
                    seguir = false; // Salir del bucle
                }
            }
        } else {
            printCentered("🚫 El sistema está en su primera ejecución, no se pueden agregar administradores.");
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
                        printCentered("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(loguinPersonas); // Establecemos la lista de administradores
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                printCentered("🚫 El archivo de administradores no existe. Asegúrese de que se haya creado correctamente.");
                // Asegurarse de que el archivo exista en la primera ejecución o crear uno vacío
                try {
                    loguin.createNewFile();
                    printCentered("✔️ Archivo de administradores creado.");
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de administradores.");
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
                        printCentered("🚫 No se encontraron empleados deserializados.");
                    } else {
                        // Actualizamos la lista de empleados
                        setListaEmpleados(empleadosAEliminar);

                    }
                } catch (JSONException e) {
                    printCentered("🚫 Error al deserializar los empleados.");
                    e.printStackTrace();
                    return;
                }
            } else {
                // Si el archivo no existe, lo creamos vacío
                printCentered("🚫 El archivo de empleados no existe. Creando archivo vacío...");
                try {
                    if (personalEliminado.createNewFile()) {
                        printCentered("✔️ Archivo de empleados creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de empleados.");
                        return;
                    }
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de empleados.");
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
                printCentered("✔️ Empleado eliminado correctamente de la lista.");
            }

            // Serializamos la lista de empleados nuevamente en el archivo JSON
            try {
                GestionJSON.serializarSet(listaEmpleados, personalEliminado.getPath());
                printCentered("✔️ Archivo de empleados actualizado.");
            } catch (Exception e) {
                printCentered("🚫 Error al serializar el archivo de empleados.");
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
                        printCentered("🚫 No se encontraron administradores deserializados.");
                    } else {
                        setListaAdministradores(adminAeliminar);
                    }
                } catch (JSONException e) {
                    printCentered("🚫 Error al deserializar el archivo de administradores.");
                    e.printStackTrace();
                }
            } else {
                // Si el archivo no existe, lo creamos vacío
                printCentered("🚫 El archivo de administradores no existe. Creando archivo vacío...");
                try {
                    if (eliminarAdmin.createNewFile()) {
                        printCentered("✔️ Archivo de administradores creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de administradores.");
                        return;
                    }
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de administradores.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            // Verificar si la lista de administradores no está vacía
            if (listaAdministradores.isEmpty()) {
                printCentered("🚫 No hay administradores en la lista de administradores.");
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
                    printCentered("✔️ Administrador eliminado correctamente de la lista.");
                }
            }

            // Serializar de nuevo la lista de administradores al archivo JSON
            try {
                GestionJSON.serializarSet(listaAdministradores, "Archivos JSON/admins.json");
                printCentered("✔️ Archivo de administradores actualizado.");
            } catch (JSONException e) {
                printCentered("🚫 Error al serializar el archivo de administradores.");
                e.printStackTrace();
            }
        }
    }




    public void mostrarCuentasAdmin() {
        if (listaAdministradores.isEmpty()) {
            printCentered("🚨 La lista de administradores está vacía.");
            return;
        }

        System.out.println(("======== Lista de Administradores ========"));
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
            printCentered("🚫 La lista de empleados está vacía.");
            return;
        }

        // Cabecera
        System.out.println("╔═══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                 Lista de Empleados                                ║");
        System.out.println("╠════════════════════╦════════════════════╦════════════════════╦════════════════════╣");
        System.out.println("║        DNI         ║       Nombre       ║       Apellido     ║        Cargo       ║");
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
                try {
                    SistemaVuelo.setVuelosGenerados(GestionJSON.deserializarVuelos(eliminarVuelo.getPath()));
                    if (SistemaVuelo.getVuelosGenerados().isEmpty()) {
                        printCentered("🚫 No se encontraron vuelos deserializados.");
                    } else {

                    }
                } catch (JSONException e) {
                    printCentered("🚫 Error al deserializar el archivo de vuelos.");
                    e.printStackTrace();
                }
            } else {
                // Si el archivo no existe, lo creamos vacío
                printCentered("🚫 El archivo de vuelos no existe. Creando archivo vacío...");
                try {
                    if (eliminarVuelo.createNewFile()) {
                        printCentered("✔️ Archivo de vuelos creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de vuelos.");
                        return;
                    }
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de vuelos.");
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
                printCentered("🚫 Error: no se puede eliminar el vuelo porque tiene reservas hechas");
                return;
            }

            SistemaVuelo.getVuelosGenerados().remove(vuelo);
            printCentered("✅ Vuelo con ID " + idVuelo + " eliminado exitosamente.");


            try {

                GestionJSON.serializarLista(SistemaVuelo.getVuelosGenerados(), "Archivos JSON/vuelos.json");
                printCentered("✔️ Archivo de vuelos actualizado.");
            } catch (JSONException e) {
                printCentered("🚫 Error al serializar el archivo de vuelos.");
                e.printStackTrace();
            }




        }
        }







    public  void agregarVuelo(String origen, String destino, AlmacenamientoAviones almacenamientoAviones) throws AeropuertoNoEncontradoException {
        if (ConfigAdmin.isFirstRunAdmin()) {
            File vuelos = new File("Archivos JSON/vuelos.json");
            File aeropuertos = new File("Archivos JSON/aeropuertos.json");

            // Verificar si los archivos existen y deserializar
            if (vuelos.exists() && aeropuertos.exists()) {
                List<Vuelo> vuelosJSON = null;
                Set<Aeropuerto> aeropuertosJSON = null;
                try {
                    vuelosJSON = GestionJSON.deserializarLista(Vuelo.class, vuelos.getPath());
                    aeropuertosJSON = GestionJSON.deserializarSet(Aeropuerto.class, aeropuertos.getPath());
                    if (vuelosJSON.isEmpty() || aeropuertosJSON.isEmpty()) {
                        printCentered("🚫 No se encontraron vuelos deserializados o aeropuertos deserializados.");
                    } else {
                        SistemaVuelo.setVuelosGenerados(vuelosJSON);
                        SistemaAeropuerto.setListaAeropuertos(aeropuertosJSON);
                    }
                } catch (JSONException e) {
                    printCentered("🚫 Error al deserializar el archivo de vuelos o aeropuertos.");
                    e.printStackTrace();
                }
            } else {
                // Si los archivos no existen, los creamos vacíos
                printCentered("🚫 Los archivos de vuelos o aeropuertos no existen. Creando archivos vacíos...");
                try {
                    if (vuelos.createNewFile()) {
                        printCentered("✔️ Archivo de vuelos creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de vuelos.");
                        return;
                    }

                    if (aeropuertos.createNewFile()) {
                        printCentered("✔️ Archivo de aeropuertos creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de aeropuertos.");
                    }
                } catch (IOException e) {
                    printCentered("🚫 Error al crear el archivo de aeropuertos.");
                    e.printStackTrace();
                    return; // Salir si no se puede crear el archivo
                }
            }

            // Verificar si el aeropuerto de origen existe
            Aeropuerto origenAeropuerto = SistemaAeropuerto.getListaAeropuertos().stream()
                    .filter(a -> a.getNombre().equalsIgnoreCase(origen))
                    .findFirst()
                    .orElse(null);

            if (origenAeropuerto == null) {
                throw new AeropuertoNoEncontradoException("El origen no se encuentra en la lista de aeropuertos disponibles");
            }

            // Verificar si el aeropuerto de destino existe
            Aeropuerto destinoAeropuerto = SistemaAeropuerto.getListaAeropuertos().stream()
                    .filter(a -> a.getNombre().equalsIgnoreCase(destino))
                    .findFirst()
                    .orElse(null);

            if (destinoAeropuerto == null) {
                throw new AeropuertoNoEncontradoException("El destino no se encuentra en la lista de aeropuertos");
            }

            // Crear el vuelo
            Vuelo vuelo = new Vuelo(origen, destino);
               vuelo.setIdVuelo(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            // Obtener todos los aviones disponibles de los hangares
            List<Avion> avionesDisponibles = almacenamientoAviones.obtenerAvionesDeTodosLosHangares();

            if (!avionesDisponibles.isEmpty()) {
                // Seleccionar el primer avión disponible (aquí puedes agregar lógica más avanzada si es necesario)
                Avion avionDisponible = avionesDisponibles.get(0); // Modificar si es necesario un criterio más específico

                // Asignar el avión al vuelo
                vuelo.setAvion(avionDisponible);
                vuelo.setEstadoEmbarque(EstadoEmbarque.EN_HORARIO);
                printCentered("✔️ Avión asignado automáticamente al vuelo.");
            } else {
                printCentered("🚫 No hay aviones disponibles para asignar al vuelo.");
            }

            // Agregar el vuelo a la lista de vuelos generados
            SistemaVuelo.getVuelosGenerados().add(vuelo);
            printCentered("✔️ Vuelo creado correctamente.");

            // Serializar vuelos y aeropuertos actualizados
            try {
                List<Vuelo> vuelosSerializar = SistemaVuelo.getVuelosGenerados();
                GestionJSON.serializarLista(vuelosSerializar, "Archivos JSON/vuelos.json");

                Set<Aeropuerto> aeropuertoSerializar = SistemaAeropuerto.getListaAeropuertos();
                GestionJSON.serializarSet(aeropuertoSerializar, "Archivos JSON/aeropuertos.json");

                printCentered("✔️ Archivo de vuelos actualizado.");
                printCentered("✔️ Archivo de aeropuertos actualizado.");
            } catch (JSONException e) {
                printCentered("🚫 Error al serializar el archivo de vuelos o aeropuertos.");
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
            printCentered("🔑 Ingrese el nombre del administrador: ");
            nombre = scanner.nextLine();
            if (nombre.trim().isEmpty()) {
                printCentered("🚨 El nombre no puede estar vacío. Por favor, ingrésalo nuevamente. 📝");
            }
        } while (nombre.trim().isEmpty());

        // Validar apellido
        do {
            printCentered("📛 Ingrese el apellido del administrador: ");
            apellido = scanner.nextLine();
            if (apellido.trim().isEmpty()) {
                printCentered("🚨 El apellido no puede estar vacío. Por favor, ingrésalo nuevamente. 📝");
            }
        } while (apellido.trim().isEmpty());

        // Validar edad
        do {
            printCentered("🎂 Ingrese la edad del administrador: ");
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            if (edad <= 0 || edad >= 110) {
                printCentered("🚨 La edad debe ser mayor que 0 y menor que 110. Por favor, intente nuevamente. 🔢");
            }
        } while (edad <= 0 || edad >= 110);

        // Validar DNI
        do {
            printCentered("🆔 Ingrese el DNI del administrador (8 dígitos numéricos): ");
            dni = scanner.nextLine();
            if (dni.length() != 8) {
                printCentered("🚨 El DNI debe tener exactamente 8 caracteres. Intente nuevamente. 🔢");
            } else if (!dni.matches("\\d+")) { // Verifica que el DNI contenga solo números
                printCentered("🚨 El DNI debe contener solo números. Por favor, intente nuevamente. 🔢");
            }
        } while (dni.length() != 8 || !dni.matches("\\d+"));

        // Crear y retornar la cuenta admin
        printCentered("✔️ Cuenta de administrador creada con éxito. ¡Bienvenido al sistema! 🎉");
        return new Persona(nombre, apellido, edad, dni);
    }


    private Empleado crearEmpleado(){
        String modificacionEmpleado = "";
        String nombre = "";
        String apellido = "";
        String dni = "";
        int edad = 0;
        TipoEmpleado tipo = null;

        // Solicitar nombre
        do {
            printCentered("🔑 Ingrese el nombre del empleado: ");
            nombre = scanner.nextLine();

            // Validación de nombre
            if (nombre.trim().isEmpty()) {
                printCentered("❌ El nombre no puede estar vacío. Intente nuevamente.");
            }
        } while (nombre.trim().isEmpty());

        // Solicitar apellido
        do {
            printCentered("📛 Ingrese el apellido del empleado: ");
            apellido = scanner.nextLine();

            // Validación de apellido
            if (apellido.trim().isEmpty()) {
                printCentered("❌ El apellido no puede estar vacío. Intente nuevamente.");
            }
        } while (apellido.trim().isEmpty());

        // Solicitar edad
        do {
            printCentered("🎂 Ingrese la edad del empleado: ");
            while (!scanner.hasNextInt()) {
                printCentered("❌ Por favor ingrese un número válido para la edad.");
                scanner.next(); // Limpiar buffer
            }
            edad = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            // Validación de edad (por ejemplo, mayor o igual a 18)
            if (edad < 18) {
                printCentered("❌ La edad debe ser mayor o igual a 18 años.");
            }
        } while (edad < 18);

        // Solicitar DNI
        do {
            printCentered("🆔 Ingrese el DNI del empleado (8 dígitos numéricos): ");
            dni = scanner.nextLine();

            // Validación de DNI (debe tener 8 dígitos numéricos)
            if (!dni.matches("\\d{8}")) {
                printCentered("❌ El DNI debe contener exactamente 8 dígitos numéricos.");
            }
        } while (!dni.matches("\\d{8}"));

        // Solicitar tipo de empleo
        do {
            printCentered("💼 Ingrese el tipo de empleo del empleado (PILOTO, COPILOTO, AZAFATA): ");
            String tipoInput = scanner.nextLine().toUpperCase();

            // Validación de tipo de empleo
            try {
                tipo = TipoEmpleado.valueOf(tipoInput);
            } catch (IllegalArgumentException e) {
                printCentered("❌ El tipo de empleo no es válido. Ingrese uno de los siguientes: PILOTO, COPILOTO, AZAFATA.");
            }
        } while (tipo == null);

        // Preguntar si desea modificar la información
        do {
            printCentered("¿Desea modificar su información? (S/N): ");
            modificacionEmpleado = scanner.nextLine();

            // Si desea modificar, repetir la recolección de datos
            if (modificacionEmpleado.equalsIgnoreCase("s")) {
                return crearEmpleado(); // Volver a ejecutar el proceso de creación
            }
        } while (!modificacionEmpleado.equalsIgnoreCase("s") && !modificacionEmpleado.equalsIgnoreCase("n"));

        // Crear el empleado con la información validada
        return new Empleado(nombre, apellido, edad, dni, tipo);
    }






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
        personas.add(new Empleado("lionel","messi",37,"35105918",TipoEmpleado.PILOTO));


        return personas;
    }

    public static void printCentered(String text) {
        int terminalWidth = 150; // Puedes ajustar este valor según el ancho de tu terminal
        int padding = (terminalWidth - text.length()) / 2;
        String paddedText = " ".repeat(padding) + text;
        System.out.println(paddedText);
    }

    public static void limpiarPantalla() {
        // Imprime 50 líneas vacías para simular la limpieza de pantalla
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }}


