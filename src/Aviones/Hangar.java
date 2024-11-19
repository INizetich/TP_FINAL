package Aviones;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@JsonPropertyOrder({"numeroHangar", "capacidadMaxima","listaAviones"})
public class Hangar <T extends Avion> implements Serializable {

    @JsonProperty("numeroHangar")
    private int numeroHangar;

    @JsonProperty("listaAviones")
    private List<Avion> listaAviones;
@JsonProperty("capacidadMaxima")
private  final int capacidadMaxima = 7;

    public Hangar() {
        this.numeroHangar = 0;
        this.listaAviones = new ArrayList<>();

    }

    public List<Avion> getListaAviones() {
        return listaAviones;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setListaAviones(List<Avion> listaAviones) {
        this.listaAviones = listaAviones;
    }

    public boolean estaLleno() {
        return listaAviones.size() >= capacidadMaxima; // capacidadMaxima debe ser un atributo de Hangar
    }

    public Hangar(int numeroHangar) {
        this.numeroHangar = numeroHangar;
        this.listaAviones = new ArrayList<>();
    }

    public int getNumeroHangar() {
        return numeroHangar;
    }

    @Override
    public String toString() {
        return "Hangar{" +
                "listaAviones=" + listaAviones +
                ", NumeroHangar='" + numeroHangar + '\'' +
                '}';
    }

    public void setNumeroHangar(int numeroHangar) {
        this.numeroHangar = numeroHangar;
    }

    public void agregarAvion(T avion) {
        this.listaAviones.add(avion);
    }

    public void eliminarAvion(T avion) {
        if(this.listaAviones.contains(avion)) {
            this.listaAviones.remove(avion);
            System.out.println("Avion eliminado correctamente del Hangar");
        }
    }

    public List<Avion> ObtenerListaAviones() {
        return this.listaAviones;
    }


    public String toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("numeroHangar", numeroHangar);
        jsonObject.put("capacidadMaxima", capacidadMaxima);

        JSONArray avionesArray = new JSONArray();
        for (Avion avion : listaAviones) {
            avionesArray.put(avion.toJson());
        }
        jsonObject.put("listaAviones", avionesArray);

        return jsonObject.toString();
    }

}
