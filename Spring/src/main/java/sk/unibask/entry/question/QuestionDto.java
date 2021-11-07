package sk.unibask.entry.question;

import sk.unibask.authentication.UserDto;
import sk.unibask.entry.EntryDto;
import sk.unibask.entry.answer.AnswerDto;
import sk.unibask.entry.comment.CommentDto;

import java.util.Date;
import java.util.List;

public class QuestionDto extends EntryDto {
    private String title;
    private String categoryName;
    private Long views;
    private List<AnswerDto> answers;
    private List<CommentDto> comments;
    private Long solvedAnswerId;
    private Date lastActivity;

    public QuestionDto(Long id, String text, Long views, Long reputation, Date creationDate, UserDto author, String title, String categoryName, List<AnswerDto> answers, List<CommentDto> comments, Long solvedAnswerId, Date lastActivity) {
        super(id, text, reputation, creationDate, author);
        this.title = title;
        this.views = views;
        this.categoryName = categoryName;
        this.answers = answers;
        this.comments = comments;
        this.solvedAnswerId = solvedAnswerId;
        this.lastActivity = lastActivity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getSolvedAnswerId() {
        return solvedAnswerId;
    }

    public void setSolvedAnswerId(Long solvedAnswerId) {
        this.solvedAnswerId = solvedAnswerId;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }

    public Date getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Date lastActivity) {
        this.lastActivity = lastActivity;
    }
}
