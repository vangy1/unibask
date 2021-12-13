package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.AccountRepository;

import java.security.Principal;
import java.util.Date;

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

    public Account login(String mail, String password) {
        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        return authenticationService.getLoggedAccount();
    }

    public Account register(String mail, String password, String username, String verificationCode) {
        if (!mail.endsWith("uniba.sk")) return null;
        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            authenticationService.createAccount(mail, password, username);
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        }

        return authenticationService.getLoggedAccount();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAccount(String mail, String password, String username) {
        var account = new Account();
        account.setEmail(mail);
        account.setAvatarSeed(username);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setCreationDate(new Date());
        accountRepository.save(account);
    }

    @Transactional
    public Account passwordNew(String mail, String password, String verificationCode) {
        if (!mail.endsWith("uniba.sk")) return null;
        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            Account account = accountRepository.findByEmail(mail).get();
            account.setPassword(passwordEncoder.encode(password));
            accountRepository.save(account);
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        }
        return authenticationService.getLoggedAccount();
    }

    @Transactional
    public Account getLoggedAccount() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null) return null;
        return accountRepository.findByEmail(principal.getName()).orElse(null);
    }

    @Transactional
    public void passwordChange(String oldPassword, String newPassword) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        if (passwordEncoder.matches(oldPassword, loggedAccount.getPassword())) {
            loggedAccount.setPassword(passwordEncoder.encode(newPassword));
        }
    }
}
