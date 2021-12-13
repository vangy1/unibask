package sk.unibask.entry.comment;

import sk.unibask.entry.EntryDto;

public class CommentDto extends EntryDto {
    public CommentDto(CommentDto.Builder builder) {
        super(builder);
    }

    public static class Builder extends EntryDto.Builder<Builder> {
        @Override
        public CommentDto.Builder getThis() {
            return this;
        }

        public CommentDto build() {
            return new CommentDto(this);
        }
    }

    public static CommentDto.Builder builder() {
        return new CommentDto.Builder();
    }

}
