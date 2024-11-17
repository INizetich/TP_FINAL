package Config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configs {
    private static final String CONFIG_FILE = "properties/config.properties";

    static {
        // Registrar el shutdown hook para cambiar first_run a true cuando se detenga el programa
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Cambiar first_run a true cuando el programa se detenga
            try {
                setFirstRun(true); // Establecer first_run a true
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
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

    // Establece el valor de first_run en el archivo de configuración
    public static void setFirstRunComplete() {
        try {
            setFirstRun(false); // Cambiar first_run a false
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para establecer el valor de first_run
    private static void setFirstRun(boolean value) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            // Si el archivo no existe, no pasa nada y continuamos
        }

        // Actualizamos el valor de first_run
        properties.setProperty("first_run", String.valueOf(value));

        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, null);
        }
    }
}
