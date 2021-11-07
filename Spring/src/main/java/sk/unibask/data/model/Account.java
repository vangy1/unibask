package sk.unibask.data.model;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String username;
    private String password;
    private String avatarSeed;
    private String avatarFilename;

    @OneToOne
    @JoinColumn
    private StudyProgram studyProgram;

    @OneToMany(mappedBy = "account")
    private Set<Entry> entries;

    @OneToMany(mappedBy = "account")
    private Set<Vote> votes;

    @OneToMany(mappedBy = "account")
    private Set<Follow> follows;

    @OneToMany(mappedBy = "account")
    private Set<Notification> notifications;

    @ManyToMany
    private Set<Authority> authorities;

    @ManyToMany
    private Set<Category> favoriteCategories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarSeed() {
        return avatarSeed;
    }

    public void setAvatarSeed(String avatarSeed) {
        this.avatarSeed = avatarSeed;
    }

    public String getAvatarFilename() {
        return avatarFilename;
    }

    public void setAvatarFilename(String avatarPath) {
        this.avatarFilename = avatarPath;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Category> getFavoriteCategories() {
        return favoriteCategories;
    }

    public void setFavoriteCategories(Set<Category> favoriteCategories) {
        this.favoriteCategories = favoriteCategories;
    }

    public Set<Follow> getFollows() {
        return follows;
    }

    public void setFollows(Set<Follow> follows) {
        this.follows = follows;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }
}
