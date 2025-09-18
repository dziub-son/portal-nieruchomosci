package pl.twojafirma.nieruchomosci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.twojafirma.nieruchomosci.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}