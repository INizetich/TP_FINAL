package Aviones;

import Enums.TipoEmpleado;
import Excepciones.NoEsPilotoException;
import Interfaces.IAvion;
import Personas.Empleado;
import com.fasterxml.jackson.annotation.*;
import org.json.JSONObject;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconocidos durante la deserialización
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nombre", "capacidadAvion", "motor", "modelo", "codigoAvion", "piloto" })
public class Avion implements IAvion {
@JsonProperty("nombre")
    private String nombre;
@JsonProperty("capacidadAvion")
    private int capacidadAvion;
@JsonProperty("motor")
    private String motor;
@JsonProperty("modelo")
    private String modelo;
@JsonProperty("codigoAvion")
    private String codigoAvion;

    private Empleado empleado;


    public Avion() {
        this.nombre = "";
        this.capacidadAvion = 0;
        this.motor = "";
        this.modelo = "";
        this.codigoAvion = "";

    }

    public Avion(String nombre, int capacidadAvion, String motor, String modelo, String codigoAvion) {
        this.nombre = nombre;
        this.capacidadAvion = capacidadAvion;
        this.motor = motor;
        this.modelo = modelo;
        this.codigoAvion = codigoAvion;

    }



    // Getters y setters
    public String getCodigoAvion() {
        return codigoAvion;
    }

    public void setCodigoAvion(String codigoAvion) {
        this.codigoAvion = codigoAvion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidadAvion() {
        return capacidadAvion;
    }

    public void setCapacidadAvion(int capacidadAvion) {
        this.capacidadAvion = capacidadAvion;
    }

    public String getMotor() {
        return motor;
    }

    public void setMotor(String motor) {
        this.motor = motor;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void asignarPiloto(Empleado piloto) throws NoEsPilotoException {
        if (piloto.getTipoEmpleado() != TipoEmpleado.PILOTO) {
            throw new NoEsPilotoException("La persona elegida no es piloto.");
        }
        this.empleado = piloto;
    }



    public Empleado getPiloto() {
        return empleado;
    }

    @Override
    public String toString() {
        return "Avion{" +
                "nombre='" + nombre + '\'' +
                ", capacidadAvion=" + capacidadAvion +
                ", motor='" + motor + '\'' +
                ", modelo='" + modelo + '\'' +
                ", codigoAvion='" + codigoAvion + '\'' +
                ", Piloto=" + (empleado != null ? empleado : "No asignado") + // Muestra "No asignado" si no hay piloto
                '}';
    }

    @Override
    public String despegar() {
        return "Avión con código: " + codigoAvion + " via libre para maniobrar sobre pista para su pronto despegue.";
    }

    @Override
    public String aterrizar() {
        return "Avión con código: " + codigoAvion + " via libre para descender a pista.";
    }

    @Override
    public String cargarCombustible() {
        return "Avión con código: " + codigoAvion + " via libre para el reabastecimiento de combustible.";
    }

    @Override
    public String asignarRuta() {
        return "Ruta asignada al avión: " + codigoAvion + ". Ruta libre para ir al destino.";
    }


    // Método toJson para la clase Avion usando org.json
    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nombre", nombre);
        jsonObject.put("capacidadAvion", capacidadAvion);
        jsonObject.put("motor", motor);
        jsonObject.put("modelo", modelo);
        jsonObject.put("codigoAvion", codigoAvion);
        jsonObject.put("empleado", empleado != null ? empleado.toJson() : null); // Asumiendo que Empleado también tiene un método toJson
        return jsonObject.toString();
    }


    /*@JsonProperty("nombre")
    private String nombre;
@JsonProperty("capacidadAvion")
    private int capacidadAvion;
@JsonProperty("motor")
    private String motor;
@JsonProperty("modelo")
    private String modelo;
@JsonProperty("codigoAvion")
    private String codigoAvion;

    private Empleado empleado;*/
}