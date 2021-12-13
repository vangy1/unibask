package sk.unibask.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDto getUser(@RequestParam("userId") String userId) {
        return userService.getUser(Long.valueOf(userId));
    }

    @PostMapping("/study-program")
    public void setStudyProgram(@RequestBody Map<String, String> body) {
        userService.setStudyProgramId(Integer.valueOf(body.get("studyProgramId")));
    }
}
