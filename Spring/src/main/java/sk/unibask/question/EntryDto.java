package sk.unibask.question;

import java.util.Date;

public class EntryDto {
    private String id;
    private String text;
    private String reputation;
    private Date creationDate;
    private AuthorDto author;

    public EntryDto(String id, String text, String reputation, Date creationDate, AuthorDto author) {
        this.id = id;
        this.text = text;
        this.reputation = reputation;
        this.creationDate = creationDate;
        this.author = author;
    }

    public EntryDto(Long id, String text, Long reputation, Date creationDate, AuthorDto author) {
        this.id = String.valueOf(id);
        this.text = text;
        this.reputation = String.valueOf(reputation);
        this.creationDate = creationDate;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public AuthorDto getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDto author) {
        this.author = author;
    }

    public String getReputation() {
        return reputation;
    }

    public void setReputation(String reputation) {
        this.reputation = reputation;
    }
}
