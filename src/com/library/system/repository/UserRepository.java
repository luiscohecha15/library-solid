package src.com.library.system.repository;

import src.com.library.system.domain.User;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findById(String userId);
}