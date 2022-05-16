package sk.unibask.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.authentication.UserDto;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Authority;
import sk.unibask.data.model.StudyProgram;
import sk.unibask.data.repository.AccountRepository;
import sk.unibask.data.repository.StudyProgramRepository;
import sk.unibask.profile.avatar.AvatarService;
import sk.unibask.profile.studyprogram.StudyProgramDto;
import sk.unibask.vote.VoteService;

@Service
public class ProfileService {
    private final AuthenticationService authenticationService;
    private final AvatarService avatarService;
    private final AccountRepository accountRepository;
    private final StudyProgramRepository studyProgramRepository;
    private final VoteService voteService;

    @Autowired
    public ProfileService(AuthenticationService authenticationService, AvatarService avatarService, AccountRepository accountRepository, StudyProgramRepository studyProgramRepository, VoteService voteService) {
        this.authenticationService = authenticationService;
        this.avatarService = avatarService;
        this.accountRepository = accountRepository;
        this.studyProgramRepository = studyProgramRepository;
        this.voteService = voteService;
    }

    @Transactional
    public UserDto getUser(Account account) {
        if (account == null) return null;
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), account.getAuthorities().stream().map(Authority::getName).toList(), null, voteService.getReputationOfAccount(account.getId()));
    }

    public UserDto getUserWithoutTransaction(Account account) {
        if (account == null) return null;
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null, null, voteService.getReputationOfAccount(account.getId()));
    }

    public UserDto getUser(Long userId) {
        authenticationService.getLoggedAccount();
        Account account = accountRepository.findById(userId).orElse(null);
        if (account == null) return null;
        StudyProgram studyProgram = account.getStudyProgram();
        if (studyProgram == null)
            return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null, null, voteService.getReputationOfAccount(userId));
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null,
                new StudyProgramDto(studyProgram.getId(), studyProgram.getShortName(), studyProgram.getLongName()), voteService.getReputationOfAccount(userId));
    }

    public UserInfoDto getUserInfo() {
        Account loggedAccount = authenticationService.getLoggedAccount();
        return new UserInfoDto(loggedAccount.getStudyProgram() == null ? null : loggedAccount.getStudyProgram().getId(), loggedAccount.getMailNotifications());
    }

    @Transactional
    public void setUserInfo(String studyProgramId, boolean mailNotificationsEnabled) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        StudyProgram studyProgram = null;
        if (studyProgramId != null) {
            studyProgram = studyProgramRepository.findById(Long.valueOf(studyProgramId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Študijný plán neexisutje."));
        }
        loggedAccount.setStudyProgram(studyProgram);
        loggedAccount.setMailNotifications(mailNotificationsEnabled);
    }
}
