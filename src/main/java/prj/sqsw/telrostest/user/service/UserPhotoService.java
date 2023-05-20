package prj.sqsw.telrostest.user.service;

import org.springframework.web.multipart.MultipartFile;
import prj.sqsw.telrostest.user.dto.UserPhotoDto;

public interface UserPhotoService {

    byte[] getPhoto(Long userId);

    UserPhotoDto uploadPhoto(Long userId, MultipartFile photo, String path);

    void deletePhoto(Long userId);

}
