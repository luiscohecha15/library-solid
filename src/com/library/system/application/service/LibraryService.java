package src.com.library.system.application.service;

// Principio I (ISP): Una interfaz cohesiva para las operaciones de la biblioteca.
public interface LibraryService {
    /**
     * Presta un libro a un usuario.
     * @param isbn ISBN del libro a prestar.
     * @param userId ID del usuario que toma el libro.
     * @throws BookNotFoundException si el libro no existe.
     * @throws UserNotFoundException si el usuario no existe.
     * @throws BookAlreadyLoanedException si el libro ya está prestado.
     */
    void borrowBook(String isbn, String userId);

    /**
     * Devuelve un libro a la biblioteca.
     * @param isbn ISBN del libro a devolver.
     * @throws BookNotFoundException si el libro no existe (para validar).
     * @throws LoanNotFoundException si el libro no tiene un préstamo activo.
     */
    void returnBook(String isbn);
}