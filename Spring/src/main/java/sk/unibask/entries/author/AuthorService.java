package sk.unibask.entries.author;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.unibask.data.model.Account;

@Service
public class AuthorService {

    @Transactional
    public AuthorDto getAuthorDto(Account account) {
        return new AuthorDto(account.getId(), account.getUsername(), account.getAvatar(), null);
    }

}
