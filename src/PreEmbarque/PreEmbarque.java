package PreEmbarque;
import java.util.Random;

public class PreEmbarque {



    public void verificarSeguridad() {

        // Verificar Seguridad

            // Información de la persona que pasa por seguridad

            System.out.println("---------------------------------------------------------");
            System.out.println("Por favor, coloque sus pertenencias en la bandeja.");
            System.out.println("___________");
            System.out.println("| POLICÍA |");
            System.out.println("     ()  ");
            System.out.println("    /|\\  ");
            System.out.println("     |    ");
            System.out.println("    / \\   ");
            // Simulación de colocación de objetos
            System.out.println("Colocando: Laptop, teléfono móvil, zapatos, cinturón...");
            System.out.println("Revisando las pertenencias...");
            System.out.println("");
            // Revisión de líquidos
            Random random = new Random();
            boolean tieneLiquidos = random.nextBoolean();
            if (tieneLiquidos) {
                System.out.println("¡Atención! Se han detectado líquidos. Se le pedirá que los retire.");
                System.out.println("");
                System.out.println("Quitando líquidos de su maleta...");
                System.out.println("");
            } else {
                System.out.println("No se detectaron líquidos.");
            }

            // Revisión de objetos prohibidos
            boolean tieneObjetosProhibidos = random.nextBoolean();
            if (tieneObjetosProhibidos) {
                System.out.println("¡Alerta! Se ha encontrado un objeto prohibido en su equipaje.");
                System.out.println("Por favor, retire el objeto o proceda a la revisión adicional.");
            } else {
                System.out.println("No se encontraron objetos prohibidos.");
            }

            // Simulación de escaneo
            System.out.println("Escaneando su equipaje en el detector de rayos X...");
            System.out.println("Escaneando su persona en el detector de metales...");

            // Resultado final
            System.out.println("---------------------------------------------------------");
            System.out.println("¡Todo en orden! Puede proceder al embarque :) .");
            System.out.println("Gracias por su cooperación y que tenga un buen viaje!.");
            System.out.println("---------------------------------------------------------");

    }



}
