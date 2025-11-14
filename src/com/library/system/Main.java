package src.com.library.system;

import src.com.library.system.application.service.LibraryService;
import src.com.library.system.application.service.LibraryServiceImpl;
import src.com.library.system.domain.Book;
import src.com.library.system.domain.User;
import persistence.InMemoryBookRepository;
import persistence.InMemoryLoanRepository;
import persistence.InMemoryUserRepository;
import src.com.library.system.repository.BookRepository;
import src.com.library.system.repository.LoanRepository;
import src.com.library.system.repository.UserRepository;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Gestión de Biblioteca (SOLID) ===");

        // --- INICIO: Composition Root (Inyección de Dependencias) ---
        // 1. Crear las implementaciones de bajo nivel (los "detalles")
        BookRepository bookRepository = new InMemoryBookRepository();
        UserRepository userRepository = new InMemoryUserRepository();
        LoanRepository loanRepository = new InMemoryLoanRepository();

        // 2. Inyectar estas implementaciones en el servicio de alto nivel
        // El servicio SÓLO conoce las interfaces, no las clases "InMemory..."
        LibraryService libraryService = new LibraryServiceImpl(
                bookRepository,
                userRepository,
                loanRepository
        );
        // --- FIN: Composition Root ---


        // --- Configuración Inicial (Datos de prueba) ---
        System.out.println("\n--- Cargando datos iniciales... ---");
        Book book1 = new Book("978-0321125217", "Domain-Driven Design", "Eric Evans");
        Book book2 = new Book("978-0132350884", "Clean Code", "Robert C. Martin");
        bookRepository.save(book1);
        bookRepository.save(book2);

        User user1 = new User("U-001", "Ana Torres");
        User user2 = new User("U-002", "Carlos Ruiz");
        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Libros y usuarios cargados.");


        // --- Simulación de Casos de Uso ---
        System.out.println("\n--- Simulación de Préstamos ---");

        // Caso 1: Préstamo exitoso
        try {
            libraryService.borrowBook("978-0132350884", "U-001"); // Ana toma "Clean Code"
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Caso 2: Préstamo fallido (Libro ya prestado)
        try {
            libraryService.borrowBook("978-0132350884", "U-002"); // Carlos intenta tomar "Clean Code"
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Caso 3: Préstamo fallido (Libro no existe)
        try {
            libraryService.borrowBook("111-1111111111", "U-002");
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Caso 4: Devolución exitosa
        System.out.println("\n--- Simulación de Devoluciones ---");
        try {
            libraryService.returnBook("978-0132350884"); // Ana devuelve "Clean Code"
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Caso 5: Devolución fallida (Libro no estaba prestado)
        try {
            libraryService.returnBook("978-0321125217"); // Intentar devolver "DDD" (nunca se prestó)
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }

        // Caso 6: Préstamo exitoso (después de devolución)
        System.out.println("\n--- Simulación Post-Devolución ---");
        try {
            libraryService.borrowBook("978-0132350884", "U-002"); // Carlos ahora sí puede tomar "Clean Code"
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}