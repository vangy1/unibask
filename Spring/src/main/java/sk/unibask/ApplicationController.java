package sk.unibask;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ApplicationController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
