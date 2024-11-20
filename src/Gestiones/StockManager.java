package Gestiones;

import Config.ConfigAdmin;
import JSON.GestionJSON;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StockManager {
    // Estructura de stock: Categoría -> (Ítem -> Cantidad)
    private static Map<String, Map<String, Integer>> stock = new HashMap<>();

    // Agregar al stock
    public static void agregarAStock(String categoria, String item, int cantidad) {
        // Verificar si es la primera ejecución del administrador
        if (!ConfigAdmin.isFirstRunAdmin()) return;

        File archivoStock = new File("Archivos JSON/Stock.json");
        Map<String, Map<String, Integer>> stock = new HashMap<>();

        // Deserializar o inicializar el archivo de stock
        if (archivoStock.exists()) {
            try {
                stock = GestionJSON.deserializarStock("Archivos JSON/Stock.json");
                if (stock == null || stock.isEmpty()) {
                    System.out.println("🚫 No se encontraron productos deserializados.");
                    return;
                }
            } catch (Exception e) {
                System.err.println("🚫 Error al deserializar el archivo de productos.");
                e.printStackTrace();
                return;
            }
        } else {
            // Crear un archivo vacío si no existe
            System.out.println("🚫 El archivo de productos no existe. Creando archivo vacío...");
            try {
                if (archivoStock.createNewFile()) {
                    System.out.println("✔️ Archivo de productos creado.");
                } else {
                    System.err.println("🚫 No se pudo crear el archivo de productos.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("🚫 Error al crear el archivo de productos.");
                e.printStackTrace();
                return;
            }
        }
        stock.putIfAbsent(categoria, new HashMap<>());
        Map<String, Integer> items = stock.get(categoria);

        // Agrega o actualiza la cantidad del ítem en la categoría
        items.put(item, items.getOrDefault(item, 0) + cantidad);


        // Serializar el stock actualizado
        GestionJSON.serializarMapa(stock, "Archivos JSON/Stock.json");
        System.out.println("✔️ Archivo de productos actualizado correctamente.");


    }

    public static Map<String, Map<String, Integer>> getStock() {
        return stock;
    }

    public static void setStock(Map<String, Map<String, Integer>> stock) {
        StockManager.stock = stock;
    }







    public static void eliminarDeStock(String categoria, String item, int cantidad) {
        // Verificar si es la primera ejecución del administrador
        if (!ConfigAdmin.isFirstRunAdmin()) return;

        File archivoStock = new File("Archivos JSON/Stock.json");
        Map<String, Map<String, Integer>> stock = new HashMap<>();

        // Deserializar o inicializar el archivo de stock
        if (archivoStock.exists()) {
            try {
                stock = GestionJSON.deserializarStock("Archivos JSON/Stock.json");
                if (stock == null || stock.isEmpty()) {
                    System.out.println("🚫 No se encontraron productos deserializados.");
                    return;
                }
            } catch (Exception e) {
                System.err.println("🚫 Error al deserializar el archivo de productos.");
                e.printStackTrace();
                return;
            }
        } else {
            // Crear un archivo vacío si no existe
            System.out.println("🚫 El archivo de productos no existe. Creando archivo vacío...");
            try {
                if (archivoStock.createNewFile()) {
                    System.out.println("✔️ Archivo de productos creado.");
                } else {
                    System.err.println("🚫 No se pudo crear el archivo de productos.");
                    return;
                }
            } catch (IOException e) {
                System.err.println("🚫 Error al crear el archivo de productos.");
                e.printStackTrace();
                return;
            }
        }

        // Verificar existencia de categoría e ítem
        if (stock.containsKey(categoria)) {
            Map<String, Integer> items = stock.get(categoria);
            if (items.containsKey(item)) {
                int stockActual = items.get(item);
                if (stockActual >= cantidad) {
                    items.put(item, stockActual - cantidad);

                    // Eliminar ítem si la cantidad es 0
                    if (items.get(item) == 0) {
                        items.remove(item);
                        System.out.println("✔️ Producto eliminado del stock.");
                    }

                    // Eliminar categoría si queda vacía
                    if (items.isEmpty()) {
                        stock.remove(categoria);
                        System.out.println("✔️ Categoría eliminada por quedar vacía.");
                    }

                } else {
                    System.err.println("🚫 No hay suficiente stock para eliminar.");
                    return;
                }
            } else {
                System.err.println("🚫 El ítem no existe en la categoría especificada.");
                return;
            }
        } else {
            System.err.println("🚫 La categoría especificada no existe.");
            return;
        }

        // Serializar el stock actualizado
        GestionJSON.serializarMapa(stock, "Archivos JSON/Stock.json");
        System.out.println("✔️ Archivo de productos actualizado correctamente.");
    }


    // Consultar el stock actual (todas las categorías y sus ítems)
        public static Map<String, Map<String, Integer>> obtenerStock () {
            return new HashMap<>(stock); // Devuelve una copia del mapa para evitar modificaciones externas
        }

        // Consultar cantidad de un ítem específico dentro de una categoría
        public static int consultarStock (String categoria, String item){
            if (stock.containsKey(categoria)) {
                return stock.get(categoria).getOrDefault(item, 0);
            }
            return 0; // Si no se encuentra la categoría o el ítem, retorna 0
        }

        // Hardcodear algunos ítems en el stock
        private static void hardcodearStock () {
            // Bebidas
            agregarAStock("Bebidas", "Agua mineral", 10);
            agregarAStock("Bebidas", "Gaseosa", 20);
            agregarAStock("Bebidas", "Jugo natural", 15);

            // Comida
            agregarAStock("Comida", "Empanada", 12);
            agregarAStock("Comida", "Sandwich", 8);
            agregarAStock("Comida", "Papas fritas", 20);
            agregarAStock("Comida", "Hot Dog", 10);
            agregarAStock("Comida", "Porción pizza", 6);

            // Artículos varios
            agregarAStock("Artículos varios", "Revista", 5);
            agregarAStock("Artículos varios", "Chicle", 50);
            agregarAStock("Artículos varios", "Encendedor", 10);
        }

        // Cargar el stock automáticamente al inicio
        static {
            hardcodearStock();
        }
    }

