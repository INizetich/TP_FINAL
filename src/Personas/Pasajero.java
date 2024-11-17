package Personas;

import Pertenencias.Valija;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.UUID;

public class Pasajero extends Persona {

    @JsonProperty("numeroAsiento")
    private String nroAsiento;  // Asegúrate de que esto sea coherente con el nombre usado en el JSON

    @JsonProperty("valija")
    private List<Valija> valija; // Esto debería ser una lista de objetos Valija, no una cadena

    @JsonProperty("checkInRealizado")  // Mapear el campo JSON "checkInRealizado" al atributo "checkIn"
    private boolean checkIn;

    @JsonProperty("codigoPasajero")  // Asegúrate de que el nombre del campo sea correcto en el JSON
    private String codigoPasajero;

    // Constructor con parámetros
    public Pasajero(String nombre, String apellido, int edad, String dni, List<Valija> valija, String nroAsiento) {
        super(nombre, apellido, edad, dni);  // Llamada al constructor de la clase Persona
        this.nroAsiento = nroAsiento;
        this.valija = valija;
        this.checkIn = false;  // Inicializamos como no realizado
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Constructor por defecto
    public Pasajero() {
        super();  // Llamada al constructor por defecto de la clase Persona
        this.nroAsiento = "";
        this.valija = null;
        this.checkIn = false;
        this.codigoPasajero = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // Getters y setters
    public boolean isCheckInRealizado() {
        return checkIn;
    }

    public void setCheckIn(boolean checkIn) {
        this.checkIn = checkIn;
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

    public String getCodigoPasajero() {
        return codigoPasajero;
    }

    public void setCodigoPasajero(String codigoPasajero) {
        this.codigoPasajero = codigoPasajero;
    }

    // Método para representar el objeto como cadena
    @Override
    public String toString() {
        return super.toString() +
                "nroAsiento='" + nroAsiento + '\'' +
                ", cantidadEquipaje=" + valija +
                '}';
    }

    // Método para realizar el Check-In
    public void realizarCheckIn(String numeroAsiento) {
        this.checkIn = true;
        this.nroAsiento = numeroAsiento;
    }
}
