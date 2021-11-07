package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.avatar.AvatarService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Authority;

@Service
public class UserService {
    @Autowired
    private UserService userService;

    private final AuthenticationService authenticationService;
    private final AvatarService avatarService;


    @Value("${app.avatars-path}")
    private String avatarsPath;


    @Autowired
    public UserService(AuthenticationService authenticationService, AvatarService avatarService) {
        this.authenticationService = authenticationService;
        this.avatarService = avatarService;
    }

    @Transactional
    public UserDto getUser(Account account) {
        if (account == null) return null;
        return new UserDto(account.getEmail(), account.getUsername(), avatarService.getAvatarUrl(account), account.getAuthorities().stream().map(Authority::getName).toList());
    }


}
