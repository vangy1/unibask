package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Category;
import sk.unibask.data.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findById(long id);

    @Query("SELECT q FROM Question q WHERE q.category = :category")
    List<Question> findByCategory(@Param("category") Category category);

}
