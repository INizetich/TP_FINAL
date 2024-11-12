package GestionCheckIn;


import Excepciones.CapacidadMaximaException;
import Excepciones.DniRegistradoException;
import Excepciones.dniNoEncontradoException;
import Enums.EstadoEmbarque;
import GestionAviones.Vuelo;
import GestionAviones.SistemaVuelo;
import Personas.Pasajero;
import java.text.SimpleDateFormat;
import java.util.*;

public class SistemaCheckIn {
    private final Map<String, CheckIn> mapaCheckIn; // UUID como clave, CheckIn como valor


    public SistemaCheckIn() {

        this.mapaCheckIn = new HashMap<>();
    }


    public void realizarCheckIn()  {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Lista de vuelos disponibles:");
        SistemaVuelo.getVuelos().forEach(v -> System.out.println(
                "ID: " + v.getIdVuelo() + " | Origen: " + v.getOrigen() +
                        " | Destino: " + v.getDestino() + " | Avión: " + v.getAvion().getNombre() +
                        " | Estado de embarque: " + v.getEstadoEmbarque()
        ));

        // Selección del vuelo
        System.out.print("Ingrese el ID del vuelo que desea abordar: ");
        String idVueloSeleccionado = scanner.nextLine().toUpperCase();

        // Filtro la lista de vuelos por el id de vuelo
        Vuelo vueloSeleccionado = SistemaVuelo.getVuelos().stream()
                .filter(v -> v.getIdVuelo().equalsIgnoreCase(idVueloSeleccionado))
                .findFirst()
                .orElse(null);

        if (vueloSeleccionado == null) {
            System.out.println("El ID del vuelo ingresado no se encuentra en la lista de vuelos disponibles.");
            return;
        }

        // Mostrar asientos disponibles
        List<String> asientosDisponibles = generarAsientosDisponibles(vueloSeleccionado);
        if (asientosDisponibles.isEmpty()) {
            System.out.println("No hay asientos disponibles.");
            return;
        }

        System.out.println("Asientos disponibles: " + asientosDisponibles);

        // Seleccionar asiento
        System.out.print("Seleccione un asiento disponible: ");
        String asientoSeleccionado = scanner.nextLine().toUpperCase();

        if (!asientosDisponibles.contains(asientoSeleccionado)) {
            System.out.println("El asiento seleccionado no está disponible.");
            return;
        }



        Pasajero pasajero = crearPasajero(asientoSeleccionado);

        try {
            if (vueloSeleccionado.agregarPasajero(pasajero)) {
                vueloSeleccionado.ocuparAsiento(asientoSeleccionado);
                mapaCheckIn.put(pasajero.getDni(), new CheckIn(vueloSeleccionado, asientoSeleccionado, pasajero));
                System.out.println("Check-in realizado exitosamente para " + pasajero.getNombre() + " " + pasajero.getApellido());
                pasajero.setCheckIn(true);
            }
        } catch (CapacidadMaximaException e) {
            System.out.println(e.getMessage());
        }
    }


    private Pasajero crearPasajero(String asientoSeleccionado) throws DniRegistradoException {
        String eleccion;
        String nombre;
        String apellido;
        int edad;
        String dni;
        int cantidadEquipaje;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese los datos del pasajero:");
            System.out.print("Nombre: ");
            nombre = scanner.nextLine();
            System.out.print("Apellido: ");
             apellido = scanner.nextLine();
            System.out.print("Edad: ");
            edad = scanner.nextInt();
            scanner.nextLine();
            System.out.print("DNI: ");
           dni = scanner.nextLine();

            // Verificar si el DNI ya está asociado a un check-in
            if (mapaCheckIn.containsKey(dni)) {
                throw new DniRegistradoException("El DNI " + dni + " ya está asociado a un check-in.");
            }
            System.out.print("Cantidad de equipaje: ");
            cantidadEquipaje = scanner.nextInt();
            scanner.nextLine();

            System.out.println("desea editar su informacion? s/n");
          eleccion = scanner.next().trim().toLowerCase() + "";

        }while (eleccion.equals("s"));

        return new Pasajero(nombre, apellido, edad, dni, cantidadEquipaje,asientoSeleccionado);

    }

    private List<String> generarAsientosDisponibles(Vuelo vuelo) {
        List<String> asientosDisponibles = new ArrayList<>();
        for (int i = 1; i <= vuelo.getAvion().getCapacidadAvion(); i++) {
            String asientoA = "A" + i;
            String asientoB = "B" + i;
            String asientoC = "C" + i;
            String asientoD = "D" + i;
            String asientoE = "E" + i;
            if (!vuelo.getAsientos().contains(asientoA)) {
                asientosDisponibles.add(asientoA);
                asientosDisponibles.add(asientoB);
                asientosDisponibles.add(asientoC);
                asientosDisponibles.add(asientoD);
                asientosDisponibles.add(asientoE);
            }
        }
        return asientosDisponibles;
    }

    public void mostrarMap() {
        mapaCheckIn.forEach((key, value) -> System.out.println("Código: " + key + " -> " + value));
    }


    public void mostrarInformacionCheckIn(String dni) throws dniNoEncontradoException {
        boolean encontrado = false;

        for (CheckIn checkIn : mapaCheckIn.values()) {
            Pasajero pasajero = checkIn.getPasajero();
            //el trim sirve para limpiar los espacios
            if (pasajero.getDni().trim().equalsIgnoreCase(dni.trim())) {
                // Verificar si el check-in se ha realizado
                if (pasajero.isCheckInRealizado()) {
                    System.out.println("Información del Check-In para " + pasajero.getNombre() + " " + pasajero.getApellido() + ":");
                    System.out.println(checkIn.toString());



                    // Generación del boleto de avión
                    Vuelo vuelo = checkIn.getVuelo();
                    StringBuilder boleto = new StringBuilder();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                         vuelo.setEstadoEmbarque(EstadoEmbarque.CERRADO);
                    boleto.append("************************************\n");
                    boleto.append("            BOLETO DE AVION        \n");
                    boleto.append("************************************\n");
                    boleto.append("Pasajero: ").append(pasajero.getNombre()).append(" ").append(pasajero.getApellido()).append("\n");
                    boleto.append("DNI: ").append(pasajero.getDni()).append("\n");
                    boleto.append("Origen: ").append(vuelo.getOrigen()).append("\n");
                    boleto.append("Destino: ").append(vuelo.getDestino()).append("\n");
                    boleto.append("Fecha de vuelo: ").append(formatoFecha.format(vuelo.getHorario())).append("\n");
                    boleto.append("Número de asiento: ").append(pasajero.getNroAsiento()).append("\n");
                    boleto.append("Puerta de embarque: ").append(vuelo.getPuertaEmbarque()).append("\n");
                    boleto.append("************************************\n");
                    // Generar un código único para el boleto
                    String codigoUnico = UUID.randomUUID().toString();
                    boleto.append("Codigo unico de identificacion: ").append(codigoUnico).append("\n");
                    boleto.append("************************************\n");
                    boleto.append("¡Buen viaje! Gracias por volar con nosotros.\n");
                    boleto.append("************************************");

                    // Mostrar el boleto
                    System.out.println(boleto.toString());
                } else {
                    System.out.println("El check-in aún no ha sido realizado para " + pasajero.getNombre() + " " + pasajero.getApellido());
                }
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            throw new dniNoEncontradoException("El DNI no se encuentra dentro del sistema de check-in.");
        }
    }



}
