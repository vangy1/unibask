package sk.unibask.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sk.unibask.authentication.UserDto;

import java.util.Map;


@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final ProfileService userService;

    @Autowired
    public ProfileController(ProfileService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDto getUser(@RequestParam("userId") String userId) {
        return userService.getUser(Long.valueOf(userId));
    }

    @GetMapping("/info")
    public UserInfoDto getUserInfo() {
        return userService.getUserInfo();
    }

    @PostMapping("/info")
    public void setUserInfo(@RequestBody Map<String, String> body) {
        userService.setUserInfo(body.get("studyProgramId"),
                Boolean.parseBoolean(body.get("mailNotificationsEnabled")));
    }
}
