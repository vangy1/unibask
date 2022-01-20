package sk.unibask.user.avatar;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AvatarService {
    @Value("${location.avatars-folder}")
    private String avatarFolder;

    @Value("${app.avatars-path}")
    private String avatarsPath;

    private final String AVATAR_API_URL = "https://avatars.dicebear.com/api/adventurer/:";

    private final AuthenticationService authenticationService;

    @Autowired
    public AvatarService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Transactional
    public String uploadAvatar(MultipartFile file) throws IOException {
        authenticationService.getLoggedAccount();
        String filename = RandomStringUtils.random(24, true, true);
        storeAvatar(file, filename);
        return avatarsPath + filename;
    }

    public void storeAvatar(MultipartFile file, String filename) throws IOException {
        Path avatarFolderPath = Paths.get(avatarFolder);
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }

        Path destinationFile = avatarFolderPath.resolve(Paths.get(filename)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(avatarFolderPath.toAbsolutePath())) {
            throw new RuntimeException("Cannot store file outside current directory.");
        }

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, destinationFile,
                    StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public String getAvatarUrl(Account account) {
        if (account.getAvatarFilename() == null) {
            return AVATAR_API_URL + account.getAvatarSeed() + ".svg";
        } else {
            return avatarsPath + account.getAvatarFilename();
        }
    }

    @Transactional
    public void saveAvatar(String avatarUrl) {
        Account account = authenticationService.getLoggedAccount();
        if (avatarUrl.startsWith(AVATAR_API_URL)) {
            account.setAvatarSeed(avatarUrl.replace(AVATAR_API_URL, "").replace(".svg", ""));
            account.setAvatarFilename(null);
        } else if (avatarUrl.startsWith(avatarsPath)) {
            account.setAvatarFilename(avatarUrl.replace(avatarsPath, ""));
        }
    }
}


