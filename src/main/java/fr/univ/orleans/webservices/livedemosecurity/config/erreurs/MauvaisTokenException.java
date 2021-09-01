package fr.univ.orleans.webservices.livedemosecurity.config.erreurs;

public class MauvaisTokenException extends RuntimeException {
    public MauvaisTokenException(String message) {
        super(message);
    }
}
