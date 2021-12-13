package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findById(long id);

    @Query("SELECT q FROM Question q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "left join fetch answers.comments answers_comments " +
            "left join fetch answers.votes answers_votes " +
            "left join fetch q.comments comments " +
            "where q.id = :questionId")
    Optional<Question> findOneWithAllData(@Param("questionId") Long questionId);

    @Query("SELECT q FROM Question q " +
            "left join fetch q.votes votes " +
            "left join fetch q.viewers viewers " +
            "where q.id = :questionId")
    Optional<Question> findOneWithVotesViewers(@Param("questionId") Long questionId);


    @Query("SELECT distinct q FROM Question q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "order by q.lastActivity desc nulls last ")
    List<Question> findAllWithViewersVotesAnswersComments();

    @Query("SELECT distinct q FROM Question q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where q.category.id = :categoryId " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByCategoryWithViewersVotesAnswersComments(@Param("categoryId") Long categoryId);

    @Query("SELECT distinct  q FROM accounts a " +
            "join a.followingCategories fc " +
            "join fc.questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where a.id = :accountId " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByFollowingWithViewersVotesAnswersComments(@Param("accountId") Long accountId);

    @Query("SELECT q from Question q " +
            "left join fetch q.votes " +
            "where q.account.id = :accountId " +
            "order by q.creationDate desc nulls last")
    List<Question> findAllByAccountWithVotes(@Param("accountId") Long accountId);


}
