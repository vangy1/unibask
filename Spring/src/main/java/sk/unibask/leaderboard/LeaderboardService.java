package sk.unibask.leaderboard;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.VoteRepository;
import sk.unibask.profile.avatar.AvatarService;

import java.util.List;

@Service
public class LeaderboardService {
    private final VoteRepository voteRepository;
    private final AvatarService avatarService;
    private final AuthenticationService authenticationService;

    public LeaderboardService(VoteRepository voteRepository, AvatarService avatarService, AuthenticationService authenticationService) {
        this.voteRepository = voteRepository;
        this.avatarService = avatarService;
        this.authenticationService = authenticationService;
    }

    @Transactional
    public List<LeaderboardItemDto> getLeaderboard() {
        authenticationService.getLoggedAccount();
        return voteRepository.getReputationOfAllAccounts().stream().map(value -> {
            var account = (Account) value[0];
            var reputation = (Long) value[1];
            return new LeaderboardItemDto(account.getId(), reputation, avatarService.getAvatarUrl(account), account.getUsername());
        }).toList();
    }
}
