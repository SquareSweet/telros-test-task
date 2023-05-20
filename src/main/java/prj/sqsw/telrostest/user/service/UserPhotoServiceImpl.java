package prj.sqsw.telrostest.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import prj.sqsw.telrostest.user.dto.UserPhotoDto;
import prj.sqsw.telrostest.user.exception.UserPhotoDownloadException;
import prj.sqsw.telrostest.user.exception.UserPhotoUploadException;
import prj.sqsw.telrostest.user.model.User;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPhotoServiceImpl implements UserPhotoService {
    private final UserService userService;

    @Override
    public byte[] getPhoto(Long userId) {
        User user = userService.findById(userId); //throws exception if user doesn't exist

        Path filePath = Paths.get(user.getPhoto());
        byte[] photo;
        try {
            if (Files.exists(filePath)) {
                photo = Files.readAllBytes(filePath);
            } else {
                photo = new byte[0];
            }
        } catch (IOException e) {
            throw new UserPhotoDownloadException();
        }

        return photo;
    }

    @Override
    public UserPhotoDto uploadPhoto(Long userId, MultipartFile photo, String path) {
        User user = userService.findById(userId); //throws exception if user doesn't exist
        if (user.getPhoto() != null) {
            Path filePath = Paths.get(user.getPhoto());
            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                throw new UserPhotoUploadException();
            }
        }

        String filePath = path + File.separator + userId + "_" +
                String.format("%s.%s", UUID.randomUUID().toString().replace("-", ""), "jpg");

        try {
            Path uploadPath = Paths.get(path);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            BufferedImage image = ImageIO.read(photo.getInputStream());
            photo.getInputStream().close(); //ImageIO.read doesn't close the input stream
            BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
            convertedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);

            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            boolean canWrite = ImageIO.write(convertedImage, "jpg", fileOutputStream);
            fileOutputStream.close(); //ImageIO.write doesn't close the output stream

            if (!canWrite) {
                throw new IllegalStateException("Error occurred when saving photo");
            }

        } catch (IOException e) {
            throw new UserPhotoUploadException();
        }

        userService.updatePhoto(filePath, userId);

        return new UserPhotoDto(filePath);
    }

    @Override
    public void deletePhoto(Long userId) {
        User user = userService.findById(userId); //throws exception if user doesn't exist
        if (user.getPhoto() != null) {
            Path filePath = Paths.get(user.getPhoto());
            try {
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            } catch (IOException e) {
                throw new UserPhotoUploadException();
            }
        }
        userService.updatePhoto("", userId);
    }
}
