package GestionAviones;

import java.util.*;

import Excepciones.CapacidadMaximaException;
import Personas.Pasajero;

public class Vuelo {
    private  String idVuelo;
    private String destino; // Nombre del aeropuerto destino
    private String origen;  // Nombre del aeropuerto origen
    private Date horario;
    private Avion avion;
    private PuertaEmbarque puertaEmbarque;
    private EstadoEmbarque estadoEmbarque;
    private Set<Pasajero> listaPasajeros;
    private Set<String>asientos = new HashSet<>();

    public Vuelo() {
        this.idVuelo = generarIdVuelo();
        this.destino = "";
        this.origen = "";
        this.horario = null;
        this.avion = null;
        this.estadoEmbarque = null;
        this.listaPasajeros = new HashSet<>();
        this.puertaEmbarque = PuertaEmbarque.obtenerPuertaAleatoria();
    }

    public Vuelo(String origen, String destino, Date horario, Avion avion) {
        this.idVuelo = generarIdVuelo();
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.avion = avion;
        this.estadoEmbarque = EstadoEmbarque.ABIERTO;
        this.listaPasajeros = new HashSet<>();
        this.puertaEmbarque = PuertaEmbarque.obtenerPuertaAleatoria();
    }

    private String generarIdVuelo() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getOrigen() {
        return origen;
    }


    public String getIdVuelo() {
        return idVuelo;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public EstadoEmbarque getEstadoEmbarque() {
        return estadoEmbarque;
    }

    public void setEstadoEmbarque(EstadoEmbarque estadoEmbarque) {
        this.estadoEmbarque = estadoEmbarque;
    }

    public Set<Pasajero> getListaPasajeros() {
        return listaPasajeros;
    }

    public void setListaPasajeros(Set<Pasajero> listaPasajeros) {
        this.listaPasajeros = listaPasajeros;
    }

    public PuertaEmbarque getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(PuertaEmbarque puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public Set<String> getAsientos() {
        return asientos;
    }

    public boolean agregarPasajero(Pasajero pasajero) throws CapacidadMaximaException {
        if (listaPasajeros.size() < avion.getCapacidadAvion()) {
            listaPasajeros.add(pasajero);
            return true;
        }
        throw new CapacidadMaximaException("El avión llegó a su capacidad máxima.");
    }

    public void mostrarListaPasajeros(){
        Iterator<Pasajero> it = listaPasajeros.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void ocuparAsiento(String asiento){
        asientos.add(asiento);
    }

    public boolean estaAsientoDisponible(String asiento) {
        return !asiento.contains(asiento);
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "idVuelo='" + idVuelo + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", horario=" + horario +
                avion +
                ", puertaEmbarque=" + puertaEmbarque +
                ", estadoEmbarque=" + estadoEmbarque +

                '}';
    }
}
