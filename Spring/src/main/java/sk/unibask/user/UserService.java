package sk.unibask.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Authority;
import sk.unibask.data.model.StudyProgram;
import sk.unibask.data.repository.AccountRepository;
import sk.unibask.data.repository.StudyProgramRepository;
import sk.unibask.user.avatar.AvatarService;
import sk.unibask.user.studyprogram.StudyProgramDto;

@Service
public class UserService {
    @Autowired
    private UserService userService;
    private final AuthenticationService authenticationService;
    private final AvatarService avatarService;
    private final AccountRepository accountRepository;
    private final StudyProgramRepository studyProgramRepository;

    @Value("${app.avatars-path}")
    private String avatarsPath;

    @Autowired
    public UserService(AuthenticationService authenticationService, AvatarService avatarService, AccountRepository accountRepository, StudyProgramRepository studyProgramRepository) {
        this.authenticationService = authenticationService;
        this.avatarService = avatarService;
        this.accountRepository = accountRepository;
        this.studyProgramRepository = studyProgramRepository;
    }

    @Transactional
    public UserDto getUser(Account account) {
        if (account == null) return null;
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), account.getAuthorities().stream().map(Authority::getName).toList(), null);
    }

    public UserDto getUserWithoutTransaction(Account account) {
        if (account == null) return null;
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null, null);
    }

    public UserDto getUser(Long userId) {
        Account account = accountRepository.findById(userId).orElse(null);
        if (account == null) return null;
        StudyProgram studyProgram = account.getStudyProgram();
        if (studyProgram == null)
            return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null, null);
        return new UserDto(account.getId(), account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getCreationDate(), null,
                new StudyProgramDto(studyProgram.getId(), studyProgram.getShortName(), studyProgram.getLongName()));
    }

    @Transactional
    public void setStudyProgramId(long studyProgramId) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        StudyProgram studyProgram = studyProgramRepository.findById(studyProgramId).orElse(null);
        if (studyProgram == null) return;
        loggedAccount.setStudyProgram(studyProgram);
    }
}
