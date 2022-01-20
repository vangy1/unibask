package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestionId(long id);

    @Query("SELECT a from answers a " +
            "left join fetch a.votes " +
            "where a.isAnonymous = false and a.account.id = :accountId " +
            "order by a.creationDate desc nulls last")
    List<Answer> findAllByAccountWithVotes(@Param("accountId") Long accountId);
}
