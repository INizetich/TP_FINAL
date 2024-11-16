package JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GestionJSON {

    // Método para serializar un Set de objetos a un archivo JSON
    public static void serializarSet(Set<?> set, String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(nombreArchivo), set);
            System.out.println("Datos serializados a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para serializar una lista de objetos a un archivo JSON
    public static void serializarLista(List<?> lista, String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(nombreArchivo), lista);
            System.out.println("Datos serializados a " + nombreArchivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para deserializar un archivo JSON a un Set de objetos
    public static  <T> Set<T> deserializarSet(Class<T> clase, String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructCollectionType(Set.class, clase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para deserializar un archivo JSON a una lista de objetos
    public static  <T> List<T> deserializarLista(Class<T> clase, String nombreArchivo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(nombreArchivo), objectMapper.getTypeFactory().constructCollectionType(List.class, clase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
