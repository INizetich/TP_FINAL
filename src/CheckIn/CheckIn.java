package CheckIn;

import Aviones.Vuelo;
import Excepciones.ReservaInexistenteException;
import Excepciones.dniNoEncontradoException;
import Gestiones.SistemaReserva;

import JSON.GestionJSON;
import Personas.Pasajero;
import Utilidades.Utilities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import org.json.JSONException;
//import javazoom.jl.player.Player;


import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder ({"vuelo","numeroAsiento","pasajero","CodigoCheckIn"})
public class CheckIn {
    private static final String Click = "src/Sonidos/Click.mp3";
    @JsonProperty ("vuelo")
    private Vuelo vuelo;
    @JsonProperty("numeroAsiento")
    private String numeroAsiento;
    @JsonProperty("pasajero")
    private Pasajero pasajero;
    @JsonProperty("CodigoCheckIn")
    private static String CodigoCheckIn;


    // Constructor con @JsonCreator y @JsonProperty
    @JsonCreator
    public CheckIn( @JsonProperty("vuelo") Vuelo vuelo,
                   @JsonProperty("numeroAsiento") String numeroAsiento,
                   @JsonProperty("pasajero") Pasajero pasajero) {
        this.vuelo = vuelo;
        this.CodigoCheckIn = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.numeroAsiento = numeroAsiento;
        this.pasajero = pasajero;
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
        try {
            // Deserializar el mapa de reservas desde el archivo JSON
            Map<String, Set<CheckIn>> mapaReservas = GestionJSON.deserializarReservas("Archivos JSON/Check-In.json");



            if (mapaReservas == null || mapaReservas.isEmpty()) {
                printCentered("No hay reservas disponibles en el sistema.");
                return;
            }

            Set<CheckIn> checkIns = mapaReservas.get(dni);

            if (checkIns == null) {
                throw new dniNoEncontradoException("El DNI no se encuentra dentro del sistema de reservas.");
            }

            for (CheckIn checkIn : checkIns) {
                Pasajero pasajero = checkIn.getPasajero();

                // Verificar si el check-in ha sido realizado
                if (pasajero.isCheckIn()) {
                    printCentered("============================");
                    printCentered("🎉 Reserva Confirmada 🎉");
                    printCentered("============================");
                    printCentered("👤 Pasajero: " + pasajero.getNombre() + " " + pasajero.getApellido());
                    printCentered("🆔 DNI: " + pasajero.getDni());

                    // Mostrar detalles del vuelo
                    Vuelo vuelo = checkIn.getVuelo();

                    if (vuelo != null) {
                        printCentered("✈️ Vuelo: " + vuelo.getOrigen() + " ➡️ " + vuelo.getDestino());

                        // Convertir String a LocalDateTime usando el formato ISO
                        String horarioString = vuelo.getHorario(); // Suponiendo que es un String
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                        try {
                            LocalDateTime localDateTime = LocalDateTime.parse(horarioString, inputFormatter);

                            // Formatear la fecha
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            String formattedDate = localDateTime.format(outputFormatter);

                            printCentered("📅 Fecha del Vuelo: " + formattedDate);
                            printCentered("💺 Número de Asiento: " + checkIn.getNumeroAsiento());
                            printCentered("🗳️ Código de Check-In: " + checkIn.getCodigoCheckIn());
                            printCentered("============================");
                        } catch (DateTimeParseException e) {
                            printCentered("Error al parsear la fecha y hora: " + e.getMessage());
                        }
                    }

                    // ClickSonido();
                } else {
                    printCentered("========================================================================");
                    printCentered("❌ La reserva aún no ha sido realizada para " + pasajero.getNombre() + " " + pasajero.getApellido());
                    // ClickSonido();
                }
            }
        } catch (JSONException e) {
            printCentered("Error al mostrar la reserva: " + e.getMessage());
        }
    }







    public static void generarBoleto(String dni, SistemaReserva sistemaReserva) throws ReservaInexistenteException {
        try {
            // Deserializar el mapa de reservas desde el archivo JSON
            Map<String, Set<CheckIn>> mapaReservas = GestionJSON.deserializarReservas("Archivos JSON/Check-In.json");

            Utilities.mostrarCargando();

            if (mapaReservas == null || mapaReservas.isEmpty()) {
                printCentered("No hay reservas disponibles en el sistema.");
                return;
            }

            Set<CheckIn> checkIns = mapaReservas.get(dni);

            if (checkIns == null) {
                throw new ReservaInexistenteException("❌ el dni no se encuentra asociado a ninguna reserva");
            }

            // Iterar sobre el mapa de reservas
            for (CheckIn checkIn : checkIns) {
                Pasajero pasajero = checkIn.getPasajero();

                // Generación del boleto de avión
                if (pasajero.isCheckIn()) {
                    Vuelo vuelo = checkIn.getVuelo();

                    if (vuelo != null) {
                        StringBuilder boleto = new StringBuilder();

                        // Convertir String a LocalDateTime usando el formato ISO
                        String horarioString = vuelo.getHorario(); // Suponiendo que es un String
                        DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                        try {
                            LocalDateTime localDateTime = LocalDateTime.parse(horarioString, inputFormatter);

                            // Formatear la fecha para el boleto
                            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            String formattedDate = localDateTime.format(outputFormatter);

                            // Cabecera del boleto con emojis
                            boleto.append("======================================================================\n");
                            boleto.append("🌟                        BOLETO DE AVION                        🌟\n");
                            boleto.append("======================================================================\n");

                            // Información del pasajero y vuelo con emojis
                            boleto.append("👤 Pasajero: ").append(pasajero.getNombre()).append(" ").append(pasajero.getApellido()).append("\n");
                            boleto.append("🆔 DNI: ").append(pasajero.getDni()).append("\n");
                            boleto.append("🌍 Origen: ").append(vuelo.getOrigen()).append("\n");
                            boleto.append("✈️ Destino: ").append(vuelo.getDestino()).append("\n");
                            boleto.append("📅 Fecha de vuelo: ").append(formattedDate).append("\n"); // Usa el formattedDate
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
                            printCentered(boleto.toString());
                            // ClickSonido();
                            break; // Si se encuentra el boleto, terminamos el ciclo
                        } catch (DateTimeParseException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public static void printCentered(String text) {
        int terminalWidth = 150; // Puedes ajustar este valor según el ancho de tu terminal
        int padding = (terminalWidth - text.length()) / 2;
        String paddedText = " ".repeat(padding) + text;
        System.out.println(paddedText);
    }

    public static void limpiarPantalla() {
        // Imprime 50 líneas vacías para simular la limpieza de pantalla
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }}

    }







