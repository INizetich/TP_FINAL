package Excepciones;

public class CodigoAvionNoExistenteException extends RuntimeException {
    public CodigoAvionNoExistenteException(String message) {
        super(message);
    }
}
