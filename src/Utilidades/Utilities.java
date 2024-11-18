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

}
