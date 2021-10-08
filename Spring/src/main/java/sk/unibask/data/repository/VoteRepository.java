package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Entry;
import sk.unibask.data.model.Vote;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("SELECT v FROM Vote v WHERE v.entry = :entry and v.value > 0")
    List<Vote> findAllUpvotes(@Param("entry") Entry entry);

    @Query("SELECT v FROM Vote v WHERE v.entry = :entry and v.value < 0")
    List<Vote> findAllDownvotes(@Param("entry") Entry entry);

    @Query("SELECT COALESCE(sum(v.value), 0) FROM Vote v WHERE v.account = :account")
    Long findAccountReputation(@Param("account") Account account);

    @Query("SELECT COALESCE(sum(v.value), 0) FROM Vote v WHERE v.entry = :entry")
    Long findEntryReputation(@Param("entry") Entry entry);

    @Query("SELECT count(v.id) FROM Vote v WHERE v.entry = :entry and v.value > 0")
    Long findUpvotesCount(@Param("entry") Entry entry);

    @Query("SELECT count(v.id) FROM Vote v WHERE v.entry = :entry and v.value < 0")
    Long findDownvotesCount(@Param("entry") Entry entry);


}
