package sk.unibask.data.model;


import javax.persistence.*;
import java.util.List;

@Entity
public class Question extends Entry {
    @Column(columnDefinition = "varchar(1000)")
    private String title;
    private boolean isAnonymous;

    @ManyToOne
    private Category category;

    @OneToOne
    private Answer solvedAnswer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
