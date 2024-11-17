package CheckIn;

import Aviones.Vuelo;
import Excepciones.ReservaInexistenteException;
import Excepciones.dniNoEncontradoException;
import Gestiones.SistemaReserva;
import Personas.Pasajero;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class CheckIn {
    private Vuelo vuelo;
    private String numeroAsiento;
    private Pasajero pasajero;
    private static String CodigoCheckIn;


    public CheckIn(Vuelo vuelo, String numeroAsiento, Pasajero pasajero) {
        this.vuelo = vuelo;
        this.numeroAsiento = numeroAsiento;
        this.pasajero = pasajero;
        this.CodigoCheckIn = UUID.randomUUID().toString().substring(0, 16);

    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }



    public String getCodigoCheckIn() {
        return CodigoCheckIn;
    }

    public void setCodigoCheckIn(String codigoPasajero) {
        this.CodigoCheckIn = codigoPasajero;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                vuelo +
                 pasajero +
                '}';
    }



    public static void mostrarReserva(String dni, SistemaReserva sistemaReserva) throws dniNoEncontradoException {

        boolean encontrado = false;

        for (CheckIn checkIn : sistemaReserva.getMapaReservas().values()) {
            Pasajero pasajero = checkIn.getPasajero();
            //el trim sirve para limpiar los espacios
            if (pasajero.getDni().trim().equalsIgnoreCase(dni.trim())) {
                // Verificar si el check-in se ha realizado
                if (pasajero.isCheckInRealizado()) {
                    System.out.println("Información de la reserva para " + pasajero.getNombre() + " " + pasajero.getApellido() + ":");
                    System.out.println(checkIn.toString());

                } else {
                    System.out.println("******************************************************************");
                    System.out.println("La reserva aún no ha sido realizado para " + pasajero.getNombre() + " " + pasajero.getApellido());
                }
                encontrado = true;
                break;

            }
        }

        if (!encontrado) {

            throw new dniNoEncontradoException("El DNI no se encuentra dentro del sistema de reservas.");
        }

    }


    public static void generarBoleto(String dni,SistemaReserva sistemaReserva) throws ReservaInexistenteException {

        for (CheckIn checkIn : sistemaReserva.getMapaReservas().values() ) {
            Pasajero pasajero = checkIn.getPasajero();
            if (pasajero.getDni().trim().equalsIgnoreCase(dni.trim())) {
                // Generación del boleto de avión
                Vuelo vuelo = checkIn.getVuelo();
                StringBuilder boleto = new StringBuilder();
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                boleto.append("*********************************************************************\n");
                boleto.append("*                         BOLETO DE AVION                           *\n");
                boleto.append("*********************************************************************\n");
                boleto.append("*Pasajero: ").append(pasajero.getNombre()).append(" ").append(pasajero.getApellido()).append("\n");
                boleto.append("*DNI: ").append(pasajero.getDni()).append("\n");
                boleto.append("*Origen: ").append(vuelo.getOrigen()).append("\n");
                boleto.append("*Destino: ").append(vuelo.getDestino()).append("\n");
                boleto.append("*Fecha de vuelo: ").append(formatoFecha.format(vuelo.getHorario())).append("\n");
                boleto.append("*Número de asiento: ").append(pasajero.getNroAsiento()).append("\n");
                boleto.append("*Puerta de embarque: ").append(vuelo.getPuertaEmbarque()).append("\n");
                boleto.append("*********************************************************************\n");
                boleto.append("Codigo unico de identificacion: ").append(CodigoCheckIn).append("\n");
                boleto.append("*********************************************************************\n");
                boleto.append("*      ¡Buen viaje! Gracias por volar con nosotros.                 *\n");
                boleto.append("*********************************************************************\n");


                // Mostrar el boleto
                System.out.println(boleto.toString());

            }else {
               throw new ReservaInexistenteException("el boleto de avion no tiene ningun dni asociado que haya realizado una reserva");
            }
        }

    }



}
