package com.gestion.location.service;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FileUploadService {

    private static final String UPLOAD_DIR = "uploads";
    private static final String IMAGE_DIR = "images";

    public String uploadImage(Part filePart, String contextPath) throws IOException {
        // Créer le répertoire de destination s'il n'existe pas
        String uploadPath = contextPath + File.separator + UPLOAD_DIR + File.separator + IMAGE_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Générer un nom de fichier unique
        String fileName = UUID.randomUUID().toString() + "_" + getFileName(filePart);

        // Chemin complet du fichier
        String filePath = uploadPath + File.separator + fileName;

        // Écrire le fichier sur le disque
        filePart.write(filePath);

        // Retourner le chemin relatif pour la base de données
        return UPLOAD_DIR + "/" + IMAGE_DIR + "/" + fileName;
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

    public boolean deleteImage(String imageUrl, String contextPath) {
        try {
            String filePath = contextPath + File.separator + imageUrl;
            File file = new File(filePath);
            return file.delete();
        } catch (Exception e) {
            return false;
        }
    }
}