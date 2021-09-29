package sk.unibask.data.model;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Question extends Entry {
    private int upvotes;
    private int downvotes;

    @ManyToOne
    private Category category;

    @OneToOne
    private Answer solvedAnswer;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
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
