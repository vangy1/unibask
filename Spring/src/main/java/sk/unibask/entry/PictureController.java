package sk.unibask.entry;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/picture")

public class PictureController {

    private final PictureService pictureService;

    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    @PostMapping("/upload")
    @ResponseStatus(value = HttpStatus.OK)
    private String uploadPicture(@RequestParam("picture") MultipartFile file) throws IOException {
        return pictureService.uploadPicture(file);
    }


}
