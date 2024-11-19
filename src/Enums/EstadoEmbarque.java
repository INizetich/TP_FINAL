package Enums;

import java.util.Random;

public enum EstadoEmbarque {
    EN_HORARIO,
    CERRADO,
    ATRASADO;

    // Método para devolver un estado aleatorio
    public static EstadoEmbarque getEstadoAleatorio() {
        Random random = new Random();
        // Elegir un valor aleatorio entre los valores del enum
        EstadoEmbarque[] estados = EstadoEmbarque.values();
        return estados[random.nextInt(estados.length)];
    }
}
