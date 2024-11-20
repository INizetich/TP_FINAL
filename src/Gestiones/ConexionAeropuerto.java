package Gestiones;

import Aeropuerto.Aeropuerto;
import Aviones.Vuelo;
import Config.ConfigAdmin;
import Config.Configs;
import JSON.GestionJSON;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ConexionAeropuerto {
    // Mapa de conexiones: Origen -> (Destino -> IDs de vuelos)
    private static  Map<String, Map<String, Set<String>>> vuelosReservados = new HashMap<>();

    // Registrar la conexión entre dos aeropuertos usando sus códigos internacionales y el ID del vuelo
    public void registrarConexion(String codigoOrigen, String codigoDestino, String idVuelo) {
        vuelosReservados.putIfAbsent(codigoOrigen, new HashMap<>());
        vuelosReservados.get(codigoOrigen).putIfAbsent(codigoDestino, new HashSet<>());
        vuelosReservados.get(codigoOrigen).get(codigoDestino).add(idVuelo);
    }

    public static Map<String, Map<String, Set<String>>> getConexiones() {
        return vuelosReservados;
    }

    public  static void mostrarConexiones() {
        if (!Configs.isFirstRun() || Configs.isFirstRun()) {
            File conexiones = new File("Archivos JSON/ConexionesAeropuertos.json");

            // Verificar si los archivos existen y deserializar
            if (conexiones.exists()) {
                Map<String, Map<String, Set<String>>> conexionesJSON = new HashMap<>();
                try {
                    conexionesJSON = GestionJSON.deserializarConexiones(conexiones.getPath());

                    if (conexionesJSON.isEmpty()) {
                        printCentered("🚫 No se encontraron vuelos reservados.");
                    } else {
                        ConexionAeropuerto.setConexiones(conexionesJSON);
                    }
                } catch (JSONException | IOException e) {
                    printCentered("🚫 Error al deserializar el archivo de conexiones.");
                    e.printStackTrace();
                }
            } else {
                // Si los archivos no existen, los creamos vacíos
                printCentered("🚫 El archivo de vuelos reservados no existe. Creando archivos vacíos...");
                try {
                    if (conexiones.createNewFile()) {
                        printCentered("✔️ Archivo de vuelos reservados creado.");
                    } else {
                        printCentered("🚫 No se pudo crear el archivo de vuelos reservados.");
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            printCentered("🌐✈️  Vuelos reservados ✈️🌐");

            // Iterar sobre las conexiones
            vuelosReservados.forEach((origen, destinos) -> {
                // Concatenar los destinos y los vuelos correspondientes en una sola línea
                StringBuilder conexionInfo = new StringBuilder("🛫 Origen: " + origen);

                destinos.forEach((destino, idVuelos) -> {
                    // Agregar los destinos con los respectivos vuelos
                    conexionInfo.append(" | 🌍 Destino: " + destino + " -> 🛬 Vuelo: " + String.join(", ", idVuelos));
                });

                // Imprimir la información de la conexión de forma bonita
                printCentered(conexionInfo.toString());
            });

            printCentered("==============================================");
            printCentered("¡Viaja con nosotros y disfruta de las mejores conexiones! ✈️🌍");
        }
    }

    public static void setConexiones(Map<String, Map<String, Set<String>>> conexiones) {
        ConexionAeropuerto.vuelosReservados = conexiones;
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

