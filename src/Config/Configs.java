package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configs {
    private static final String CONFIG_FILE = "properties/config.properties";
    private static Properties properties = new Properties();
    public static void cargarConfiguracionCliente() {
        File configFile = new File(CONFIG_FILE);

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
            properties.store(output, "Configuración inicial del menú cliente");
            System.out.println("Archivo de configuración del menú cliente creado con first_run=true");
        } catch (IOException e) {
            System.err.println("Error al crear el archivo de configuración del menú cliente.");
            e.printStackTrace();
        }
    }


    // Lee el valor de first_run
    public static boolean isFirstRun() {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
            return Boolean.parseBoolean(properties.getProperty("first_run", "true"));
        } catch (IOException e) {
            // Si el archivo no existe, se considera la primera vez
            return true;
        }
    }

    // Establece el valor de first_run en el archivo de configuración solo la primera vez
    public static void setFirstRunComplete() {
        try {
            // Solo se debe actualizar first_run a false si es la primera ejecución
            if (isFirstRun()) {
                setFirstRun(false);  // Cambiar first_run a false
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para establecer el valor de first_run
    private static void setFirstRun(boolean value) throws IOException {
        Properties properties = new Properties();

        // Si el archivo no existe, lo creamos
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        try (FileInputStream fis = new FileInputStream(configFile)) {
            properties.load(fis);
        } catch (IOException e) {
            // Si el archivo no existe, no pasa nada y continuamos
        }

        // Actualizamos el valor de first_run
        properties.setProperty("first_run", String.valueOf(value));

        try (FileOutputStream fos = new FileOutputStream(configFile)) {
            properties.store(fos, null);
        }
    }

}


/*
*
* package Config;

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

*
*
* */


