package prj.sqsw.telrostest.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import prj.sqsw.telrostest.user.dto.UserPhotoDto;
import prj.sqsw.telrostest.user.service.UserPhotoService;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/users/{userId}/photo")
public class UserPhotoController {
    private final UserPhotoService userPhotoService;

    private final String path = "profile_photos";

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadPhoto(@PathVariable Long userId) {
        return userPhotoService.getPhoto(userId);
    }

    @PostMapping()
    public UserPhotoDto uploadPhoto(@PathVariable Long userId,
                              @RequestParam(value = "photo") MultipartFile photo) {
        return userPhotoService.uploadPhoto(userId, photo, path);
    }

    @DeleteMapping()
    public void deletePhoto(@PathVariable Long userId) {
        userPhotoService.deletePhoto(userId);
    }
}
