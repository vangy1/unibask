package sk.unibask.entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sk.unibask.authentication.AuthenticationService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class PictureService {
    @Value("${location.pictures-folder}")
    private String pictureFolder;
    @Value("${app.pictures-path}")
    private String picturePath;

    private final AuthenticationService authenticationService;

    public PictureService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Transactional
    public String uploadPicture(MultipartFile file) throws IOException {
        authenticationService.getLoggedAccount();
        String filename = RandomStringUtils.random(24, true, true);
        storePicture(file, filename);
        return picturePath + filename;
    }

    public void storePicture(MultipartFile file, String filename) throws IOException {
        Path pictureFolderPath = Paths.get(pictureFolder);
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        Path destinationFile = pictureFolderPath.resolve(Paths.get(filename)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(pictureFolderPath.toAbsolutePath())) {
            throw new RuntimeException("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }

}
