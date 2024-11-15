package Aviones;


import Enums.TipoEmpleado;
import Excepciones.NoEsPilotoException;
import Interfaces.IAvion;
import Personas.Empleado;


public class Avion implements IAvion {

    private String nombre;

    private int capacidadAvion;

    private String motor;

    private String modelo;

    private String codigoAvion;

    private Empleado empleado;

    public Avion(){

        this.nombre = "";
        this.capacidadAvion = 0;
        this.motor = "";
        this.modelo = "";
        this.codigoAvion = "";
    }

    public Avion(String nombre, int capacidadAvion, String motor, String modelo,String codigoAvion) {
        this.nombre = nombre;
        this.capacidadAvion = capacidadAvion;
        this.motor = motor;
        this.modelo = modelo;
        this.codigoAvion = codigoAvion;
    }

    public String  getCodigoAvion() {
        return codigoAvion;
    }

    public void setCodigoAvion(String  codigoAvion) {
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

    public void setPiloto(Empleado piloto) {
        this.empleado = piloto;
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


    @Override
    public String toString() {
        return "Avion{" +
                "nombre='" + nombre + '\'' +
                ", capacidadAvion=" + capacidadAvion +
                ", motor='" + motor + '\'' +
                ", modelo='" + modelo + '\'' +
                ", codigoAvion='" + codigoAvion + '\'' +
                ", Piloto=" + empleado +
                '}';
    }

    public void asignarPiloto(Empleado piloto) throws NoEsPilotoException {
        this.empleado = piloto;


        if (piloto.getTipoEmpleado()!= TipoEmpleado.PILOTO) {
            throw new NoEsPilotoException("la persona elegida no es piloto");

        }
    }

    public Empleado getPiloto() {
        return empleado;
    }



    @Override
    public String despegar() {
        return " avion con codigo: " + codigoAvion+ " via libre para maniobrar sobre pista para su pronto despegue";
    }

    @Override
    public String aterrizar() {
        return " avion con codigo: " + codigoAvion+ " via libre para descender a pista";
    }

    @Override
    public String cargarCombustible() {
        return " avion con codigo: " + codigoAvion+ " via libre para el reabastecimiento de combustible";
    }

    @Override
    public String asignarRuta(){
        return  "Ruta asignada al avion: " + codigoAvion+ "ruta libre para ir al destino";
    }



}
