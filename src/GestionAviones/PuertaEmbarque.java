package GestionAviones;

public enum PuertaEmbarque {
    A1, A2, A3, A4, A5, B1, B2, B3, B4, B5, C1, C2, C3, C4, C5;

    // Método para obtener una puerta aleatoria
    public static PuertaEmbarque obtenerPuertaAleatoria() {
        PuertaEmbarque[] puertas = PuertaEmbarque.values();
        int indiceAleatorio = (int) (Math.random() * puertas.length);
        return puertas[indiceAleatorio];
    }
}