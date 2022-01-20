package sk.unibask.data.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity(name = "questions")
public class Question extends Entry {
    @Column(columnDefinition = "varchar(1000)")
    private String title;
    private boolean isAnonymous;
    private Date lastActivity;

    @ManyToOne
    private Category category;

    @OneToOne
    private Answer solvedAnswer;

    @ManyToMany
    private Set<Account> viewers;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers;

    @ManyToMany(mappedBy = "followingQuestions")
    private Set<Account> followerAccounts;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Answer getSolvedAnswer() {
        return solvedAnswer;
    }

    public void setSolvedAnswer(Answer solvedAnswer) {
        this.solvedAnswer = solvedAnswer;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public Set<Account> getViewers() {
        return viewers;
    }

    public void setViewers(Set<Account> viewers) {
        this.viewers = viewers;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }

    public Set<Account> getFollowerAccounts() {
        return followerAccounts;
    }

    public void setFollowerAccounts(Set<Account> followerAccounts) {
        this.followerAccounts = followerAccounts;
    }
}
