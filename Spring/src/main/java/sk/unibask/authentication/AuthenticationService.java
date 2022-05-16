package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.AccountRepository;

import java.security.Principal;
import java.util.Date;

@Service
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    @Autowired
    private final AuthenticationProvider authenticationProvider;
    @Autowired
    private final VerificationCodeService verificationCodeService;
    @Autowired
    private AuthenticationService authenticationService;


    @Autowired
    public AuthenticationService(PasswordEncoder passwordEncoder, AccountRepository accountRepository, AuthenticationProvider authenticationProvider, VerificationCodeService verificationCodeService) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
        this.authenticationProvider = authenticationProvider;
        this.verificationCodeService = verificationCodeService;
    }

    public Account login(String mail, String password) {
//        checkMail(mail);
        SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        return authenticationService.getLoggedAccount();
    }

    public Account register(String mail, String password, String username, String verificationCode) {
//        checkMail(mail);
        if (accountRepository.findByEmail(mail).isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Účet so zadanou e-mailovou adresou už existuje.");
        if (accountRepository.findByUsername(username).isPresent())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Účet so zadaným používateľským menom už existuje.");

        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            authenticationService.createAccount(mail, password, username);
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Overovací kód je nesprávny.");
        }

        return authenticationService.getLoggedAccount();
    }

    private void checkMail(String mail) {
        if (!mail.endsWith("@uniba.sk") && !mail.endsWith("@fmph.uniba.sk")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Mail musí byť v uniba.sk doméne.");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createAccount(String mail, String password, String username) {
        var account = new Account();
        account.setEmail(mail);
        account.setAvatarSeed(username);
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setCreationDate(new Date());
        account.setMailNotifications(false);
        accountRepository.save(account);
    }

    @Transactional
    public Account passwordNew(String mail, String password, String verificationCode) {
//        checkMail(mail);
        if (verificationCodeService.isVerificationCodeValid(mail, verificationCode)) {
            Account account = accountRepository.findByEmail(mail).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Účet s daným mailom neexistuje."));
            account.setPassword(passwordEncoder.encode(password));
            accountRepository.save(account);
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(mail, password)));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Overovací kód je nesprávny.");
        }

        return authenticationService.getLoggedAccount();
    }

    @Transactional
    public Account getLoggedAccount() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Neprihlásený používateľ.");
        return accountRepository.findByEmail(principal.getName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Účet prihláseného používateľa neexistuje."));
    }

    @Transactional
    public Account getLoggedAccountOrNull() {
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
