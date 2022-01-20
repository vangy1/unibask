package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Entry;
import sk.unibask.data.model.Vote;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByEntryId(Long entryId);

    @Query("SELECT v FROM votes v WHERE v.account.id = :accountId and v.entry.id = :entryId")
    Optional<Vote> findByAccountAndEntry(@Param("accountId") Long accountId, @Param("entryId") Long entryId);

    @Query("SELECT v FROM votes v WHERE v.entry = :entry and v.value > 0")
    List<Vote> findAllUpvotes(@Param("entry") Entry entry);

    @Query("SELECT v FROM votes v WHERE v.entry = :entry and v.value < 0")
    List<Vote> findAllDownvotes(@Param("entry") Entry entry);

    @Query("SELECT COALESCE(sum(v.value), 0) FROM votes v WHERE v.account = :account")
    Long findAccountReputation(@Param("account") Account account);

    @Query("SELECT COALESCE(sum(v.value), 0) FROM votes v WHERE v.entry = :entry")
    Long findEntryReputation(@Param("entry") Entry entry);

    @Query("SELECT count(v.id) FROM votes v WHERE v.entry = :entry and v.value > 0")
    Long findUpvotesCount(@Param("entry") Entry entry);

    @Query("SELECT count(v.id) FROM votes v WHERE v.entry = :entry and v.value < 0")
    Long findDownvotesCount(@Param("entry") Entry entry);

    @Query("SELECT COALESCE(sum(case when (q.isAnonymous is null or q.isAnonymous = false) and (ans.isAnonymous is null or ans.isAnonymous = false) then v.value else 0 end), 0) " +
            "FROM votes v " +
            "join v.entry as e " +
            "left join questions q on q.id = e.id " +
            "left join answers ans on ans.id = e.id " +
            "WHERE e.account.id = :accountId")
    Long getReputationOfAccount(@Param("accountId") Long accountId);

    @Query("SELECT distinct a, COALESCE(sum(case when (q.isAnonymous is null or q.isAnonymous = false) and (ans.isAnonymous is null or ans.isAnonymous = false) then v.value else 0 end), 0) " +
            "FROM accounts a " +
            "left join a.entries e " +
            "left join e.votes v " +
            "left join questions q on q.id = e.id " +
            "left join answers ans on ans.id = e.id " +
            "group by a " +
            "order by COALESCE(sum(case when (q.isAnonymous is null or q.isAnonymous = false) and (ans.isAnonymous is null or ans.isAnonymous = false) then v.value else 0 end), 0) desc")
    List<Object[]> getReputationOfAllAccounts();
    //    @Query("SELECT distinct a, COALESCE(sum(v.value), 0) FROM Vote v join v.entry as e join e.account as a left join Question q on q.id = e.id left join Answer ans on ans.id = e.id WHERE (q.isAnonymous is null or q.isAnonymous = false) and (ans.isAnonymous is null or ans.isAnonymous = false) group by a order by COALESCE(sum(v.value), 0) desc")

}
