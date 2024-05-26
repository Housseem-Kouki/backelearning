package com.example.springjwt.file;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

///@Service
//@AllArgsConstructor
public class FileService {

    @Value("${upload.dir}") // Chemin vers le dossier d'uploads (défini dans application.properties)
    private String uploadDir;

    public void saveFileFromBlob(String filename, byte[] blobData) throws IOException {
        Path path = Paths.get(uploadDir + "/" + filename);
        Files.write(path, blobData);
    }

    public byte[] readFileToBlob(String filename) throws IOException {
        Path path = Paths.get(uploadDir + "/" + filename);
        return Files.readAllBytes(path);
    }
}
