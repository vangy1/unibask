package sk.unibask.entries.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public QuestionDto getQuestion(@RequestParam("id") String id, @RequestParam("answers") String answers) {
        if ("true".equals(answers)) {
            return questionService.getQuestionWithAnswers(Long.parseLong(id));
        } else {
            return questionService.getQuestion(Long.parseLong(id));
        }
    }

    @PostMapping
    public String createNewQuestion(@RequestBody Map<String, String> body) {
        var newQuestion = questionService.createNewQuestion(body.get("title"), body.get("text"), Long.parseLong(body.get("categoryId")), Boolean.parseBoolean(body.get("isAnonymous")));
        return String.valueOf(newQuestion.getId());
    }
}
