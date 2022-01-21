package sk.unibask.profile.avatar;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public void saveAvatar(@RequestBody Map<String, String> body) {
        avatarService.saveAvatar(body.get("avatarUrl"));
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file) throws IOException {
        return avatarService.uploadAvatar(file);
    }


}
