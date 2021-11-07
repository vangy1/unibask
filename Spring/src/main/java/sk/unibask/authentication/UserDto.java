package sk.unibask.authentication;

import java.util.List;

public class UserDto {
    private String mail;
    private String username;
    private String avatar;
    private List<String> roles;
    private Long reputation;

    public UserDto(String mail, String username, String avatar, List<String> roles) {
        this.mail = mail;
        this.username = username;
        this.avatar = avatar;
        this.roles = roles;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
