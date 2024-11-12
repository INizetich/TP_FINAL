package Excepciones;

public class HangarNoExistenteException extends RuntimeException {
    public HangarNoExistenteException(String message) {
        super(message);
    }
}
