package sk.unibask.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Entry;
import sk.unibask.data.model.Vote;
import sk.unibask.data.repository.EntryRepository;
import sk.unibask.data.repository.VoteRepository;

import java.util.Date;

@Service
public class VoteService {

    @Autowired
    private VoteService voteService;

    private final AuthenticationService authenticationService;
    private final EntryRepository entryRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(AuthenticationService authenticationService, EntryRepository entryRepository, VoteRepository voteRepository) {
        this.authenticationService = authenticationService;
        this.entryRepository = entryRepository;
        this.voteRepository = voteRepository;
    }


    public void upvoteEntry(Long entryId) {
        voteService.voteEntry(entryId, 1);
    }


    public void downvoteEntry(Long entryId) {
        voteService.voteEntry(entryId, -1);
    }

    @Transactional
    public void voteEntry(Long entryId, int value) {
        System.out.println("1");
        Account loggedAccount = authenticationService.getLoggedAccount();
        Entry entry = entryRepository.findById(entryId).orElse(null);
        if (entry == null || entry.getAccount() == loggedAccount) return;


        Vote vote = voteRepository.findByAccountAndEntry(loggedAccount.getId(), entryId).orElseGet(() -> {
            Vote vote1 = new Vote();
            vote1.setCreationDate(new Date());
            vote1.setAccount(loggedAccount);
            vote1.setEntry(entry);
            return vote1;
        });


        vote.setValue(value);
        voteRepository.save(vote);
    }

    @Transactional
    public void unvoteEntry(Long entryId) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        Entry entry = entryRepository.findById(entryId).orElse(null);
        if (entry == null || entry.getAccount() == loggedAccount) return;

        Vote vote = voteRepository.findByEntryId(entryId).orElse(null);
        if (vote != null && vote.getAccount() == loggedAccount) {
            voteRepository.delete(vote);
        }
    }


    @Transactional
    public void getVotesForEntry(Long entryId) {

    }

    @Transactional
    public void getVotesForUser(Long userId) {

    }


}
