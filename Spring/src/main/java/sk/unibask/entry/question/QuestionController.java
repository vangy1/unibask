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
                                          @RequestParam(value = "phrase", required = false) String phrase,
                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                          @RequestParam(value = "limit", required = false, defaultValue = "50") int limit) {
        return questionService.getQuestions(phrase != null ? phrase.toLowerCase() : null, followed, category, page, limit).stream().peek(questionDto -> questionDto.setText(null)).toList();
    }


    @GetMapping("/question")
    public QuestionDto getQuestion(@RequestParam("id") String id) {
        return questionService.getQuestion(Long.parseLong(id));

    }

    @PostMapping("/question")
    public String createNewQuestion(@RequestBody Map<String, String> body) {
        var newQuestion = questionService.createNewQuestion(body.get("title"), body.get("text"), body.get("unformattedText"), Long.parseLong(body.get("categoryId")), Boolean.parseBoolean(body.get("isAnonymous")));
        return String.valueOf(newQuestion.getId());
    }

    @PostMapping("/question/follow")
    public void changeFollowStatus(@RequestBody Map<String, String> body) {
        questionService.changeFollowStatus(body.get("id"), Boolean.valueOf(body.get("followed")));
    }
}
