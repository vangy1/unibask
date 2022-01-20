package sk.unibask.entry.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sk.unibask.entry.EntityToDtoService;

import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final EntityToDtoService entityToDtoService;

    @Autowired
    public AnswerController(AnswerService answerService, EntityToDtoService entityToDtoService) {
        this.answerService = answerService;
        this.entityToDtoService = entityToDtoService;
    }

    @PostMapping
    @Transactional
    public AnswerDto createNewAnswer(@RequestBody Map<String, String> body) {
        return entityToDtoService.answerToAnswerDto(answerService.createNewAnswer(body.get("text"), body.get("unformattedText"), Boolean.parseBoolean(body.get("isAnonymous")), Long.valueOf(body.get("questionId"))), null);
    }

    @PostMapping("/mark-solved")
    @Transactional
    public void markSolved(@RequestBody Map<String, String> body) {
        answerService.markSolved(Long.valueOf(body.get("answerId")), Boolean.parseBoolean(body.get("isSolved")));
    }
}
