package sk.unibask.data.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date creationDate;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String username;
    private String password;
    private String avatarSeed;
    private String avatarFilename;
    private Boolean mailNotifications = false;

    @ManyToOne
    @JoinColumn
    private StudyProgram studyProgram;

    @OneToMany(mappedBy = "account")
    private Set<Entry> entries;

    @OneToMany(mappedBy = "account")
    private Set<Vote> votes;

    @OneToMany(mappedBy = "reporter")
    private Set<Report> reports;

    @OneToMany(mappedBy = "account")
    @OrderBy("creationDate DESC")
    private Set<Notification> notifications;

    @ManyToMany
    private Set<Authority> authorities;

    @ManyToMany(mappedBy = "viewers")
    private Set<Question> viewedQuestions;

    @ManyToMany
    private Set<Category> followingCategories;

    @ManyToMany
    private Set<Question> followingQuestions;


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

    public Set<Category> getFollowingCategories() {
        return followingCategories;
    }

    public void setFollowingCategories(Set<Category> followingCategories) {
        this.followingCategories = followingCategories;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<Question> getViewedQuestions() {
        return viewedQuestions;
    }

    public void setViewedQuestions(Set<Question> viewedQuestions) {
        this.viewedQuestions = viewedQuestions;
    }

    public Set<Question> getFollowingQuestions() {
        return followingQuestions;
    }

    public void setFollowingQuestions(Set<Question> followingQuestions) {
        this.followingQuestions = followingQuestions;
    }

    public Set<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<Notification> notifications) {
        this.notifications = notifications;
    }

    public Boolean getMailNotifications() {
        return mailNotifications;
    }

    public void setMailNotifications(Boolean mailNotifications) {
        this.mailNotifications = mailNotifications;
    }
}
