package sk.unibask.question;

import java.util.Date;
import java.util.List;

public class AnswerDto extends EntryDto {
    private List<CommentDto> comments;
    private Boolean solvesQuestion;

    public AnswerDto(String id, String text, String reputation, Date creationDate, AuthorDto author, List<CommentDto> comments, Boolean solvesQuestion) {
        super(id, text, reputation, creationDate, author);
        this.comments = comments;
    }

    public AnswerDto(Long id, String text, Long reputation, Date creationDate, AuthorDto author, List<CommentDto> comments, Boolean solvesQuestion) {
        super(id, text, reputation, creationDate, author);
        this.comments = comments;
        this.solvesQuestion = solvesQuestion;
    }

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Boolean getSolvesQuestion() {
        return solvesQuestion;
    }

    public void setSolvesQuestion(Boolean solvesQuestion) {
        this.solvesQuestion = solvesQuestion;
    }
}
