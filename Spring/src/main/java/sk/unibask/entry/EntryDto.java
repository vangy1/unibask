package sk.unibask.entry;

import sk.unibask.authentication.UserDto;
import sk.unibask.entry.comment.CommentDto;

import java.util.Date;
import java.util.List;

public class EntryDto {
    private Long id;
    private String text;
    private Long reputation;
    private Integer myVote;
    private Date creationDate;
    private UserDto author;
    private List<CommentDto> comments;
    private Long questionId;

    protected EntryDto(Builder<?> builder) {
        this.id = builder.id;
        this.text = builder.text;
        this.reputation = builder.reputation;
        this.myVote = builder.myVote;
        this.creationDate = builder.creationDate;
        this.author = builder.author;
        this.comments = builder.comments;
        this.questionId = builder.questionId;
    }


    public static Builder builder() {
        return new Builder() {
            @Override
            public Builder<?> getThis() {
                return this;
            }
        };
    }

    public abstract static class Builder<T extends Builder<T>> {
        private Long id;
        private String text;
        private Long reputation;
        private Integer myVote;
        private Date creationDate;
        private UserDto author;
        private List<CommentDto> comments;
        private Long questionId;

        public abstract T getThis();

        public T setId(Long id) {
            this.id = id;
            return this.getThis();
        }

        public T setText(String text) {
            this.text = text;
            return this.getThis();
        }

        public T setReputation(Long reputation) {
            this.reputation = reputation;
            return this.getThis();
        }

        public T setMyVote(Integer myVote) {
            this.myVote = myVote;
            return this.getThis();
        }

        public T setCreationDate(Date creationDate) {
            this.creationDate = creationDate;
            return this.getThis();
        }

        public T setAuthor(UserDto author) {
            this.author = author;
            return this.getThis();
        }

        public T setComments(List<CommentDto> comments) {
            this.comments = comments;
            return this.getThis();
        }

        public T setQuestionId(Long questionId) {
            this.questionId = questionId;
            return this.getThis();
        }

        public EntryDto build() {
            return new EntryDto(this);
        }
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

    public Long getReputation() {
        return reputation;
    }

    public void setReputation(Long reputation) {
        this.reputation = reputation;
    }

    public Integer getMyVote() {
        return myVote;
    }

    public void setMyVote(Integer myVote) {
        this.myVote = myVote;
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

    public List<CommentDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentDto> comments) {
        this.comments = comments;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
}
