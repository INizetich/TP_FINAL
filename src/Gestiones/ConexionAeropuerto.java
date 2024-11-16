package Gestiones;

import java.util.*;

public class ConexionAeropuerto {
    // Mapa de conexiones: Origen -> (Destino -> IDs de vuelos)
    private static  Map<String, Map<String, Set<String>>> conexiones = new HashMap<>();

    // Registrar la conexión entre dos aeropuertos usando sus códigos internacionales y el ID del vuelo
    public void registrarConexion(String codigoOrigen, String codigoDestino, String idVuelo) {
        conexiones.putIfAbsent(codigoOrigen, new HashMap<>());
        conexiones.get(codigoOrigen).putIfAbsent(codigoDestino, new HashSet<>());
        conexiones.get(codigoOrigen).get(codigoDestino).add(idVuelo);
    }

    public static Map<String, Map<String, Set<String>>> getConexiones() {
        return conexiones;
    }

    public static void mostrarConexiones() {
        System.out.println("🌐✈️ vuelos reservados ✈️🌐");

        if (conexiones.isEmpty()) {
            System.out.println("🚫 No se encuentra ningún vuelo reservado en este momento. 🛫");
        }else if (!conexiones.isEmpty()) {
            // Iterar sobre las conexiones
            conexiones.forEach((origen, destinos) -> {
                // Concatenar los destinos y los vuelos correspondientes en una sola línea
                StringBuilder conexionInfo = new StringBuilder("🛫 Origen: " + origen);

                destinos.forEach((destino, idVuelos) -> {
                    // Agregar los destinos con los respectivos vuelos
                    conexionInfo.append(" | 🌍 Destino: " + destino + " -> 🛬 Vuelo: " + String.join(", ", idVuelos));
                });

                // Imprimir la información de la conexión de forma bonita
                System.out.println(conexionInfo.toString());
            });

            System.out.println("==============================================");
            System.out.println("¡Viaja con nosotros y disfruta de los mejores vuelos a un precio accesible! ✈️🌍");
        }
        }

}
