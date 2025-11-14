package src.com.library.system.application.exception;

public class BookAlreadyLoanedException extends RuntimeException {
    public BookAlreadyLoanedException(String isbn) { super("El libro con ISBN: " + isbn + " ya está prestado."); }
}