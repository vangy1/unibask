package sk.unibask.entry;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.unibask.data.model.*;
import sk.unibask.entry.answer.AnswerDto;
import sk.unibask.entry.comment.CommentDto;
import sk.unibask.entry.question.QuestionDto;
import sk.unibask.profile.ProfileService;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class EntityToDtoService {

    private final ProfileService userService;

    @Autowired
    public EntityToDtoService(ProfileService userService) {
        this.userService = userService;
    }

    public QuestionDto questionToQuestionDto(Question question, Long loggedAccountId) {
        QuestionDto.Builder questionDtoBuilder = QuestionDto.builder()
                .setId(question.getId())
                .setQuestionId(question.getId())
                .setText(question.getEntryText())
                .setCreationDate(question.getCreationDate())
                .setTitle(question.getTitle())
                .setCategoryName(question.getCategory().getTitle())
                .setSolvedAnswerId(question.getSolvedAnswer() != null ? question.getSolvedAnswer().getId() : null)
                .setLastActivity(question.getLastActivity());

        if (!question.isAnonymous()) {
            questionDtoBuilder.setAuthor(userService.getUserWithoutTransaction(question.getAccount()));
        }

        setVotes(question, questionDtoBuilder, loggedAccountId);
        setComments(question, questionDtoBuilder, loggedAccountId);
        if (Hibernate.isInitialized(question.getViewers()) && question.getViewers() != null) {
            questionDtoBuilder.setViews((long) question.getViewers().size());
        } else {
            questionDtoBuilder.setViews(0L);
        }
        if (Hibernate.isInitialized(question.getAnswers()) && question.getAnswers() != null) {
            questionDtoBuilder.setAnswers(question.getAnswers().stream().map(answer -> answerToAnswerDto(answer, loggedAccountId)).sorted().toList());
        }

        return questionDtoBuilder.build();
    }


    public AnswerDto answerToAnswerDto(Answer answer, Long loggedAccountId) {
        AnswerDto.Builder answerDtoBuilder = AnswerDto.builder()
                .setId(answer.getId())
                .setQuestionId(answer.getQuestion().getId())
                .setText(answer.getEntryText())
                .setCreationDate(answer.getCreationDate())
                .setSolvesQuestion(answer == answer.getQuestion().getSolvedAnswer());
        if (!answer.isAnonymous()) {
            answerDtoBuilder.setAuthor(userService.getUserWithoutTransaction(answer.getAccount()));
        }
        setVotes(answer, answerDtoBuilder, loggedAccountId);
        setComments(answer, answerDtoBuilder, loggedAccountId);
        return answerDtoBuilder.build();
    }

    public CommentDto commentToCommentDto(Comment comment, Long loggedAccountId) {
        CommentDto.Builder commentDtoBuilder = CommentDto.builder()
                .setId(comment.getId())
                .setText(comment.getEntryText())
                .setCreationDate(comment.getCreationDate())
                .setAuthor(userService.getUserWithoutTransaction(comment.getAccount()));

        setVotes(comment, commentDtoBuilder, loggedAccountId);
        if (comment.getEntry() instanceof Question question) {
            commentDtoBuilder.setQuestionId(question.getId());
        } else if (comment.getEntry() instanceof Answer answer) {
            commentDtoBuilder.setQuestionId(answer.getQuestion().getId());
        }
        return commentDtoBuilder.build();
    }

    private void setVotes(Entry entry, EntryDto.Builder<?> builder, Long loggedAccountId) {
        if (Hibernate.isInitialized(entry.getVotes()) && entry.getVotes() != null) {
            builder.setReputation((long) entry.getVotes().stream().mapToInt(Vote::getValue).sum());
            if (loggedAccountId != null) {
                builder.setMyVote(entry.getVotes().stream().filter(vote -> Objects.equals(vote.getAccount().getId(), loggedAccountId)).findAny().map(Vote::getValue).orElse(0));
            }
        } else {
            builder.setReputation(0L);
        }
    }

    private void setComments(Entry entry, EntryDto.Builder<?> builder, Long loggedAccountId) {
        if (Hibernate.isInitialized(entry.getComments()) && entry.getComments() != null) {
            builder.setComments(entry.getComments().stream().map(comment -> {
                Hibernate.initialize(comment.getVotes());
                return commentToCommentDto(comment, loggedAccountId);
            }).toList());
        } else {
            builder.setComments(new ArrayList<>());
        }
    }

}
