package JSON;

import Aviones.Avion;
import Aviones.Hangar;
import Aviones.Vuelo;
import CheckIn.CheckIn;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class GestionJSON {
    private static ObjectMapper objectMapper = new ObjectMapper();


    public static <T> void serializarLista(List<T> lista, String nombreArchivo) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // habilitamos el mapper para que escriba el json con indentacion

        try {
            mapper.writeValue(new File(nombreArchivo), lista); // Sobrescribe el archivo
        } catch (IOException e) {
            System.err.println("Error al serializar lista: " + e.getMessage());
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


    public static <T> Set<T> deserializarSetEmpleados(Class<T> clazz, String rutaArchivo) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TypeFactory typeFactory = mapper.getTypeFactory();
        File archivo = new File(rutaArchivo);

        // Validar existencia del archivo
        if (!archivo.exists()) {
            throw new FileNotFoundException("El archivo " + rutaArchivo + " no fue encontrado.");
        }

        // Leer y deserializar el contenido
        return mapper.readValue(archivo, typeFactory.constructCollectionType(Set.class, clazz));
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




    // Método estático para deserializar las conexiones desde un archivo JSON
    public static Map<String, Map<String, Set<String>>> deserializarConexiones(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Leer el archivo y convertirlo al tipo esperado
        return objectMapper.readValue(
                new File(filePath),
                new TypeReference<Map<String, Map<String, Set<String>>>>() {}
        );
    }





    public static List<Hangar<Avion>> deserializarHangares(String archivoJson) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            // Deserializar el archivo JSON en una List<Hangar<Avion>>
            List<Hangar<Avion>> hangares = mapper.readValue(new File(archivoJson), new TypeReference<List<Hangar<Avion>>>(){});
            System.out.println("Deserialización exitosa.");
            return hangares;
        } catch (IOException e) {
            System.err.println("Error al deserializar los hangares: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }


    public static Map<String, Map<String, Integer>> deserializarStock(String nombreArchivo) {
        try {
            // Registrar módulo de soporte adicional si es necesario
            objectMapper.registerModule(new JavaTimeModule());

            // Deserializar usando TypeReference para el tipo específico
            return objectMapper.readValue(
                    new File(nombreArchivo),
                    new TypeReference<Map<String, Map<String, Integer>>>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al deserializar el stock: " + e.getMessage());
        }
        return null; // Retorna null en caso de error
    }


}