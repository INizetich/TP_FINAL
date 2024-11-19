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