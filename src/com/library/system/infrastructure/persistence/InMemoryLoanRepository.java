package persistence;

import src.com.library.system.domain.Loan;
import src.com.library.system.repository.LoanRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryLoanRepository implements LoanRepository {
    // Usamos un Map para un acceso rápido por ID
    private final Map<String, Loan> loans = new HashMap<>();

    @Override
    public void save(Loan loan) {
        loans.put(loan.getLoanId(), loan);
    }

    @Override
    public Optional<Loan> findActiveLoanByBookIsbn(String isbn) {
        // Un libro solo puede tener un préstamo activo a la vez
        return loans.values().stream()
                .filter(loan -> loan.getBook().getIsbn().equals(isbn) && !loan.isReturned())
                .findFirst();
    }

    @Override
    public List<Loan> findActiveLoansByUser(String userId) {
        return loans.values().stream()
                .filter(loan -> loan.getUser().getUserId().equals(userId) && !loan.isReturned())
                .collect(Collectors.toList());
    }
}