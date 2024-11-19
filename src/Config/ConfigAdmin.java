package Config;
import java.io.*;
import java.util.Properties;
public class ConfigAdmin {
    // Ruta del archivo de configuración para el menú admin
    private static final String CONFIG_FILE2 = "properties/config_admin.properties";
    private static Properties properties = new Properties();
    // Cargar el archivo de configuración para el menú admin
    public static void cargarConfiguracionAdmin() {
        File configFile = new File(CONFIG_FILE2);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis); // Cargar propiedades desde el archivo
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo de configuración del menú admin.");
                e.printStackTrace();
            }
        } else {
            // Si el archivo no existe, crear uno nuevo con "first_run=true"
            crearConfiguracionInicial(configFile);
        }
    }
    // Crear la configuración inicial si el archivo no existe
    private static void crearConfiguracionInicial(File configFile) {
        try (FileOutputStream output = new FileOutputStream(configFile)) {
            properties.setProperty("first_run", "true");
            properties.store(output, "Configuración inicial del menú admin");
            System.out.println("Archivo de configuración del menú admin creado con first_run=true");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de configuración del menú admin.");
            e.printStackTrace();
        }
    }
    // Leer el valor de "first_run" del archivo de configuración del menú admin
    public static boolean isFirstRunAdmin() {
        return Boolean.parseBoolean(properties.getProperty("first_run", "true"));
    }
    // Establecer el valor de "first_run" a false en el archivo de configuración del menú admin
    public static void setFirstRunFalseAdmin() {
        properties.setProperty("first_run", "false");
        actualizarConfiguracion();
    }
    // Actualizar el archivo de configuración con las propiedades actuales
    private static void actualizarConfiguracion() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE2)) {
            properties.store(fos, "Configuración actualizada para el menú admin");
        } catch (IOException e) {
            System.err.println("Error al actualizar el archivo de configuración del menú admin.");
            e.printStackTrace();
        }
    }
    // Opcional: Método para ver las propiedades cargadas (útil para depuración)
    public static void mostrarConfiguracion() {
        properties.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}