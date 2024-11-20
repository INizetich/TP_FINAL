package PreEmbarque;
import Personas.Pasajero;

import java.util.Random;

public class PreEmbarque {


    public static void verificarSeguridad() {
        // Verificar Seguridad

        // Información de la persona que pasa por seguridad
        printCentered("-------------------------------------------------------------");
        printCentered("    ✈️ ¡Bienvenido a la revisión de seguridad de embarque! 🛂");
        printCentered("-------------------------------------------------------------");
        printCentered("Por favor, coloque sus pertenencias en la bandeja para su inspección.");
        printCentered("\n");

        printCentered("        ___________");
        printCentered("       |  POLICÍA  |");
        printCentered("       |     ()     |");
        printCentered("       |    /|\\    |");
        printCentered("       |     |     |");
        printCentered("       |    / \\    |");
        printCentered("       |___________|");
        printCentered("\n");

        printCentered("🔧 Simulando la colocación de sus pertenencias: ");
        printCentered("🖥️ Laptop");
        printCentered("📱 Teléfono móvil");
        printCentered("👟 Zapatos");
        printCentered("👖 Cinturón");
        printCentered("-------------------------------------------------------------");
        printCentered("🔍 Escaneando su equipaje en el detector de rayos X...");
        printCentered("🔒 Escaneando su persona en el detector de metales...");

        printCentered("\n");

        Random random = new Random();

        // Probabilidad de detectar líquidos: 20% (1 de 5)
        boolean tieneLiquidos = random.nextDouble() < 0.2; // 20% de probabilidad
        if (tieneLiquidos) {
            printCentered("⚠️ ¡Atención! Se han detectado líquidos. Se le pedirá que los retire.");
            printCentered("-------------------------------------------------------------");
            printCentered("🚫 Quitando líquidos de su maleta...");
        } else {
            printCentered("✅ No se detectaron líquidos en su equipaje.");
        }

        printCentered("\n");

        // Probabilidad de detectar objetos prohibidos: 10% (1 de 10)
        boolean tieneObjetosProhibidos = random.nextDouble() < 0.1; // 10% de probabilidad
        if (tieneObjetosProhibidos) {
            printCentered("🚨 ¡Alerta! Se ha encontrado un objeto prohibido en su equipaje.");
            printCentered("🔴 Por favor, retire el objeto o proceda con la revisión adicional.");
        } else {
            printCentered("✅ No se encontraron objetos prohibidos.");
        }

        printCentered("\n");
        printCentered("-------------------------------------------------------------");
        printCentered("🎉 ¡Todo en orden! Puede proceder al embarque 🙂");
        printCentered("🙏 Gracias por su cooperación y que tenga un buen viaje. 🌍✈️");
        printCentered("-------------------------------------------------------------");
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






