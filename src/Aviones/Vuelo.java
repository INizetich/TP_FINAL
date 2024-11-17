package Aviones;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Enums.EstadoEmbarque;
import Enums.PuertaEmbarque;
import Excepciones.CapacidadMaximaException;
import Personas.Pasajero;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idVuelo", "origen", "destino", "horario", "estadoEmbarque", "avion", "puertaEmbarque", "listaPasajeros", "asientos"})
public class Vuelo {
    @JsonProperty("idVuelo")
    private String idVuelo;

    @JsonProperty("origen")
    private String origen;

    @JsonProperty("destino")
    private String destino;

    @JsonProperty("puertaEmbarque")
    private PuertaEmbarque puertaEmbarque;

    @JsonProperty("horario")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime horario;

    @JsonProperty("avion")
    private Avion avion;

    private EstadoEmbarque estadoEmbarque;

    @JsonProperty("listaPasajeros")
    private Set<Pasajero> listaPasajeros;

    private Set<String> asientos;

    public Vuelo(String idVuelo) {
        this.idVuelo = idVuelo;
        this.origen = "";
        this.destino = "";
        this.horario = null;
        this.avion = null;
        this.estadoEmbarque = null;
        this.listaPasajeros = new HashSet<>();
        this.puertaEmbarque = PuertaEmbarque.obtenerPuertaAleatoria();
        this.asientos = new HashSet<>();
    }

    @JsonCreator
    public Vuelo(
            @JsonProperty("id") String id,
            @JsonProperty("origen") String origen,
            @JsonProperty("destino") String destino,
            @JsonProperty("horario") LocalDateTime horario,
            @JsonProperty("estadoEmbarque") EstadoEmbarque estadoEmbarque,
            @JsonProperty("avion") Avion avion
    ) {
        this.idVuelo = id;
        this.origen = origen;
        this.destino = destino;
        this.horario = horario;
        this.estadoEmbarque = estadoEmbarque;
        this.avion = avion;
        this.puertaEmbarque = PuertaEmbarque.obtenerPuertaAleatoria();
        this.listaPasajeros = new HashSet<>();
        this.asientos = new HashSet<>();
    }

    // Métodos y validaciones de pasajeros y asientos
    public boolean agregarPasajero(Pasajero pasajero) throws CapacidadMaximaException {
        if (listaPasajeros.contains(pasajero)) {
            System.out.println("El pasajero ya está registrado en este vuelo.");
            return false;
        }
        if (listaPasajeros.size() < avion.getCapacidadAvion()) {
            listaPasajeros.add(pasajero);
            return true;
        }
        throw new CapacidadMaximaException("El avión ha alcanzado su capacidad máxima.");
    }

    public void mostrarListaPasajeros() {
        Iterator<Pasajero> it = listaPasajeros.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    public void ocuparAsiento(String asiento) {
        if (!asientos.contains(asiento)) {
            asientos.add(asiento);
        }
    }

    public boolean estaAsientoDisponible(String asiento) {
        return !asientos.contains(asiento);
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "idVuelo='" + idVuelo + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", horario=" + horario +
                ", avion=" + avion +
                ", puertaEmbarque=" + puertaEmbarque +
                ", estadoEmbarque=" + estadoEmbarque +
                '}';
    }

    public String getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(String idVuelo) {
        this.idVuelo = idVuelo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public PuertaEmbarque getPuertaEmbarque() {
        return puertaEmbarque;
    }

    public void setPuertaEmbarque(PuertaEmbarque puertaEmbarque) {
        this.puertaEmbarque = puertaEmbarque;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
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

    public Set<String> getAsientos() {
        return asientos;
    }

    public void setAsientos(Set<String> asientos) {
        this.asientos = asientos;
    }
}
