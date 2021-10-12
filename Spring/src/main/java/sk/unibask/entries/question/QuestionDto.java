package sk.unibask.entries.question;

import sk.unibask.entries.EntryDto;
import sk.unibask.entries.answer.AnswerDto;
import sk.unibask.entries.author.AuthorDto;
import sk.unibask.entries.comment.CommentDto;

import java.util.Date;
import java.util.List;

public class QuestionDto extends EntryDto {
    private String title;
    private String categoryName;
    private List<AnswerDto> answers;
    private List<CommentDto> comments;
    private String solvedAnswerId;

    public QuestionDto(String id, String text, String reputation, Date creationDate, AuthorDto author, String title, String categoryName, List<AnswerDto> answers, List<CommentDto> comments, String solvedAnswerId) {
        super(id, text, reputation, creationDate, author);
        this.title = title;
        this.categoryName = categoryName;
        this.answers = answers;
        this.comments = comments;
        this.solvedAnswerId = solvedAnswerId;
    }

    public QuestionDto(Long id, String text, Long reputation, Date creationDate, AuthorDto author, String title, String categoryName, List<AnswerDto> answers, List<CommentDto> comments, Long solvedAnswerId) {
        super(id, text, reputation, creationDate, author);
        this.title = title;
        this.categoryName = categoryName;
        this.answers = answers;
        this.comments = comments;
        this.solvedAnswerId = String.valueOf(solvedAnswerId);
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

    public String getSolvedAnswerId() {
        return solvedAnswerId;
    }

    public void setSolvedAnswerId(String solvedAnswerId) {
        this.solvedAnswerId = solvedAnswerId;
    }
}
