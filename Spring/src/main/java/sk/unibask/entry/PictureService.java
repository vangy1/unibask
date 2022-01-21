package sk.unibask.entry;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.storage.StorageService;

import java.io.IOException;

@Service
public class PictureService {
    @Value("${location.pictures-folder}")
    private String pictureFolder;
    @Value("${app.pictures-path}")
    private String picturePath;

    private final AuthenticationService authenticationService;
    private final StorageService storageService;

    public PictureService(AuthenticationService authenticationService, StorageService storageService) {
        this.authenticationService = authenticationService;
        this.storageService = storageService;
    }

    @Transactional
    public String uploadPicture(MultipartFile file) throws IOException {
        authenticationService.getLoggedAccount();
        String filename = RandomStringUtils.random(24, true, true);
        storageService.storeFile(file, filename, pictureFolder);
        return picturePath + filename;
    }
}
