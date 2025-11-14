package src.com.library.system.application.service;

import src.com.library.system.domain.Book;
import src.com.library.system.domain.User;
import src.com.library.system.domain.Loan;
import src.com.library.system.repository.BookRepository;
import src.com.library.system.repository.LoanRepository;
import src.com.library.system.repository.UserRepository;
import src.com.library.system.application.exception.*;

import java.time.LocalDate;
import java.util.UUID;

// Principio S (SRP): Esta clase tiene la única responsabilidad de orquestar
// la lógica de negocio de préstamos y devoluciones.

// Principio D (DIP): Esta clase (alto nivel) depende de abstracciones
// (interfaces de repositorio), no de implementaciones (bajo nivel).
public class LibraryServiceImpl implements LibraryService {

    // Las dependencias son abstracciones (interfaces)
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    // Inyección de Dependencias (DIP) a través del constructor.
    // El servicio no crea sus dependencias, se las "inyectan".
    public LibraryServiceImpl(BookRepository bookRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    @Override
    public void borrowBook(String isbn, String userId) {
        // 1. Validar que el libro existe
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BookNotFoundException(isbn));

        // 2. Validar que el usuario existe
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        // 3. Validar que el libro no esté ya prestado
        if (loanRepository.findActiveLoanByBookIsbn(isbn).isPresent()) {
            throw new BookAlreadyLoanedException(isbn);
        }

        // 4. Crear el préstamo (Lógica de negocio)
        Loan newLoan = new Loan(
                UUID.randomUUID().toString(), // Generar un ID único para el préstamo
                book,
                user,
                LocalDate.now(),
                LocalDate.now().plusDays(14) // Regla de negocio: 14 días de préstamo
        );

        // 5. Guardar el préstamo
        loanRepository.save(newLoan);
        System.out.println("ÉXITO: Libro '" + book.getTitle() + "' prestado a " + user.getName());
    }

    @Override
    public void returnBook(String isbn) {
        // 1. Validar que el libro existe (buena práctica)
        if (bookRepository.findByIsbn(isbn).isPresent() == false) {
            throw new BookNotFoundException(isbn);
        }

        // 2. Encontrar el préstamo ACTIVO de este libro
        Loan activeLoan = loanRepository.findActiveLoanByBookIsbn(isbn)
                .orElseThrow(() -> new LoanNotFoundException(isbn));

        // 3. Registrar la devolución (Lógica de negocio)
        activeLoan.setReturnDate(LocalDate.now());

        // 4. Actualizar el préstamo en el repositorio
        // En una implementación en memoria, la referencia se actualiza automáticamente.
        // En una BBDD, llamaríamos a loanRepository.update(activeLoan) o save(activeLoan).
        // Para este ejemplo, la modificación al objeto 'activeLoan' es suficiente
        // porque 'InMemoryLoanRepository' mantiene la referencia directa.
        // Si 'save' fuera un 'saveOrUpdate', lo llamaríamos:
        loanRepository.save(activeLoan); // Asegura que la actualización se registre

        System.out.println("ÉXITO: Libro '" + activeLoan.getBook().getTitle() + "' ha sido devuelto.");
    }
}