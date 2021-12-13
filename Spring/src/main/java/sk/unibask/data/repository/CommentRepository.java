package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByEntryId(long id);

    @Query("SELECT c from Comment c " +
            "left join fetch c.votes " +
            "where c.account.id = :accountId " +
            "order by c.creationDate desc nulls last")
    List<Comment> findAllByAccountWithVotes(@Param("accountId") Long accountId);
}
