package sk.unibask.entry.answer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    @Transactional
    public AnswerDto createNewAnswer(@RequestBody Map<String, String> body) {
        var newAnswer = answerService.createNewAnswer(body.get("text"), Boolean.parseBoolean(body.get("isAnonymous")), Long.valueOf(body.get("questionId")));
        return answerService.answerToDto(newAnswer);
    }
}
