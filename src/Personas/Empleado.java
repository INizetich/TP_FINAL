package Personas;

import Enums.TipoEmpleado;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.json.JSONObject;


import java.util.Random;

public class Empleado extends Persona {

    private TipoEmpleado tipoEmpleado;

    private int NroEmpleado;
   Random rand = new Random();
    public Empleado (){
        super();
        this.tipoEmpleado = null;
    }

    public Empleado(@JsonProperty("nombre") String nombre
            , @JsonProperty("apellido") String apellido
            , @JsonProperty("edad") int edad
            , @JsonProperty("dni") String dni
            , @JsonProperty("tipoEmpleado") TipoEmpleado tipoEmpleado)
    { super(nombre, apellido, edad, dni);
        this.tipoEmpleado = tipoEmpleado;
        this.NroEmpleado = rand.nextInt(0, 99999); }



    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }
    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public int getNroEmpleado() {
        return NroEmpleado;
    }


    public void setNroEmpleado(int nroEmpleado) {
        NroEmpleado = nroEmpleado;
    }

    @Override
    public String toString() {
        return super.toString() +" ,"+
                "tipoEmpleado=" + tipoEmpleado +
                ", codigoEmpleado='" + NroEmpleado + '\'' +
                '}';
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dni", getDni());  // Supongo que Persona tiene un getter para dni
        jsonObject.put("nombre", getNombre());
        jsonObject.put("apellido", getApellido());
        jsonObject.put("tipoEmpleado", tipoEmpleado != null ? tipoEmpleado.toString() : null);
        jsonObject.put("NroEmpleado", NroEmpleado);
        return jsonObject.toString();
    }


}
