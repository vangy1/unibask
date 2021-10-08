package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    List<Answer> findAllByQuestionId(long id);
}
