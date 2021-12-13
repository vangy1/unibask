package sk.unibask.entry.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // will need limit for pagination
    @GetMapping("/questions")
    public List<QuestionDto> getQuestions(@RequestParam(value = "followed", required = false) boolean followed,
                                          @RequestParam(value = "category", required = false) Long category,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        if (followed) {
            return questionService.getFollowedQuestions(page, limit);
        } else if (category != null) {
            return questionService.getQuestionsByCategory(category, page, limit);
        } else {
            return questionService.getQuestions(page, limit);
        }
    }


    @GetMapping("/question")
    public QuestionDto getQuestion(@RequestParam("id") String id, @RequestParam("answers") String answers) {
        if ("true".equals(answers)) {
            return questionService.getQuestionWithAnswers(Long.parseLong(id));
        } else {
            return questionService.getQuestion(Long.parseLong(id));
        }
    }

    @PostMapping("/question")
    public String createNewQuestion(@RequestBody Map<String, String> body) {
        var newQuestion = questionService.createNewQuestion(body.get("title"), body.get("text"), Long.parseLong(body.get("categoryId")), Boolean.parseBoolean(body.get("isAnonymous")));
        return String.valueOf(newQuestion.getId());
    }
}
