package GestionAviones;

import java.util.ArrayList;
import java.util.List;

public class Hangar <T extends Avion>{

    private int numeroHangar;
    private List<T> listaAviones;

    public Hangar() {

        this.numeroHangar = 0;
        this.listaAviones = new ArrayList<T>();
    }

    public Hangar(int numeroHangar) {

        this.numeroHangar = numeroHangar;
        this.listaAviones = new ArrayList<T>();
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

    public void agrearAvion(T avion) {
        this.listaAviones.add(avion);
    }

    public void eliminarAvion(T avion) {
        if(this.listaAviones.contains(avion)) {
            this.listaAviones.remove(avion);
            System.out.println("Avion eliminado correctamente del Hangar");
        }
    }

    public List<T> ObtenerListaAviones() {
        return this.listaAviones;
    }

}
