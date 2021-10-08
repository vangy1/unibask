package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByEntryId(long id);
}
