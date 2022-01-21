package sk.unibask.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.*;
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

    @Transactional
    public Long getReputationOfAccount(Long accountId) {
        return voteRepository.getReputationOfAccount(accountId);
    }


    public void upvoteEntry(Long entryId) {
        voteService.voteEntry(entryId, 1);
    }


    public void downvoteEntry(Long entryId) {
        voteService.voteEntry(entryId, -1);
    }

    @Transactional
    public void voteEntry(Long entryId, int value) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        Entry entry = entryRepository.findById(entryId).orElse(null);
        if (entry == null) return;
        if (entry instanceof Question question) {
            if (!question.isAnonymous() && question.getAccount() == loggedAccount) return;
        } else if (entry instanceof Answer answer) {
            if (!answer.isAnonymous() && answer.getAccount() == loggedAccount) return;
        } else {
            if (entry.getAccount() == loggedAccount) return;
        }

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
}
