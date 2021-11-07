package sk.unibask.entry.comment;

import sk.unibask.authentication.UserDto;
import sk.unibask.entry.EntryDto;

import java.util.Date;

public class CommentDto extends EntryDto {
    public CommentDto(Long id, String text, Long reputation, Date creationDate, UserDto author) {
        super(id, text, reputation, creationDate, author);
    }
}
