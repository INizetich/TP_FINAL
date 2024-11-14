package Excepciones;

public class NoEsPilotoException extends RuntimeException {
    public NoEsPilotoException(String message) {
        super(message);
    }
}
