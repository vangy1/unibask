package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.VerificationCode;

import java.util.List;
import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    Optional<VerificationCode> findByEmail(String email);

    List<VerificationCode> findAllByEmail(String email);
}
