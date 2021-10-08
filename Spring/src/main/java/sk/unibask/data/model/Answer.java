package sk.unibask.data.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Answer extends Entry {
    @ManyToOne
    @JoinColumn(nullable = false)
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
