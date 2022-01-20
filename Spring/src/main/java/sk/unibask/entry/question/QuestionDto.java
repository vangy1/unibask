package sk.unibask.entry.question;

import sk.unibask.entry.EntryDto;
import sk.unibask.entry.answer.AnswerDto;

import java.util.Date;
import java.util.List;

public class QuestionDto extends EntryDto {
    private String title;
    private String categoryName;
    private Long views;
    private List<AnswerDto> answers;
    private Long solvedAnswerId;
    private Date lastActivity;
    private Boolean followed;

    public QuestionDto(Builder builder) {
        super(builder);
        this.title = builder.title;
        this.views = builder.views;
        this.categoryName = builder.categoryName;
        this.answers = builder.answers;
        this.solvedAnswerId = builder.solvedAnswerId;
        this.lastActivity = builder.lastActivity;
        this.followed = builder.followed;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EntryDto.Builder<Builder> {
        private String title;
        private String categoryName;
        private Long views;
        private List<AnswerDto> answers;
        private Long solvedAnswerId;
        private Date lastActivity;
        private Boolean followed;

        @Override
        public Builder getThis() {
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public Builder setViews(Long views) {
            this.views = views;
            return this;
        }

        public Builder setAnswers(List<AnswerDto> answers) {
            this.answers = answers;
            return this;
        }

        public Builder setSolvedAnswerId(Long solvedAnswerId) {
            this.solvedAnswerId = solvedAnswerId;
            return this;
        }

        public Builder setLastActivity(Date lastActivity) {
            this.lastActivity = lastActivity;
            return this;
        }

        public Builder setFollowed(Boolean followed) {
            this.followed = followed;
            return this;
        }

        public QuestionDto build() {
            return new QuestionDto(this);
        }
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

    public Boolean getFollowed() {
        return followed;
    }

    public void setFollowed(Boolean followed) {
        this.followed = followed;
    }
}
