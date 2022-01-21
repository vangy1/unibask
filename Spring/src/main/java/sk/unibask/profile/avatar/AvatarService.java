package sk.unibask.profile.avatar;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.storage.StorageService;

import java.io.IOException;

@Service
public class AvatarService {
    @Value("${location.avatars-folder}")
    private String avatarFolder;

    @Value("${app.avatars-path}")
    private String avatarsPath;

    private final String AVATAR_API_URL = "https://avatars.dicebear.com/api/adventurer/:";

    private final AuthenticationService authenticationService;
    private final StorageService storageService;

    public AvatarService(AuthenticationService authenticationService, StorageService storageService) {
        this.authenticationService = authenticationService;
        this.storageService = storageService;
    }

    @Transactional
    public String uploadAvatar(MultipartFile file) throws IOException {
        authenticationService.getLoggedAccount();
        String filename = RandomStringUtils.random(24, true, true);
        storageService.storeFile(file, filename, avatarFolder);
        return avatarsPath + filename;
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


