package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Category;
import sk.unibask.data.model.Question;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(long id);

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUsername(String username);

    @Query("SELECT a from accounts a " +
            "join a.followingQuestions fq " +
            "where fq = :question")
    Set<Account> findAccountsWithQuestionFollowed(@Param("question") Question question);

    @Query("SELECT a from accounts a " +
            "join a.followingCategories fc " +
            "where fc = :category")
    Set<Account> findAccountsWithCategoryFollowed(@Param("category") Category category);

}
