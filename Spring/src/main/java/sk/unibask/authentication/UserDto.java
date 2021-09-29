package sk.unibask.authentication;

import sk.unibask.data.model.Account;
import sk.unibask.data.model.Authority;

import java.util.List;

public class UserDto {
    private String mail;
    private String username;
    private List<String> roles;

    public UserDto(String mail, String username, List<String> roles) {
        this.mail = mail;
        this.username = username;
        this.roles = roles;
    }

    public UserDto(Account account) {
        this.mail = account.getEmail();
        this.username = account.getUsername();
        this.roles = account.getAuthorities().stream().map(Authority::getName).toList();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
