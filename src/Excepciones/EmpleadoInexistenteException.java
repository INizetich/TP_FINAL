package Excepciones;

public class EmpleadoInexistenteException extends RuntimeException {
    public EmpleadoInexistenteException(String message) {
        super(message);
    }
}
