package Gestiones;

import java.util.*;

public class ConexionAeropuerto {
    // Mapa de conexiones: Origen -> (Destino -> IDs de vuelos)
    private static final Map<String, Map<String, Set<String>>> conexiones = new HashMap<>();

    // Registrar la conexión entre dos aeropuertos usando sus códigos internacionales y el ID del vuelo
    public void registrarConexion(String codigoOrigen, String codigoDestino, String idVuelo) {
        conexiones.putIfAbsent(codigoOrigen, new HashMap<>());
        conexiones.get(codigoOrigen).putIfAbsent(codigoDestino, new HashSet<>());
        conexiones.get(codigoOrigen).get(codigoDestino).add(idVuelo);
    }

    // Mostrar conexiones con IDs de vuelos en una sola línea
    public static void mostrarConexiones() {
        System.out.println("Conexiones entre aeropuertos del vuelo: ");

        // Iterar sobre las conexiones
        conexiones.forEach((origen, destinos) -> {
            // Concatenar los destinos y los vuelos correspondientes en una sola línea
            StringBuilder conexionInfo = new StringBuilder("Origen: " + origen);

            destinos.forEach((destino, idVuelos) -> {
                conexionInfo.append(" | Destino: " + destino + " -> Vuelos: " + String.join(", ", idVuelos));
            });

            // Imprimir la información de la conexión en una sola línea
            System.out.println(conexionInfo.toString());
        });
    }
}
