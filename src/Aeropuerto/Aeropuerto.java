package Aeropuerto;

import Enums.CodigoInternacional;

public class Aeropuerto {
    private String nombre;
    private String direccion;
    private CodigoInternacional Codigo;

    public Aeropuerto(){
        this.nombre = "";
        this.direccion = "";
        this.Codigo = null;
    }

    public Aeropuerto(String nombre, String direccion, CodigoInternacional codigo) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.Codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public CodigoInternacional getCodigo() {
        return Codigo;
    }

    public void setCodigo(CodigoInternacional codigo) {
        Codigo = codigo;
    }

    @Override
    public String toString() {
        return "Aeropuerto{" +
                "nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", Codigo Internacional=" + Codigo +
                '}';
    }
}
