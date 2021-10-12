package sk.unibask.entries.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto createNewComment(@RequestBody Map<String, String> body) {
        var newComment = commentService.createNewComment(body.get("text"), Long.valueOf(body.get("entryId")));
        return commentService.commentToDto(newComment);
    }
}
