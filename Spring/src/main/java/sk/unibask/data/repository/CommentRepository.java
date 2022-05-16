package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Comment;

import java.util.Set;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT distinct c from comments c " +
            "left join fetch c.votes " +
            "where c.account.id = :accountId " +
            "order by c.creationDate desc nulls last")
    Set<Comment> findAllByAccountWithVotes(@Param("accountId") Long accountId);
}
