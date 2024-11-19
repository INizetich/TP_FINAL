package PreEmbarque;
import Personas.Pasajero;

import java.util.Random;

public class PreEmbarque {


    public static void verificarSeguridad() {
        // Verificar Seguridad

        // Información de la persona que pasa por seguridad
        System.out.println("-------------------------------------------------------------");
        System.out.println("    ✈️ ¡Bienvenido a la revisión de seguridad de embarque! 🛂");
        System.out.println("-------------------------------------------------------------");
        System.out.println("Por favor, coloque sus pertenencias en la bandeja para su inspección.");
        System.out.println("\n");

        System.out.println("        ___________");
        System.out.println("       |  POLICÍA  |");
        System.out.println("       |     ()     |");
        System.out.println("       |    /|\\    |");
        System.out.println("       |     |     |");
        System.out.println("       |    / \\    |");
        System.out.println("       |___________|");
        System.out.println("\n");

        System.out.println("🔧 Simulando la colocación de sus pertenencias: ");
        System.out.println("🖥️ Laptop");
        System.out.println("📱 Teléfono móvil");
        System.out.println("👟 Zapatos");
        System.out.println("👖 Cinturón");
        System.out.println("-------------------------------------------------------------");
        System.out.println("🔍 Escaneando su equipaje en el detector de rayos X...");
        System.out.println("🔒 Escaneando su persona en el detector de metales...");

        System.out.println("\n");

        Random random = new Random();

        // Probabilidad de detectar líquidos: 20% (1 de 5)
        boolean tieneLiquidos = random.nextDouble() < 0.2; // 20% de probabilidad
        if (tieneLiquidos) {
            System.out.println("⚠️ ¡Atención! Se han detectado líquidos. Se le pedirá que los retire.");
            System.out.println("-------------------------------------------------------------");
            System.out.println("🚫 Quitando líquidos de su maleta...");
        } else {
            System.out.println("✅ No se detectaron líquidos en su equipaje.");
        }

        System.out.println("\n");

        // Probabilidad de detectar objetos prohibidos: 10% (1 de 10)
        boolean tieneObjetosProhibidos = random.nextDouble() < 0.1; // 10% de probabilidad
        if (tieneObjetosProhibidos) {
            System.out.println("🚨 ¡Alerta! Se ha encontrado un objeto prohibido en su equipaje.");
            System.out.println("🔴 Por favor, retire el objeto o proceda con la revisión adicional.");
        } else {
            System.out.println("✅ No se encontraron objetos prohibidos.");
        }

        System.out.println("\n");
        System.out.println("-------------------------------------------------------------");
        System.out.println("🎉 ¡Todo en orden! Puede proceder al embarque 🙂");
        System.out.println("🙏 Gracias por su cooperación y que tenga un buen viaje. 🌍✈️");
        System.out.println("-------------------------------------------------------------");
    }


}




