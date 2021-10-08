package sk.unibask.question;

import java.util.Date;

public class CommentDto extends EntryDto {
    public CommentDto(String id, String text, String reputation, Date creationDate, AuthorDto author) {
        super(id, text, reputation, creationDate, author);
    }

    public CommentDto(Long id, String text, Long reputation, Date creationDate, AuthorDto author) {
        super(id, text, reputation, creationDate, author);
    }
}
