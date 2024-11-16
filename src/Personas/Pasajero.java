package Personas;


import Pertenencias.Valija;

import java.util.List;
import java.util.UUID;
public class Pasajero extends Persona{
    private String nroAsiento;
    private List<Valija> valija;
 private boolean checkIn;
    private String codigoPasajero;

    public Pasajero(String nombre, String apellido, int edad, String dni, List<Valija> valija,String nroAsiento) {
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

    public List<Valija> getValija() {
        return valija;
    }

    public void setValija(List<Valija> valija) {
        this.valija = valija;
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
