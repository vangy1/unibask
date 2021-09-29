package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.AccountRepository;

import java.security.Principal;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public UserDto login(String mail, String password) {
        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        return authenticationService.getLoggedUser();
    }

    public UserDto register(String mail, String password, String username, String verificationCode) {
        if (!mail.endsWith("@uniba.sk")) return null;
        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            authenticationService.createAccount(mail, password, username);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        return authenticationService.getLoggedUser();
    }

    @Transactional
    public void createAccount(String mail, String password, String username) {
        var account = new Account();
        account.setEmail(mail);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
    }

    @Transactional
    public void passwordChange(String mail, String password, String verificationCode) {
        if (!mail.endsWith("@uniba.sk")) return;
        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            accountRepository.findByEmail(mail).get().setPassword(passwordEncoder.encode(password));
        }
    }

    @Transactional
    public Account getLoggedAccount() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null) return null;
        return accountRepository.findByEmail(principal.getName()).orElse(null);
    }

    @Transactional
    public UserDto getLoggedUser() {
        var account = authenticationService.getLoggedAccount();
        if (account == null) return null;
        return new UserDto(account);
    }


}
