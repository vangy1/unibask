package sk.unibask.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageService {
    public void storeFile(MultipartFile file, String filename, String folderPath) throws IOException {
        Path pictureFolderPath = Paths.get(folderPath);
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
