package CheckIn;

import Aviones.Vuelo;
import Excepciones.dniNoEncontradoException;
import Gestiones.SistemaReserva;
import Personas.Pasajero;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class CheckIn {
    private Vuelo vuelo;
    private String numeroAsiento;
    private Pasajero pasajero;
    private boolean realizado;
    private String codigoPasajero;
    public CheckIn(Vuelo vuelo, String numeroAsiento, Pasajero pasajero) {
        this.vuelo = vuelo;
        this.numeroAsiento = numeroAsiento;
        this.realizado = true; // El check-in se marca como realizado al crearlo
        this.pasajero = pasajero;
        this.codigoPasajero = null;
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

    public boolean isRealizado() {
        return realizado;
    }

    public void setRealizado(boolean realizado) {
        this.realizado = realizado;
    }

    public String getCodigoCheckIn() {
        return codigoPasajero;
    }

    public void setCodigoCheckIn(String codigoPasajero) {
        this.codigoPasajero = codigoPasajero;
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
                    System.out.println("El check-in aún no ha sido realizado para " + pasajero.getNombre() + " " + pasajero.getApellido());
                }
                encontrado = true;
                break;

            }
        }

        if (!encontrado) {
            System.out.println("******************************************************************");
            throw new dniNoEncontradoException("El DNI no se encuentra dentro del sistema de reservas.");
        }

    }


    public static void generarBoleto(String dni,SistemaReserva sistemaReserva){

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
                // Generar un código único para el boleto
                String codigoUnico = UUID.randomUUID().toString();
                boleto.append("Codigo unico de identificacion: ").append(codigoUnico).append("\n");
                boleto.append("*********************************************************************\n");
                boleto.append("*      ¡Buen viaje! Gracias por volar con nosotros.                 *\n");
                boleto.append("*********************************************************************\n");


                // Mostrar el boleto
                System.out.println(boleto.toString());

            }else {
                System.out.println("no hay ninguna reserva con ese numero de dni.");
            }
        }

    }



}
