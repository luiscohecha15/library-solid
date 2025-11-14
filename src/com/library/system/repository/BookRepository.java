package src.com.library.system.repository;

import src.com.library.system.domain.Book;
import java.util.Optional;
import java.util.List;

// Principio D (DIP): Abstracción para el almacenamiento de libros.
public interface BookRepository {
    void save(Book book);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findAll();
}