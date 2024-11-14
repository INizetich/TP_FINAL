package Personas;

import Enums.TipoEmpleado;
import java.util.Objects;
import java.util.Random;

public class Empleado extends Persona{
    private TipoEmpleado tipoEmpleado;
    private int NroEmpleado;
   Random rand = new Random();
    public Empleado (){
        super();
        this.tipoEmpleado = null;
    }

    public Empleado (String nombre, String apellido, int edad,String dni, TipoEmpleado tipoEmpleado) {
        super(nombre, apellido, edad, dni);
        this.tipoEmpleado = tipoEmpleado;
        this.NroEmpleado = rand.nextInt(0,99999);
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }
    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public int getNroEmpleado() {
        return NroEmpleado;
    }



    @Override
    public String toString() {
        return super.toString() +" ,"+
                "tipoEmpleado=" + tipoEmpleado +
                ", codigoEmpleado='" + NroEmpleado + '\'' +
                '}';
    }


}
