package Personas;

public class Empleado extends Persona{
    private TipoEmpleado tipoEmpleado;

    public Empleado (){
        super();
        this.tipoEmpleado = null;
    }

    public Empleado (String nombre, String apellido, int edad, TipoEmpleado tipoEmpleado) {
        super();
        this.tipoEmpleado = tipoEmpleado;
    }

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }
    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }


    @Override
    public String toString() {
        return "Empleado{" +
                "tipoEmpleado=" + tipoEmpleado +
                '}';
    }
}
