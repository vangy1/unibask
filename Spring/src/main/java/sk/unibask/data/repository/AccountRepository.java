package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
}
