package sk.unibask.entry.answer;

import sk.unibask.entry.EntryDto;


public class AnswerDto extends EntryDto {
    private Boolean solvesQuestion;

    public AnswerDto(AnswerDto.Builder builder) {
        super(builder);
        this.solvesQuestion = builder.solvesQuestion;
    }

    public static class Builder extends EntryDto.Builder<AnswerDto.Builder> {
        private Boolean solvesQuestion;

        @Override
        public AnswerDto.Builder getThis() {
            return this;
        }

        public AnswerDto.Builder setSolvesQuestion(Boolean solvesQuestion) {
            this.solvesQuestion = solvesQuestion;
            return this;
        }

        public AnswerDto build() {
            return new AnswerDto(this);
        }
    }

    public static AnswerDto.Builder builder() {
        return new AnswerDto.Builder();
    }

    public Boolean getSolvesQuestion() {
        return solvesQuestion;
    }

    public void setSolvesQuestion(Boolean solvesQuestion) {
        this.solvesQuestion = solvesQuestion;
    }
}
