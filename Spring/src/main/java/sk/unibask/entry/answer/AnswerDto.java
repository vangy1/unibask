package sk.unibask.entry.answer;

import sk.unibask.authentication.UserDto;
import sk.unibask.entry.EntryDto;
import sk.unibask.entry.comment.CommentDto;

import java.util.Date;
import java.util.List;

public class AnswerDto extends EntryDto {
    private List<CommentDto> comments;
    private Boolean solvesQuestion;

    public AnswerDto(Long id, String text, Long reputation, Date creationDate, UserDto author, List<CommentDto> comments, Boolean solvesQuestion) {
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
