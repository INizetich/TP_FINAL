package org.json;

import Aviones.Avion;
import Aviones.Hangar;
import Aviones.Vuelo;
import CheckIn.CheckIn;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GestionJSON {

    // Método para serializar una lista en un archivo JSON
    public static <T> void serializarLista(List<T> lista, String archivoDestino) {
        JSONArray jsonArray = new JSONArray(lista); // Convierte la lista a JSONArray
        try (FileWriter fileWriter = new FileWriter(archivoDestino)) {
            fileWriter.write(jsonArray.toString(4)); // Serializa con indentación
            System.out.println("Serialización exitosa en el archivo: " + archivoDestino);
        } catch (IOException e) {
            System.err.println("Error al serializar la lista: " + e.getMessage());
        }
    }

    // Método para serializar un Set de objetos a un archivo JSON
    public static void serializarSet(Set<?> set, String nombreArchivo) {
        JSONArray jsonArray = new JSONArray(set); // Convierte el set a JSONArray
        try (FileWriter fileWriter = new FileWriter(nombreArchivo)) {
            fileWriter.write(jsonArray.toString(4)); // Serializa con indentación
            System.out.println("Serialización exitosa en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al serializar el set: " + e.getMessage());
        }
    }

    // Método para serializar un Map de objetos a un archivo JSON
    public static <K, V> void serializarMapa(Map<K, V> mapa, String nombreArchivo) {
        JSONObject jsonObject = new JSONObject(mapa); // Convierte el mapa a JSONObject
        try (FileWriter fileWriter = new FileWriter(nombreArchivo)) {
            fileWriter.write(jsonObject.toString(4)); // Serializa con indentación
            System.out.println("Serialización exitosa en el archivo: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al serializar el mapa: " + e.getMessage());
        }
    }

    // Método para deserializar un archivo JSON a una lista de objetos
    public static <T> List<T> deserializarLista(Class<T> clase, String nombreArchivo) {
        try {
            String contenido = Files.readString(Paths.get(nombreArchivo)); // Leer el contenido del archivo
            JSONArray jsonArray = new JSONArray(contenido); // Crear un JSONArray
            List<T> lista = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                lista.add((T) jsonArray.get(i)); // Convertir cada elemento
            }
            return lista;
        } catch (IOException e) {
            System.err.println("Error al deserializar la lista: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Método para deserializar un archivo JSON a un Set de objetos
    public static <T> Set<T> deserializarSet(Class<T> clase, String nombreArchivo) {
        try {
            String contenido = Files.readString(Paths.get(nombreArchivo)); // Leer el contenido del archivo
            JSONArray jsonArray = new JSONArray(contenido); // Crear un JSONArray
            Set<T> set = new HashSet<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                set.add((T) jsonArray.get(i)); // Convertir cada elemento
            }
            return set;
        } catch (IOException e) {
            System.err.println("Error al deserializar el set: " + e.getMessage());
        }
        return new HashSet<>();
    }

    // Método para deserializar un archivo JSON a un mapa
    public static <K, V> Map<K, V> deserializarMapa(Class<K> claveClase, Class<V> valorClase, String nombreArchivo) {
        try {
            String contenido = Files.readString(Paths.get(nombreArchivo)); // Leer el contenido del archivo
            JSONObject jsonObject = new JSONObject(contenido); // Crear un JSONObject
            Map<K, V> mapa = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                mapa.put((K) key, (V) jsonObject.get(key)); // Convertir cada entrada
            }
            return mapa;
        } catch (IOException e) {
            System.err.println("Error al deserializar el mapa: " + e.getMessage());
        }
        return new HashMap<>();
    }

    // Método para deserializar vuelos
    public static List<Vuelo> deserializarVuelos(String archivoFuente) {
        try {
            String contenido = Files.readString(Paths.get(archivoFuente));
            JSONArray jsonArray = new JSONArray(contenido);
            List<Vuelo> vuelos = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject vueloJson = jsonArray.getJSONObject(i);
                Vuelo vuelo = new Vuelo();
                vuelo.fromJson(vueloJson); // Asumiendo que tienes un método fromJson en la clase Vuelo
                vuelos.add(vuelo);
            }
            return vuelos;
        } catch (IOException e) {
            System.err.println("Error al deserializar vuelos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Método para deserializar reservas
    public static Map<String, Set<CheckIn>> deserializarReservas(String nombreArchivo) {
        try {
            String contenido = Files.readString(Paths.get(nombreArchivo));
            JSONObject jsonObject = new JSONObject(contenido);
            Map<String, Set<CheckIn>> reservas = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                Set<CheckIn> checkIns = new HashSet<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject checkInJson = jsonArray.getJSONObject(i);
                    CheckIn checkIn = new CheckIn();
                    checkIn.fromJson(checkInJson); // Asumiendo que tienes un método fromJson en CheckIn
                    checkIns.add(checkIn);
                }
                reservas.put(key, checkIns);
            }
            return reservas;
        } catch (IOException e) {
            System.err.println("Error al deserializar reservas: " + e.getMessage());
        }
        return new HashMap<>();
    }

    // Método para deserializar hangares
    public static List<Hangar<Avion>> deserializarHangares(String archivoJson) {
        try {
            String contenido = Files.readString(Paths.get(archivoJson));
            JSONArray jsonArray = new JSONArray(contenido);
            List<Hangar<Avion>> hangares = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject hangarJson = jsonArray.getJSONObject(i);
                Hangar<Avion> hangar = new Hangar<>();
                hangar.fromJson(hangarJson); // Asumiendo que tienes un método fromJson en Hangar
                hangares.add(hangar);
            }
            return hangares;
        } catch (IOException e) {
            System.err.println("Error al deserializar hangares: " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
