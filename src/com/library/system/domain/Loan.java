package src.com.library.system.domain;

import java.time.LocalDate;

// Entidad que representa la transacción de un préstamo.
public class Loan {
    private final String loanId;
    private final Book book;
    private final User user;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate; // Nulo si aún no se ha devuelto

    public Loan(String loanId, Book book, User user, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null; // Un préstamo inicia sin fecha de devolución
    }

    // Getters
    public String getLoanId() { return loanId; }
    public Book getBook() { return book; }
    public User getUser() { return user; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    // Setter para la lógica de negocio (devolver libro)
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return this.returnDate != null;
    }
}