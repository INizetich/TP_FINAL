package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class GestionJSON {
 private static ObjectMapper objectMapper = new ObjectMapper();


    // Método para serializar una lista de objetos a un archivo JSON
    public static void serializarLista(List<?> lista, String nombreArchivo) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(nombreArchivo), lista);


        } catch (IOException e) {
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










    ///metoro para serializar un map de objetos a un archivo JSON
    public static <K, V> void serializarMapa(Map<K, V> mapa, String nombreArchivo) {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File(nombreArchivo), mapa);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al serializar el mapa.");
        }
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

    // Método para deserializar un archivo JSON a una lista de objetos
    public static  <T> List<T> deserializarLista(Class<T> clase, String nombreArchivo) {
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructCollectionType(List.class, clase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static <K, V> Map<K, V> deserializarMapa(Class<K> claveClase, Class<V> valorClase, String nombreArchivo) {
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructMapType(Map.class, claveClase, valorClase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }





}
