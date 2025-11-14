package src.com.library.system.application.exception;
public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(String isbn) { super("No se encontró un préstamo activo para el libro con ISBN: " + isbn); }
}