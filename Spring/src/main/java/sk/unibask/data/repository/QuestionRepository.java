package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "left join fetch answers.comments answers_comments " +
            "left join fetch answers.votes answers_votes " +
            "left join fetch q.comments comments " +
            "where q.id = :questionId ")
    Optional<Question> findOneWithAllData(@Param("questionId") Long questionId);

    @Query("SELECT q FROM questions q " +
            "left join fetch q.votes votes " +
            "left join fetch q.viewers viewers " +
            "where q.id = :questionId")
    Optional<Question> findOneWithVotesViewers(@Param("questionId") Long questionId);


    @Query("SELECT distinct q FROM questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "order by q.lastActivity desc nulls last ")
    List<Question> findAllQuestions();

    @Query("SELECT distinct q FROM questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where lower(q.title) LIKE %:phrase% or lower(q.entryTextUnformatted) LIKE %:phrase% " +
            "order by q.lastActivity desc nulls last ")
    List<Question> findAllQuestions(@Param("phrase") String phrase);

    @Query("SELECT distinct q FROM questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where q.category.id = :categoryId " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByCategory(@Param("categoryId") Long categoryId);

    @Query("SELECT distinct q FROM questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where q.category.id = :categoryId and (q.title LIKE %:phrase% or q.entryTextUnformatted LIKE %:phrase%) " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByCategory(@Param("categoryId") Long categoryId, @Param("phrase") String phrase);

    @Query("SELECT distinct  q FROM accounts a " +
            "join a.followingCategories fc " +
            "join fc.questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where a.id = :accountId " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByFollowing(@Param("accountId") Long accountId);

    @Query("SELECT distinct  q FROM accounts a " +
            "join a.followingCategories fc " +
            "join fc.questions q " +
            "left join fetch q.viewers viewers " +
            "left join fetch q.votes votes " +
            "left join fetch q.answers answers " +
            "where a.id = :accountId and (q.title LIKE %:phrase% or q.entryTextUnformatted LIKE %:phrase%) " +
            "order by q.lastActivity desc nulls last")
    List<Question> findAllByFollowing(@Param("accountId") Long accountId, @Param("phrase") String phrase);

    @Query("SELECT q from questions q " +
            "left join fetch q.votes " +
            "where q.isAnonymous = false and q.account.id = :accountId " +
            "order by q.creationDate desc nulls last")
    List<Question> findAllByAccountWithVotes(@Param("accountId") Long accountId);


}
