package CheckIn;

import Aviones.Vuelo;
import Personas.Pasajero;

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


}
