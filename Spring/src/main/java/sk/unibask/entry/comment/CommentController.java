package sk.unibask.entry.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.unibask.entry.EntityToDtoService;

import java.util.Map;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final EntityToDtoService entityToDtoService;

    @Autowired
    public CommentController(CommentService commentService, EntityToDtoService entityToDtoService) {
        this.commentService = commentService;
        this.entityToDtoService = entityToDtoService;
    }

    @PostMapping
    public CommentDto createNewComment(@RequestBody Map<String, String> body) {
        return entityToDtoService.commentToCommentDto(commentService.createNewComment(body.get("text"), Long.valueOf(body.get("entryId"))), null);
    }
}
