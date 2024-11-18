package Personas;

import Pertenencias.Valija;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pasajero extends Persona {

    @JsonProperty("numeroAsiento")
    private String nroAsiento;

    @JsonProperty("valija")
    private List<Valija> valija;

    @JsonProperty("checkInRealizado")
    private boolean checkIn;

    @JsonProperty("codigoPasajero")
    private String codigoPasajero;

    // Constructor con parámetros

    public Pasajero(String nombre, String apellido, int edad, String dni, List<Valija> valija, String nroAsiento) {
        super(nombre, apellido, edad, dni);
        this.nroAsiento = nroAsiento != null ? nroAsiento : "Sin Asignar";
        this.valija = valija != null ? valija : new ArrayList<>();
        this.checkIn = false;
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Constructor por defecto
    public Pasajero() {
        super();
        this.nroAsiento = "";
        this.valija = null;
        this.checkIn = false;
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void realizarCheckIn(String numeroAsiento) {
        if (numeroAsiento == null || numeroAsiento.isEmpty()) {
            throw new IllegalArgumentException("El número de asiento no puede estar vacío.");
        }
        this.checkIn = true;
        this.nroAsiento = numeroAsiento;
    }
    @Override
    public String toString() {
        return super.toString() +
                "nroAsiento='" + nroAsiento + '\'' +
                ", cantidadEquipaje=" + valija +
                '}';
    }

    public String getNroAsiento() {
        return nroAsiento;
    }

    public void setNroAsiento(String nroAsiento) {
        this.nroAsiento = nroAsiento;
    }

    public List<Valija> getValija() {
        return valija;
    }

    public void setValija(List<Valija> valija) {
        this.valija = valija;
    }

    public boolean isCheckIn() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public String getCodigoPasajero() {
        return codigoPasajero;
    }

    public void setCodigoPasajero(String codigoPasajero) {
        this.codigoPasajero = codigoPasajero;
    }
}
