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

    public static void agregarAStock(String categoria, String item, int cantidad) {
        // Verificar si es la primera ejecución del administrador
        if (!ConfigAdmin.isFirstRunAdmin()) return;

        File archivoStock = new File("Archivos JSON/Stock.json");

        // Deserializar el archivo de stock solo una vez
        if (archivoStock.exists()) {
            try {
                // Deserializamos el stock
                StockManager.setStock(GestionJSON.deserializarStock("Archivos JSON/Stock.json"));
                if (stock == null || stock.isEmpty()) {
                    printCentered("🚫 No se encontraron productos deserializados.");
                    return;
                }
            } catch (Exception e) {
                System.err.println("🚫 Error al deserializar el archivo de productos.");
                e.printStackTrace();
                return;
            }
        } else {
            // Crear un archivo vacío si no existe
            printCentered("🚫 El archivo de productos no existe. Creando archivo vacío...");
            try {
                if (archivoStock.createNewFile()) {
                    printCentered("✔️ Archivo de productos creado.");
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

        // Verificar stock antes de agregar


        // Asegurarse de que la categoría exista en el stock
        if (!stock.containsKey(categoria)) {
            stock.put(categoria, new HashMap<>());
        }
        Map<String, Integer> items = stock.get(categoria);

        // Verificar stock del item
        int stockActual = items.getOrDefault(item, 0);

        // Agregar la cantidad al stock
        items.put(item, stockActual + cantidad);



        // Serializar el stock actualizado
        try {
            GestionJSON.serializarMapa(stock, "Archivos JSON/Stock.json");
            printCentered("✔️ Archivo de productos actualizado correctamente.");
        } catch (Exception e) {
            System.err.println("🚫 Error al serializar el archivo de productos.");
            e.printStackTrace();
        }
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
                    printCentered("🚫 No se encontraron productos deserializados.");
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
                    printCentered("✔️ Archivo de productos creado.");
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
                        printCentered("✔️ Producto eliminado del stock.");
                    }

                    // Eliminar categoría si queda vacía
                    if (items.isEmpty()) {
                        stock.remove(categoria);
                        printCentered("✔️ Categoría eliminada por quedar vacía.");
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




    // Consultar cantidad de un ítem específico dentro de una categoría
    public static int consultarStock (String categoria, String item){
        if (stock.containsKey(categoria)) {
            return stock.get(categoria).getOrDefault(item, 0);
        }
        return 0; // Si no se encuentra la categoría o el ítem, retorna 0
    }

    public static void agregarProductos(){
        stock = hardcodearStock();
    }

    // Hardcodear algunos ítems en el stock
    private static Map<String, Map<String, Integer>> hardcodearStock() {
        Map<String, Map<String, Integer>> stock = new HashMap<>();

        // Hardcodear los productos para cada categoría

        // Bebidas
        Map<String, Integer> bebidas = new HashMap<>();
        bebidas.put("Agua mineral", 10);
        bebidas.put("Gaseosa", 20);
        bebidas.put("Jugo natural", 15);
        stock.put("Bebidas", bebidas);

        // Comida
        Map<String, Integer> comida = new HashMap<>();
        comida.put("Empanada", 12);
        comida.put("Sandwich", 8);
        comida.put("Papas fritas", 20);
        comida.put("Hot Dog", 10);
        comida.put("Porción pizza", 6);
        stock.put("Comida", comida);

        // Artículos varios
        Map<String, Integer> articulosVarios = new HashMap<>();
        articulosVarios.put("Revista", 5);
        articulosVarios.put("Chicle", 50);
        articulosVarios.put("Encendedor", 10);
        stock.put("Articulos varios", articulosVarios);

        return stock;
    }

    public static void recorrerCategorias(Map<String, Map<String, Integer>> stock) {
        // Verificar si hay categorías disponibles
        if (stock.isEmpty()) {
            printCentered("🚫 No hay categorías disponibles en el stock.");
            return;
        }

        printCentered("📦 Listado de Categorías del Stock:");
        printCentered("-------------------------------");

        // Recorrer solo las claves (categorías) del Map
        for (String categoria : stock.keySet()) {
            // Estilo de presentación para cada categoría
            printCentered("✔️ " + categoria);
        }

    }


    public static void recorrerProductos(Map<String, Map<String, Integer>> stock) {
        // Verificar si el stock está vacío
        if (stock.isEmpty()) {
            printCentered("🚫 No hay productos disponibles en el stock.");
            return;
        }

        printCentered("📦 Listado de Productos por Categoría:");
        printCentered("-------------------------------------");

        // Recorrer las categorías
        for (Map.Entry<String, Map<String, Integer>> entry : stock.entrySet()) {
            String categoria = entry.getKey();
            Map<String, Integer> productos = entry.getValue();

            printCentered("🔸 Categoría: " + categoria);
            printCentered("  --------------------------");

            // Recorrer los productos dentro de la categoría
            for (Map.Entry<String, Integer> producto : productos.entrySet()) {
                String nombreProducto = producto.getKey();
                int cantidad = producto.getValue();

                // Mostrar el producto y su cantidad
                printCentered("  - " + nombreProducto + ": " + cantidad + " unidades");
            }

            printCentered("  --------------------------");
        }

        printCentered("-------------------------------------");
    }


    public static void mostrarCuadroStock () {
            int stock1 = consultarStock("Bebidas", "Agua mineral");
            int stock2 = consultarStock("Bebidas", "Jugo natural");
            int stock3 = consultarStock("Bebidas", "Gaseosa");
            int stock4 = consultarStock("Comida", "Empanada");
            int stock5 = consultarStock("Comida", "Sandwich");
            int stock6 = consultarStock("Comida", "Papas fritas");
            int stock7 = consultarStock("Comida", "Hot Dog");
            int stock8 = consultarStock("Comida", "Porción pizza");
            int stock9 = consultarStock("Artículos varios", "Revista");
            int stock10 = consultarStock("Artículos varios", "Chicle");
            int stock11 = consultarStock("Artículos varios", "Encendedor");

            printCentered("╔══════════════════════════════════════════════════════════════════════════════════════════════════╗");
            printCentered("║                                 Lista de Productos                                               ║");
            printCentered("╠════════════════════╦════════════════════╦════════════════════════════════════════════════════════╣");
            printCentered("║       Categoría    ║       Producto     ║                Stock                                   ║");
            printCentered("╠════════════════════╬════════════════════╬════════════════════════════════════════════════════════╣");
            printCentered("║       Bebidas      ║ Agua mineral       ║ Botella de 500 ml                      x" + stock1 + "     ║");
            printCentered("║       Bebidas      ║ Jugo natural       ║ Sabor a naranja, sin conservantes      x" + stock2 + "     ║");
            printCentered("║       Bebidas      ║ Gaseosa            ║ Lata de 350 ml, variedad de sabores    x" + stock3 + "     ║");
            printCentered("╠════════════════════╬════════════════════╬════════════════════════════════════════════════════════╣");
            printCentered("║       Comidas      ║ Empanada           ║ Rellena de carne                        x" + stock4 + "    ║");
            printCentered("║       Comidas      ║ Sandwich           ║ De jamón y queso                        x" + stock5 + "    ║");
            printCentered("║       Comidas      ║ Papas fritas       ║ Porción individual                      x" + stock6 + "    ║");
            printCentered("║       Comidas      ║ Hot Dog            ║ Con salsa y mostaza                     x" + stock7 + "    ║");
            printCentered("║       Comidas      ║ Porción pizza      ║ Margarita                               x" + stock8 + "    ║");
            printCentered("╠════════════════════╬════════════════════╬════════════════════════════════════════════════════════╣");
            printCentered("║ Artículos varios   ║ Revista            ║ Títulos variados                        x" + stock9 + "    ║");
            printCentered("║ Artículos varios   ║ Chicle             ║ Paquete de 10 unidades                  x" + stock10 + "    ║");
            printCentered("║ Artículos varios   ║ Encendedor         ║ Desechable                              x" + stock11 + "    ║");
            printCentered("╚════════════════════╩════════════════════╩════════════════════════════════════════════════════════╝");
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

