package sk.unibask;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ApplicationController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @GetMapping("/authentication")
//    public String authentication() {
//        return "index";
//    }
//
//    @GetMapping("/ask")
//    public String questionAsk() {
//        return "index";
//    }
//
//    @GetMapping("/question")
//    public String questionView() {
//        return "index";
//    }
//
//    @GetMapping("/categories")
//    public String categories() {
//        return "index";
//    }
//
//    @GetMapping("/list")
//    public String listQuestions() {
//        return "index";
//    }
//
//    @GetMapping("/profile")
//    public String profile() {
//        return "index";
//    }
}
