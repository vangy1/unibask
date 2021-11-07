package sk.unibask.entry;

import sk.unibask.authentication.UserDto;

import java.util.Date;

public class EntryDto {
    private Long id;
    private String text;
    private Long reputation;
    private Date creationDate;
    private UserDto author;

    public EntryDto(Long id, String text, Long reputation, Date creationDate, UserDto author) {
        this.id = id;
        this.text = text;
        this.reputation = reputation;
        this.creationDate = creationDate;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }
}
