package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.AccountRepository;

import java.util.stream.Collectors;

@Service
public class UnibaskUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    public UnibaskUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) {
        Account account = accountRepository.findByEmail(email).get();
        return new UnibaskUserDetails(
                account.getEmail(),
                account.getPassword(),
                account.getAuthorities().stream().map(authority ->
                                new SimpleGrantedAuthority(authority.getName()))
                        .collect(Collectors.toList()));
    }
}
