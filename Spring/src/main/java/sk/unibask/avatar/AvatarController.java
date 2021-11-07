package sk.unibask.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/avatar")

public class AvatarController {

    private final AvatarService avatarService;

    @Autowired
    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    private void saveAvatar(@RequestBody Map<String, String> body) throws IOException {
        avatarService.saveAvatar(body.get("avatarUrl"));
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    private String uploadAvatar(@RequestParam("avatar") MultipartFile file) throws IOException {
        return avatarService.uploadAvatar(file);
    }


}
