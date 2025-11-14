package src.com.library.system.application.exception;
// Excepciones simples de tipo Runtime
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) { super("Libro no encontrado con ISBN: " + isbn); }
}





