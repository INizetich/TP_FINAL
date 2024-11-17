package CheckIn;

import Aviones.Vuelo;
import Excepciones.ReservaInexistenteException;
import Excepciones.dniNoEncontradoException;
import Gestiones.SistemaReserva;
import JSON.GestionJSON;
import Personas.Pasajero;
import Utilidades.Utilities;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CheckIn {
    private static final String Click = "src/Sonidos/Click.mp3";
    private Vuelo vuelo;
    private String numeroAsiento;
    private Pasajero pasajero;
    private static String CodigoCheckIn;


    // Constructor con @JsonCreator y @JsonProperty
    @JsonCreator
    public CheckIn(@JsonProperty("vuelo") Vuelo vuelo,
                   @JsonProperty("numeroAsiento") String numeroAsiento,
                   @JsonProperty("pasajero") Pasajero pasajero) {
        this.vuelo = vuelo;
        this.numeroAsiento = numeroAsiento;
        this.pasajero = pasajero;
        // Generar Código de CheckIn automáticamente
        this.CodigoCheckIn = UUID.randomUUID().toString().substring(0, 16);
    }

    public Vuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(Vuelo vuelo) {
        this.vuelo = vuelo;
    }

    public String getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(String numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }



    public String getCodigoCheckIn() {
        return CodigoCheckIn;
    }

    public void setCodigoCheckIn(String codigoPasajero) {
        this.CodigoCheckIn = codigoPasajero;
    }

    @Override
    public String toString() {
        return "CheckIn{" +
                vuelo +
                 pasajero +
                '}';
    }


    public static void mostrarReserva(String dni, SistemaReserva sistemaReserva) throws dniNoEncontradoException {
        boolean encontrado = false;

        try {
            // Deserializar el mapa de reservas desde el archivo JSON
            Map<String, Set<CheckIn>> mapaReservas = GestionJSON.deserializarReservas("Archivos JSON/Check-In.json");

            if (mapaReservas == null || mapaReservas.isEmpty()) {
                System.out.println("No hay reservas disponibles en el sistema.");
                return;
            }

            // Recorrer el mapa de reservas
            for (Set<CheckIn> checkIns : mapaReservas.values()) {
                for (CheckIn checkIn : checkIns) {
                    Pasajero pasajero = checkIn.getPasajero();

                    // Limpiar espacios y comparar el DNI
                    if (pasajero.getDni().trim().equalsIgnoreCase(dni.trim())) {
                        // Verificar si el check-in ha sido realizado
                        if (pasajero.isCheckIn()) {
                            System.out.println("============================");
                            System.out.println("🎉 Reserva Confirmada 🎉");
                            System.out.println("============================");
                            System.out.println("👤 Pasajero: " + pasajero.getNombre() + " " + pasajero.getApellido());
                            System.out.println("🆔 DNI: " + pasajero.getDni());

                            // Mostrar detalles del vuelo
                            Vuelo vuelo = checkIn.getVuelo();
                            System.out.println("✈️ Vuelo: " + vuelo.getOrigen() + " ➡️ " + vuelo.getDestino());
                            System.out.println("📅 Fecha del Vuelo: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(vuelo.getHorario()));
                            System.out.println("💺 Número de Asiento: " + checkIn.getNumeroAsiento());
                            System.out.println("🗳️ Código de Check-In: " + checkIn.getCodigoCheckIn());

                            System.out.println("============================");
                            ClickSonido();
                        } else {
                            System.out.println("========================================================================");
                            System.out.println("❌ La reserva aún no ha sido realizada para " + pasajero.getNombre() + " " + pasajero.getApellido());
                            ClickSonido();

                        }
                        encontrado = true;
                        break;
                    }
                }
                if (encontrado) {
                    break;
                }
            }

            if (!encontrado) {
                throw new dniNoEncontradoException("El DNI no se encuentra dentro del sistema de reservas.");

            }
        } catch (Exception e) {
            System.out.println("Error al mostrar la reserva: " + e.getMessage());
        }
    }




    public static void generarBoleto(String dni, SistemaReserva sistemaReserva) throws ReservaInexistenteException {
        Utilities.mostrarCargando();

        // Variable para verificar si el DNI fue encontrado
        boolean encontrado = false;

        // Iterar sobre el mapa de reservas
        for (Set<CheckIn> checkIns : sistemaReserva.getMapaReservas().values()) {
            for (CheckIn checkIn : checkIns) {
                Pasajero pasajero = checkIn.getPasajero();

                // Verificar si el DNI coincide
                if (pasajero.getDni().trim().equalsIgnoreCase(dni.trim())) {
                    encontrado = true; // Se ha encontrado el pasajero

                    // Generación del boleto de avión
                    Vuelo vuelo = checkIn.getVuelo();
                    StringBuilder boleto = new StringBuilder();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    // Cabecera del boleto con emojis
                    boleto.append("======================================================================\n");
                    boleto.append("🌟                        BOLETO DE AVION                        🌟\n");
                    boleto.append("======================================================================\n");

                    // Información del pasajero y vuelo con emojis
                    boleto.append("👤 Pasajero: ").append(pasajero.getNombre()).append(" ").append(pasajero.getApellido()).append("\n");
                    boleto.append("🆔 DNI: ").append(pasajero.getDni()).append("\n");
                    boleto.append("🌍 Origen: ").append(vuelo.getOrigen()).append("\n");
                    boleto.append("✈️ Destino: ").append(vuelo.getDestino()).append("\n");
                    boleto.append("📅 Fecha de vuelo: ").append(formatoFecha.format(vuelo.getHorario())).append("\n");
                    boleto.append("🪑 Número de asiento: ").append(pasajero.getNroAsiento()).append("\n");
                    boleto.append("🚪 Puerta de embarque: ").append(vuelo.getPuertaEmbarque()).append("\n");

                    // Pie de boleto con emojis
                    boleto.append("======================================================================\n");
                    boleto.append("🔑 Código único de identificación: ").append(checkIn.getCodigoCheckIn()).append("\n");
                    boleto.append("======================================================================\n");

                    // Despedida con emojis
                    boleto.append("🎉 *¡Buen viaje! Gracias por volar con nosotros.* ✈️🌍\n");
                    boleto.append("======================================================================");

                    // Mostrar el boleto
                    System.out.println(boleto.toString());
                    ClickSonido();
                    break; // Si se encuentra el boleto, terminamos el ciclo
                }
            }
            if (encontrado) {
                break; // Si ya se encontró el DNI, terminamos el ciclo externo
            }
        }

        // Si no se encuentra el DNI en ninguna reserva, lanzamos la excepción
        if (!encontrado) {
            throw new ReservaInexistenteException("El boleto de avión no tiene ningún DNI asociado que haya realizado una reserva.");

        }  ClickSonido();

    }


    /// //////////////////////////////////////////////////////////////////////////
    /// /// METODOS PARA EL SONIDO
    private static void ClickSonido() {
        Thread audioThread = new Thread(() -> {
            try (FileInputStream fis = new FileInputStream(Click)) {
                Player player = new Player(fis);
                player.play();
            } catch (Exception e) {
                System.out.println("Error al reproducir el archivo: " + e.getMessage());
            }
        });
        audioThread.setDaemon(true); // El hilo se detendrá automáticamente cuando termine el programa
        audioThread.start();
    }



}
