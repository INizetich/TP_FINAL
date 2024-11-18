package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Configs {
    private static final String CONFIG_FILE = "properties/config.properties";

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
