package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Question;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findById(long id);
}
