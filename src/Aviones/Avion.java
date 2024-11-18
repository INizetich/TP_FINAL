package Aviones;

import Enums.TipoEmpleado;
import Excepciones.NoEsPilotoException;
import Interfaces.IAvion;
import Personas.Empleado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignora campos desconocidos durante la deserialización
public class Avion implements IAvion {

    private String nombre;
    private int capacidadAvion;
    private String motor;
    private String modelo;
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
}