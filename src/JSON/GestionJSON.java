package JSON;

import Aviones.Vuelo;
import CheckIn.CheckIn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class GestionJSON {
 private static ObjectMapper objectMapper = new ObjectMapper();


    public static <T> void serializarLista(List<T> lista, String archivoDestino) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Registrar el módulo para soporte de fechas
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Para serializar como ISO-8601

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(archivoDestino), lista);
            System.out.println("Serialización exitosa en el archivo: " + archivoDestino);
        } catch (Exception e) {
            System.err.println("Error al serializar la lista: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Método para serializar un Set de objetos a un archivo JSON
    public static void serializarSet(Set<?> set, String nombreArchivo) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try{
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(nombreArchivo), set);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    ///metodo para serializar un map de objetos a un archivo JSON
    public static <K, V> void serializarMapa(Map<K, V> mapa, String nombreArchivo) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(nombreArchivo), mapa);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al serializar el mapa.");
        }
    }


    // Método para deserializar un archivo JSON a una lista de objetos
    public static  <T> List<T> deserializarLista(Class<T> clase, String nombreArchivo) {
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructCollectionType(List.class, clase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Método para deserializar un archivo JSON a un Set de objetos
    public static  <T> Set<T> deserializarSet(Class<T> clase, String nombreArchivo) {
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructCollectionType(Set.class, clase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static <K, V> Map<K, V> deserializarMapa(Class<K> claveClase, Class<V> valorClase, String nombreArchivo) {
        try {
            // Habilitar el módulo para Java 8 Date/Time
            objectMapper.registerModule(new JavaTimeModule());

            return objectMapper.readValue(new File(nombreArchivo),
                    objectMapper.getTypeFactory().constructMapType(Map.class, claveClase, valorClase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    public static List<Vuelo> deserializarVuelos(String archivoFuente) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule()); // Soporte para fechas

        try {
            return mapper.readValue(new File(archivoFuente), new TypeReference<List<Vuelo>>() {});
        } catch (Exception e) {
            System.err.println("Error al deserializar vuelos: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
        }
    }



    public static Map<String, Set<CheckIn>> deserializarReservas(String nombreArchivo) {
        try {
            // Crear el ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            // Deserializar directamente en un Map<String, List<CheckIn>>
            Map<String, List<CheckIn>> reservasTemp = objectMapper.readValue(
                    new File(nombreArchivo),
                    new TypeReference<Map<String, List<CheckIn>>>() {}
            );

            // Convertir las listas en sets
            Map<String, Set<CheckIn>> reservas = new HashMap<>();
            for (Map.Entry<String, List<CheckIn>> entry : reservasTemp.entrySet()) {
                reservas.put(entry.getKey(), new HashSet<>(entry.getValue()));
            }

            return reservas;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*public static List<Vuelo> deserializarVuelos(String path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Vuelo> vuelos = mapper.readValue(new File(path), new TypeReference<List<Vuelo>>(){});
            return vuelos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/



    public static Map<String, Map<String, Set<String>>> deserializarVuelosReservados(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Tipo del mapa genérico
        TypeReference<Map<String, Map<String, Set<String>>>> typeRef = new TypeReference<>() {};

        // Leer y deserializar desde el archivo JSON
        return mapper.readValue(new File(filePath), typeRef);
    }


}








