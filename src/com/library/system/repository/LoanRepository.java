package src.com.library.system.repository;

import src.com.library.system.domain.Loan;
import java.util.Optional;
import java.util.List;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findActiveLoanByBookIsbn(String isbn);
    List<Loan> findActiveLoansByUser(String userId);
}
