package Personas;

import Enums.TipoEmpleado;

import java.util.UUID;

public class Empleado extends Persona{
    private TipoEmpleado tipoEmpleado;
    private String codigoEmpleado;

    public Empleado (){
        super();
        this.tipoEmpleado = null;
    }

    public Empleado (String nombre, String apellido, int edad,String dni, TipoEmpleado tipoEmpleado) {
        super(nombre, apellido, edad, dni);
        this.tipoEmpleado = tipoEmpleado;
        this.codigoEmpleado = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }
    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }


    @Override
    public String toString() {
        return super.toString() +" ,"+
                "tipoEmpleado=" + tipoEmpleado +
                ", codigoEmpleado='" + codigoEmpleado + '\'' +
                '}';
    }
}
