package Utilidades;

public class Utilities {

    public static void mostrarCargando() {
        String[] animacion = {"|", "/", "-", "\\"};  // Barras giratorias
        int i = 0;

        // Simula el proceso de carga
        System.out.print("Generando Boleto... ");
        try {
            for (int j = 0; j < 10; j++) {  // 10 ciclos de la animación
                System.out.print(animacion[i] + "\r");  // Sobrescribe la línea anterior
                i = (i + 1) % animacion.length;  // Cambia al siguiente símbolo en el arreglo
                Thread.sleep(250);  // Pausa de 250ms entre cada cambio
            }
            System.out.println("✔️ Listo!");  // Fin de la animación
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mostrarCargandoMenuPrincipal() {
        String[] animacion = {"|", "/", "-", "\\"};  // Barras giratorias
        int i = 0;

        // Simula el proceso de carga
        System.out.print("Regresando al menu principal... ");
        try {
            for (int j = 0; j < 10; j++) {  // 10 ciclos de la animación
                System.out.print(animacion[i] + "\r");  // Sobrescribe la línea anterior
                i = (i + 1) % animacion.length;  // Cambia al siguiente símbolo en el arreglo
                Thread.sleep(250);  // Pausa de 250ms entre cada cambio
            }
            System.out.println("✔️ Listo!");  // Fin de la animación
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    public static void mostrarCerrando() {
        String[] animacion = {"|", "/", "-", "\\"};  // Barras giratorias
        int i = 0;

        // Simula el proceso de carga
        System.out.print("Cerrando sesion... ");
        try {
            for (int j = 0; j < 10; j++) {  // 10 ciclos de la animación
                System.out.print(animacion[i] + "\r");  // Sobrescribe la línea anterior
                i = (i + 1) % animacion.length;  // Cambia al siguiente símbolo en el arreglo
                Thread.sleep(250);  // Pausa de 250ms entre cada cambio
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void mostrarCerrandoAdmin() {
        String[] animacion = {"|", "/", "-", "\\"};  // Barras giratorias
        int i = 0;

        // Simula el proceso de carga
        System.out.print("\u001B[31mCerrando sesión...\u001B[0m");
        try {
            for (int j = 0; j < 10; j++) {  // 10 ciclos de la animación
                System.out.print("\u001B[31m" + animacion[i] + "\u001B[0m\r");  // Sobrescribe la línea anterior y aplica el color rojo
                i = (i + 1) % animacion.length;  // Cambia al siguiente símbolo en el arreglo
                Thread.sleep(250);  // Pausa de 250ms entre cada cambio
            }
            System.out.println("\u001B[32m✔️ Listo!\u001B[0m");


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public static void printCentered(String text) {
        int terminalWidth = 160; // Ajusta el valor según tu terminal
        if (text.length() > terminalWidth) {
            // Si el texto es más largo que el ancho del terminal, lo imprime sin centrado
            System.out.println(text);
            return;
        }
        int padding = (terminalWidth - text.length()) / 2;
        String paddedText = " ".repeat(Math.max(0, padding)) + text;
        System.out.println(paddedText);
    }


    public static void limpiarPantalla() {
        // Imprime 50 líneas vacías para simular la limpieza de pantalla
        for (int i = 0; i < 15; i++) {
            System.out.println();
        }
    }



}
