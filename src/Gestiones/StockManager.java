package Gestiones;

import java.util.HashMap;
import java.util.Map;

public class StockManager {
    // Estructura de stock: Categoría -> (Ítem -> Cantidad)
    private static Map<String, Map<String, Integer>> stock = new HashMap<>();

    // Agregar al stock
    public static void agregarAStock(String categoria, String item, int cantidad) {
        // Verifica si la categoría ya existe, si no, la crea
        stock.putIfAbsent(categoria, new HashMap<>());
        Map<String, Integer> items = stock.get(categoria);

        // Agrega o actualiza la cantidad del ítem en la categoría
        items.put(item, items.getOrDefault(item, 0) + cantidad);
    }

    // Eliminar del stock
    public static boolean eliminarDeStock(String categoria, String item, int cantidad) {
        if (stock.containsKey(categoria)) {
            Map<String, Integer> items = stock.get(categoria);
            if (items.containsKey(item) && items.get(item) >= cantidad) {
                items.put(item, items.get(item) - cantidad);

                // Si el ítem queda con 0, lo eliminamos de la categoría
                if (items.get(item) == 0) {
                    items.remove(item);
                }

                // Si la categoría queda vacía, también la eliminamos
                if (items.isEmpty()) {
                    stock.remove(categoria);
                }

                return true;
            }
        }
        return false; // No hay suficiente stock o el ítem no existe
    }

    // Consultar el stock actual (todas las categorías y sus ítems)
    public static Map<String, Map<String, Integer>> obtenerStock() {
        return new HashMap<>(stock); // Devuelve una copia del mapa para evitar modificaciones externas
    }

    // Consultar cantidad de un ítem específico dentro de una categoría
    public static int consultarStock(String categoria, String item) {
        if (stock.containsKey(categoria)) {
            return stock.get(categoria).getOrDefault(item, 0);
        }
        return 0; // Si no se encuentra la categoría o el ítem, retorna 0
    }

    // Hardcodear algunos ítems en el stock
    private static void hardcodearStock() {
        agregarAStock("Bebidas", "Agua mineral", 10);
        agregarAStock("Bebidas", "Gaseosa", 20);
        agregarAStock("Bebidas", "Jugo natural", 15);

        agregarAStock("Snacks", "Papas fritas", 25);
        agregarAStock("Snacks", "Chocolate", 10);
    }