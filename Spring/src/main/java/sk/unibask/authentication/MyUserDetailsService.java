package sk.unibask.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.data.model.Account;
import sk.unibask.data.repository.AccountRepository;

import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public MyUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email).get();
        return new MyUserDetails(account.getEmail(), account.getPassword(), account.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.getName())).collect(Collectors.toList()));
    }
}
