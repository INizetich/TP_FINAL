package Personas;


import Pertenencias.Valija;

import java.util.UUID;
public class Pasajero extends Persona{
    private String nroAsiento;
    private Valija valija;
 private boolean checkIn;
    private String codigoPasajero;

    public Pasajero(String nombre, String apellido, int edad, String dni, Valija valija,String nroAsiento) {
        super(nombre, apellido, edad, dni);
        this.nroAsiento = nroAsiento;
        this.valija = valija;
        this.checkIn = false;
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Pasajero(){
        super();
        this.nroAsiento = "";
        this.valija = null;
 this.checkIn = false;
 this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

 public boolean isCheckInRealizado() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public Valija getCantidadEquipaje() {
        return valija;
    }

    public void setCantidadEquipaje(Valija valija) {
        this.valija = valija;
    }

    public String getNroAsiento() {
        return nroAsiento;
    }

    public String getCodigoPasajero() {
        return codigoPasajero;
    }

    public void setCodigoPasajero(String codigoPasajero) {
        this.codigoPasajero = codigoPasajero;
    }

    public void setNroAsiento(String nroAsiento) {
        this.nroAsiento = nroAsiento;
    }

    @Override
    public String toString() {
        return super.toString() +
                "nroAsiento='" + nroAsiento + '\'' +
                ", cantidadEquipaje=" + valija +
                '}';
    }

    ///METODOS PROPIOS
    public void realizarCheckIn(String numeroAsiento){
        this.checkIn = true;
        this.nroAsiento = numeroAsiento;
    }
}
