package Personas;


import java.util.UUID;
public class Pasajero extends Persona{
    private String nroAsiento;
    private int cantidadEquipaje;
 private boolean checkIn;
    private String codigoPasajero;

    public Pasajero(String nombre, String apellido, int edad, String dni, int cantidadEquipaje,String nroAsiento) {
        super(nombre, apellido, edad, dni);
        this.nroAsiento = nroAsiento;
        this.cantidadEquipaje = cantidadEquipaje;
        this.checkIn = false;
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Pasajero(){
        super();
        this.nroAsiento = "";
        this.cantidadEquipaje = 0;
 this.checkIn = false;
 this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

 public boolean isCheckInRealizado() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
    }

    public int getCantidadEquipaje() {
        return cantidadEquipaje;
    }

    public void setCantidadEquipaje(int cantidadEquipaje) {
        this.cantidadEquipaje = cantidadEquipaje;
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
                ", cantidadEquipaje=" + cantidadEquipaje +
                '}';
    }

    ///METODOS PROPIOS
    public void realizarCheckIn(String numeroAsiento){
        this.checkIn = true;
        this.nroAsiento = numeroAsiento;
    }
}
