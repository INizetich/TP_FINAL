package Personas;




import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.Comparable;
import java.util.Objects;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"dni", "nombre", "apellido", "edad"})
public class Persona implements Comparable<Persona>, Serializable {

    @JsonProperty("dni")
    private String dni;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellido")
    private String apellido;

    private int edad;




    public Persona() {
        this.nombre = "";
        this.apellido = "";
        this.edad = 0;

        this.dni = "";

    }

    // Constructor
    @JsonCreator
    public Persona(@JsonProperty("nombre") String nombre,
                   @JsonProperty("apellido") String apellido,
                   @JsonProperty("edad") int edad,
                   @JsonProperty("dni") String dni) {
        this.edad = edad;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    @Override
    public String toString() {
        return "Persona{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", dni='" + dni + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    @Override
    public int compareTo(Persona o) {
        int dni = this.dni.compareTo(o.dni);
        if (dni == 0) {
            return this.edad - o.edad;
        }else {
            return dni;
        }
    }

    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dni", dni);
        jsonObject.put("nombre", nombre);
        jsonObject.put("apellido", apellido);
        jsonObject.put("edad", edad);
        return jsonObject.toString();
    }
}
