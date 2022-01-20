package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
